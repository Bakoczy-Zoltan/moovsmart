package com.progmasters.moovsmart.service;

import com.progmasters.moovsmart.domain.Property;
import com.progmasters.moovsmart.domain.RoleType;
import com.progmasters.moovsmart.domain.StatusOfProperty;
import com.progmasters.moovsmart.domain.UserProperty;
import com.progmasters.moovsmart.dto.CreateUserCommand;
import com.progmasters.moovsmart.dto.UserDetails;
import com.progmasters.moovsmart.repository.PropertyRepository;
import com.progmasters.moovsmart.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    private PropertyRepository propertyRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       PropertyRepository propertyRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.propertyRepository = propertyRepository;
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

    public Boolean banUserById(Long id) {
        Optional<UserProperty>tempUser = this.userRepository.findById(id);
        if(tempUser.isPresent()){
            UserProperty user = tempUser.get();
            user.setIsActive(false);
            this.userRepository.save(user);

            makePropertiesOfBannedUserInvalid(id);

            return true;
        }else {
            return false;
        }
    }

    private void makePropertiesOfBannedUserInvalid(Long id) {
        Optional<UserProperty>tempUser = this.userRepository.findById(id);
        if(tempUser.isPresent()){
            UserProperty user = tempUser.get();
            List<Property>properties = this.propertyRepository.findAllByOwner(user);

            if(properties!= null && properties.size() > 0){
                for(Property property: properties){
                    if(!property.getStatus().equals(StatusOfProperty.ARCHIVED)){
                        property.setStatus(StatusOfProperty.FORBIDDEN);
                        property.setValid(false);
                    }
                    this.propertyRepository.save(property);
                }
            }

        }

    }

    public Boolean permitUserById(Long id) {
        Optional<UserProperty>tempUser = this.userRepository.findById(id);
        if(tempUser.isPresent()){
            UserProperty user = tempUser.get();
            user.setIsActive(true);
            this.userRepository.save(user);

            makePropertiesOfPermittedUserValid(user);

            return true;
        }else {
            return false;
        }
    }

    private void makePropertiesOfPermittedUserValid(UserProperty user) {
        List<Property> properties = this.propertyRepository.findAllByOwner(user);
        if (properties != null && properties.size() > 0) {
            for (Property property : properties) {

                if (property.getStatus().equals(StatusOfProperty.FORBIDDEN)) {
                    property.setValid(true);
                    property.setStatus(StatusOfProperty.HOLDING);
                }
                this.propertyRepository.save(property);
            }
        }
    }

    public UserDetails getUserByMail(String mail) {
        Optional<UserProperty> tempUser = this.userRepository.findAllByMail(mail);
        UserDetails userToSend = null;
        if (tempUser.isPresent()){
            UserProperty user = tempUser.get();
            userToSend = new UserDetails(user);
        }
        return userToSend;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }
}
