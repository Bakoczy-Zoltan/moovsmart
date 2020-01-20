package com.progmasters.moovsmart.repository;

import com.progmasters.moovsmart.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class PropertyRepositoryTest {

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSave() {
        //given
        UserProperty user = new UserProperty();
        user.setMail("xy@xy.com");

        userRepository.save(user);

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
        property.setLatCoord(47.507855);
        property.setLngCoord(18.987466);
        property.setValid(true);
        property.setLocalDateTime(LocalDateTime.now());
        property.setOwner(user);
        property.setImageUrls(Arrays.asList("image.jpg"));

        propertyRepository.save(property);

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
        assertEquals(user.getMail(), property.getOwner().getMail());
    }

    @Test
    public void testSaveAndFindAllByIsValid() {
        Property property1 = new Property();
        property1.setName("house1");
        property1.setValid(true);
        Property property2 = new Property();
        property2.setName("house2");
        property2.setValid(true);
        Property property3 = new Property();
        property3.setName("house3");
        property3.setValid(false);

        propertyRepository.save(property1);
        propertyRepository.save(property2);
        propertyRepository.save(property3);

        List<Property> properties = propertyRepository.findAllByIsValid();

        assertEquals(2, properties.size());
    }


    @Test
    public void testFindAllByOwner() {
        UserProperty user1 = new UserProperty();
        user1.setMail("user1@gmail.com");
        UserProperty user2 = new UserProperty();
        user1.setMail("user2@gmail.com");

        userRepository.save(user1);
        userRepository.save(user2);

        Property property1 = new Property();
        property1.setName("Ház1");
        property1.setOwner(user1);
        Property property2 = new Property();
        property2.setName("Ház2");
        property2.setOwner(user1);
        Property property3 = new Property();
        property3.setName("Ház3");
        property3.setOwner(user2);

        propertyRepository.save(property1);
        propertyRepository.save(property2);
        propertyRepository.save(property3);

        List<Property> propertyUser1 = propertyRepository.findAllByOwner(user1);
        List<Property> propertyUser2 = propertyRepository.findAllByOwner(user2);

        assertEquals(2, propertyUser1.size());
        assertEquals(1, propertyUser2.size());
    }

    @Test
    public void testGetAllCity() {
        Property property1 = new Property();
        property1.setCity("Budapest");
        property1.setValid(true);
        Property property2 = new Property();
        property2.setCity("Vác");
        property2.setValid(true);
        Property property3 = new Property();
        property3.setCity("Békéscsaba");
        property3.setValid(true);

        propertyRepository.save(property1);
        propertyRepository.save(property2);
        propertyRepository.save(property3);

        List<String> cities = propertyRepository.getAllCity();

        assertEquals(3, cities.size());
        assertTrue(cities.contains(property1.getCity()));
        assertTrue(cities.contains(property2.getCity()));
        assertTrue(cities.contains(property3.getCity()));

    }

    @Test
    public void testGetFilteredProperties() {
        Property property1 = new Property();
        property1.setName("Ház");
        property1.setArea(150.0);
        property1.setNumberOfRooms(5);
        property1.setBuildingYear(1999);
        property1.setCounty(County.BUDAPEST);
        property1.setPropertyType(PropertyType.HOUSE);
        property1.setPropertyState(PropertyState.RENEWABLE);
        property1.setCity("Budapest");
        property1.setPrice(10000000);
        property1.setDescription("");
        property1.setValid(true);

        propertyRepository.save(property1);

        List<Property> properties1 = propertyRepository.getFilteredProperties(50.0, 200.0,
                1000000, 15000000, PropertyState.RENEWABLE, PropertyType.HOUSE,
                "Budapest", 5);

        assertEquals(1, properties1.size());

//        List<Property> properties2 = propertyRepository.getFilteredProperties(0.0, 0.0,
//                0, 0, null, null,
//                null, 5);
//
//        assertEquals(1, properties2.size());

        List<Property> properties3 = propertyRepository.getFilteredProperties(0.0, 0.0,
                0, 0, null, null,
                null, 1);

        assertEquals(0, properties3.size());
    }
}
