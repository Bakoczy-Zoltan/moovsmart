package com.progmasters.moovsmart.repository;

import com.progmasters.moovsmart.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class PropertyRepositoryTest {

//    @Autowired
//    private TestEntityManager entityManager;

    @Autowired
    private PropertyRepository propertyRepository;

    @Test
    public void testSaveAndFindAllByIsValid() {
        //given
        Property property = new Property();
        property.setName("Ház");
        property.setArea(150.0);
        property.setNumberOfRooms(5);
        property.setBuildingYear(1999);
        property.setCounty(County.BUDAPEST);
        property.setPropertyType(PropertyType.HOUSE);
        property.setPropertyState(PropertyState.RENEWABLE);
        property.setCity("Budapest");
        property.setStreet("Csipke út");
        property.setStreetNumber("3");
        property.setZipCode(1125);
        property.setPrice(10000000);
        property.setDescription("");
////        property.setOwner(new UserProperty());
        property.setValid(true);
        property.setImageUrls(Arrays.asList("image.jpg"));
//
        propertyRepository.save(property);
//        entityManager.persist(property);
//        entityManager.flush();

        //when
        List<Property> properties = propertyRepository.findAllByIsValid();

        //then
        assertEquals(1, properties.size());
        assertEquals("Ház", properties.get(0).getName());

        assertEquals(property.getName(), properties.get(0).getName());
        assertEquals(property.getArea(), properties.get(0).getArea());
        assertEquals(property.getNumberOfRooms(), properties.get(0).getNumberOfRooms());
        assertEquals(property.getBuildingYear(), properties.get(0).getBuildingYear());
        assertEquals(property.getCounty(), properties.get(0).getCounty());
        assertEquals(property.getPropertyType(), properties.get(0).getPropertyType());
        assertEquals(property.getPropertyState(), properties.get(0).getPropertyState());
        assertEquals(property.getCity(), properties.get(0).getCity());
        assertEquals(property.getStreet(), properties.get(0).getStreet());
        assertEquals(property.getStreetNumber(), properties.get(0).getStreetNumber());
        assertEquals(property.getZipCode(), properties.get(0).getZipCode());
        assertEquals(property.getPrice(), properties.get(0).getPrice());
        assertEquals(property.getDescription(), properties.get(0).getDescription());
        assertEquals(property.isValid(), properties.get(0).isValid());
        assertEquals(property.getImageUrls(), properties.get(0).getImageUrls());
    }

    @Test
    public void testFindAllByOwner() {
        UserProperty user1 = new UserProperty();
        user1.setId(1L);
//        UserProperty user2 = new UserProperty();
//        user2.setId(2L);
        Property property1 = new Property();
        property1.setName("Ház1");
        property1.setOwner(user1);
//        Property property2 = new Property();
//        property2.setName("Ház2");
//        property2.setOwner(user1);
//        Property property3 = new Property();
//        property3.setName("Ház3");
//        property3.setOwner(user2);

        propertyRepository.save(property1);
//        propertyRepository.save(property2);
//        propertyRepository.save(property3);

        List<Property> propertyUser1 = propertyRepository.findAllByOwner(user1);
//        List<Property> propertyUser2 = propertyRepository.findAllByOwner(user2);

        assertEquals(1, propertyUser1.size());
//        assertEquals(1, propertyUser2.size());

    }
}
