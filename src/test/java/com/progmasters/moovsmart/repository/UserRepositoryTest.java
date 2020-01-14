package com.progmasters.moovsmart.repository;

import com.progmasters.moovsmart.domain.UserProperty;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveAndFindUserPropertiesByMail() {

        UserProperty userProperty = new UserProperty();
        userProperty.setMail("xy@xy.com");
        userProperty.setUserName("XYZ");

        userRepository.save(userProperty);

        Optional<UserProperty> optUser = userRepository.findUserPropertiesByMail(userProperty.getMail());

        assertNotNull(optUser);
        assertEquals(userProperty.getUserName(), optUser.get().getUserName());
    }
}
