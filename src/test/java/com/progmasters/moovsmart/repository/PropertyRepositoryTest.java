package com.progmasters.moovsmart.repository;

import com.progmasters.moovsmart.domain.Property;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class PropertyRepositoryTest {

    @Autowired
    private PropertyRepository propertyRepository;

    @Test
    public void testSaveAndFindAllByIsValid() {
        //given
        Property property = new Property();
        property.setName("Ház");




        propertyRepository.save(property);

        //when
        List<Property> properties = propertyRepository.findAllByIsValid();

        //then
        assertEquals(1, properties.size());
    }
}
