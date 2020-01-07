package com.progmasters.moovsmart.service;

import com.progmasters.moovsmart.domain.RoleType;
import com.progmasters.moovsmart.domain.UserProperty;
import com.progmasters.moovsmart.dto.CreateUserCommand;
import com.progmasters.moovsmart.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long makeUser(CreateUserCommand command) {
        Long id = null;
        if (this.userRepository.findUserPropertiesByMail(command.getMail()).isEmpty()) {
            this.userRepository.save(new UserProperty(command));
            Optional<UserProperty> user = this.userRepository.findUserPropertiesByMail(command.getMail());
            if (user.isPresent()) {
                id = user.get().getId();
            }
        }
        return id;
    }

    public ResponseEntity<List<String>> validateUser(Long id) {
        Optional<UserProperty>user = this.userRepository.findById(id);
        if(user.isPresent()){
            UserProperty validUser = user.get();
            validUser.setIsActive(true);
            List<String>roleList = makeRoleList(validUser);
            return new ResponseEntity<>(roleList,HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
