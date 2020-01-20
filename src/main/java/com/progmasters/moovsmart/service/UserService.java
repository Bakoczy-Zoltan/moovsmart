package com.progmasters.moovsmart.service;

import com.progmasters.moovsmart.domain.RoleType;
import com.progmasters.moovsmart.domain.UserProperty;
import com.progmasters.moovsmart.dto.CreateUserCommand;
import com.progmasters.moovsmart.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private UserRepository userRepository;


    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Long makeUser(CreateUserCommand command) {
        Long id = null;
        if (this.userRepository.findUserPropertiesByMail(command.getMail()).isEmpty()) {
            UserProperty newUser = new UserProperty(command);
            String password = passwordEncoder.encode(command.getPassword());
            newUser.setPassword(password);

            this.userRepository.save(newUser);

            Optional<UserProperty> user = this.userRepository.findUserPropertiesByMail(command.getMail());
            if (user.isPresent()) {
                id = user.get().getId();
            }
        }
        return id;
    }

    public List<String> validateUser(Long id) {
        Optional<UserProperty>user = this.userRepository.findById(id);
        if(user.isPresent()){
            UserProperty validUser = user.get();
            validUser.setIsActive(true);
            List<String>roleList = makeRoleList(validUser);
            return roleList;
        }else{
            return null;
        }
    }

    private List<String> makeRoleList(UserProperty validUser) {
        List<String>roles = new ArrayList<>();
        for(RoleType role : validUser.getRoleTypes()){
            roles.add(role.getDisplayName());
        }
        return roles;
    }
}
