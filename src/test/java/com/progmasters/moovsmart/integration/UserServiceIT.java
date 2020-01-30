package com.progmasters.moovsmart.integration;

import com.progmasters.moovsmart.domain.*;
import com.progmasters.moovsmart.dto.CreateUserCommand;
import com.progmasters.moovsmart.dto.PropertyForm;
import com.progmasters.moovsmart.dto.UserDetails;
import com.progmasters.moovsmart.repository.PropertyRepository;
import com.progmasters.moovsmart.repository.UserRepository;
import com.progmasters.moovsmart.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserServiceIT {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PropertyRepository propertyRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private UserService userService;
    private CreateUserCommand createUserCommand;

    @BeforeEach
    public void init() {
        this.userService = new UserService(userRepository, passwordEncoder, propertyRepository);

        createUserCommand = new CreateUserCommand();
        createUserCommand.setUserName("XYZ");
        createUserCommand.setMail("xy@xy.com");
        createUserCommand.setPassword("pw11");
        createUserCommand.setRole(Arrays.asList("ROLE_USER"));

    }

    @Test
    public void testMakeUser() {

        Long id = userService.makeUser(createUserCommand);

        UserProperty userProperty = userRepository.findById(id).get();

        assertEquals(createUserCommand.getMail(), userProperty.getMail());

    }

    @Test
    public void testValidateUser() {
        UserProperty userProperty = new UserProperty(createUserCommand);

        userRepository.save(userProperty);
        Long id = userRepository.findUserPropertiesByMail(userProperty.getMail()).get().getId();

        assertFalse(userRepository.findById(id).get().getIsActive());

        userService.validateUser(id);

        assertTrue(userRepository.findById(id).get().getIsActive());
    }

    @Test
    public void testBanUserById() {
        UserProperty userProperty = new UserProperty(createUserCommand);
        userProperty.setIsActive(true);
        userRepository.save(userProperty);
        Long id = userRepository.findUserPropertiesByMail(userProperty.getMail()).get().getId();

        Long invalidId = 111L;
        if (userRepository.findById(invalidId).isEmpty()) {
            assertFalse(userService.banUserById(invalidId));
        }
        assertTrue(userService.banUserById(id));
        assertFalse(userRepository.findById(id).get().getIsActive());
    }

    @Test
    public void testBannedUserRepositories() {
        UserProperty userProperty = new UserProperty(createUserCommand);
        userProperty.setIsActive(true);
        userRepository.save(userProperty);

        Property property1 = new Property();
        property1.setName("Ház");
        property1.setCounty(County.BUDAPEST);
        property1.setPropertyType(PropertyType.HOUSE);
        property1.setPropertyState(PropertyState.RENEWABLE);
        property1.setStatus(StatusOfProperty.ACCEPTED);
        property1.setOwner(userProperty);
        propertyRepository.save(property1);

        Long id = userRepository.findUserPropertiesByMail(userProperty.getMail()).get().getId();

        userService.banUserById(id);

        List<Property> bannedProperties = propertyRepository.findAllByOwner(userProperty);

        assertFalse(bannedProperties.get(0).isValid());
        assertEquals(StatusOfProperty.FORBIDDEN, bannedProperties.get(0).getStatus());

    }

    @Test
    public void testPermitUserById() {
        UserProperty userProperty = new UserProperty(createUserCommand);
        userRepository.save(userProperty);
        Long id = userRepository.findUserPropertiesByMail(userProperty.getMail()).get().getId();

        Long invalidId = 111L;
        if (userRepository.findById(invalidId).isEmpty()) {
            assertFalse(userService.permitUserById(invalidId));
        }
        assertTrue(userService.permitUserById(id));
        assertTrue(userRepository.findById(id).get().getIsActive());

    }

    @Test
    public void testPermittedUserRepositories() {
        UserProperty userProperty = new UserProperty(createUserCommand);
        userRepository.save(userProperty);

        Property property1 = new Property();
        property1.setName("Ház");
        property1.setCounty(County.BUDAPEST);
        property1.setPropertyType(PropertyType.HOUSE);
        property1.setPropertyState(PropertyState.RENEWABLE);
        property1.setOwner(userProperty);
        property1.setStatus(StatusOfProperty.FORBIDDEN);
        propertyRepository.save(property1);

        Long id = userRepository.findUserPropertiesByMail(userProperty.getMail()).get().getId();

        userService.permitUserById(id);

        List<Property> permittedProperties = propertyRepository.findAllByOwner(userProperty);

        assertTrue(permittedProperties.get(0).isValid());
        assertEquals(StatusOfProperty.HOLDING, permittedProperties.get(0).getStatus());

    }

    @Test
    public void testgetUserByMail() {
        UserProperty userProperty = new UserProperty(createUserCommand);
        userRepository.save(userProperty);

        UserDetails userDetails = userService.getUserByMail(userProperty.getMail());

        assertEquals(userProperty.getUserName(), userDetails.getUserName());
    }
}
