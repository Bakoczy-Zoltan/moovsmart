package com.progmasters.moovsmart.service;

import com.progmasters.moovsmart.domain.UserProperty;
import com.progmasters.moovsmart.dto.CreateUserCommand;
import com.progmasters.moovsmart.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long makeUser(CreateUserCommand command) {
        this.userRepository.save(new UserProperty(command));
        Long id = null;
        Optional<UserProperty> user = this.userRepository.findUserPropertiesByMail(command.getMail());
        if (user.isPresent()) {
            id = user.get().getId();
        }
        return id;
    }

    public ResponseEntity validateUser(Long id) {
        Optional<UserProperty>user = this.userRepository.findById(id);
        if(user.isPresent()){
            UserProperty validUser = user.get();
            validUser.setActive(true);
            return new ResponseEntity(HttpStatus.CREATED);
        }else{
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
