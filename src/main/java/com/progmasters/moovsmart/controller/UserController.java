package com.progmasters.moovsmart.controller;

import com.progmasters.moovsmart.domain.UserProperty;
import com.progmasters.moovsmart.dto.CreateUserCommand;
import com.progmasters.moovsmart.dto.UserDetails;
import com.progmasters.moovsmart.security.AuthenticatedUser;
import com.progmasters.moovsmart.security.MyUserDetails;
import com.progmasters.moovsmart.service.MailSenderService;
import com.progmasters.moovsmart.service.UserService;
import com.progmasters.moovsmart.validation.RegistrationValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/user")
public class UserController {

    private MailSenderService mailSenderService;
    private UserService userService;
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    private RegistrationValidator registrationValidator;


    public UserController(MailSenderService mailSenderService,
                          UserService userService,
                          RegistrationValidator registrationValidator) {
        this.mailSenderService = mailSenderService;
        this.userService = userService;
        this.registrationValidator = registrationValidator;
    }

    @InitBinder("createUserCommand")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(registrationValidator);
    }

    @PostMapping("/registration")
    public ResponseEntity createUser(@RequestBody CreateUserCommand command) {
        Long id = this.userService.makeUser(command);
        if (id != null) {
           this.mailSenderService.sendMailByTokenRegistration(command.getUserName(), command.getMail());
            return new ResponseEntity(HttpStatus.CREATED);
        } else {
            logger.warn("Registration was not possible");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/validuser/{id}")
    public ResponseEntity validateUser(@PathVariable("id") String token) {
        UserProperty user = this.mailSenderService.getUserByToken(token);
        if(user != null){
            user.setIsActive(true);
            List<String> roles = this.userService.validateUser(user.getId());
            if (roles != null) {
                return new ResponseEntity<>(roles, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/me")
    public ResponseEntity<AuthenticatedUser> authenticateUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) authentication.getPrincipal();
        return new ResponseEntity<>(new AuthenticatedUser(user), HttpStatus.OK);
    }

    @GetMapping("/getUserByUserMail/{mail}")
    public ResponseEntity<UserDetails> getUserByMail(@PathVariable("mail") String mail){
        UserDetails user = this.userService.getUserByMail(mail);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /*
    * Admin authorization methods*/

    @PutMapping("/admin/banUser/{id}")
    public ResponseEntity banUserById(@PathVariable("id")Long id){
        ResponseEntity response = this.userService.banUserById(id);
        if(response.getStatusCode().equals(HttpStatus.OK)){
            this.logger.info("User by id of: " + id + " is forbidden");
        }else {
            this.logger.warn("User by id of: " + id + " is Not Found");
        }
        return response;
    }

    @PutMapping("/admin/permitUser/{id}")
    public ResponseEntity permitUserById(@PathVariable("id")Long id){
        ResponseEntity response = this.userService.permitUserById(id);
        if(response.getStatusCode().equals(HttpStatus.OK)){
            this.logger.info("User by id of: " + id + " is permitted");
        }else {
            this.logger.warn("User by id of: " + id + " is Not Found");
        }
        return response;
    }
}
