package com.progmasters.moovsmart.integration;

import com.progmasters.moovsmart.domain.*;
import com.progmasters.moovsmart.dto.PropertyDetails;
import com.progmasters.moovsmart.dto.PropertyForm;
import com.progmasters.moovsmart.dto.PropertyListItem;
import com.progmasters.moovsmart.repository.PropertyRepository;
import com.progmasters.moovsmart.repository.UserRepository;
import com.progmasters.moovsmart.service.PropertyService;
import org.junit.jupiter.api.BeforeEach;
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
public class PropertyServiceIT {

    @Autowired
    PropertyRepository propertyRepository;

    @Autowired
    UserRepository userRepository;

    private PropertyService propertyService;

    @BeforeEach
    public void init() {
        this.propertyService = new PropertyService(propertyRepository, userRepository);
    }

    @Test
    public void testGetPropertyDetails(){
        PropertyForm property = new PropertyForm();
        property.setName("Ház");
        property.setCounty("BUDAPEST");
        property.setPropertyType("HOUSE");
        property.setPropertyState("RENEWABLE");

        UserProperty user = new UserProperty();
        user.setMail("xy@xy.com");
        user.setId(1L);

        userRepository.save(user);
        propertyService.createProperty(property, user.getMail());

        PropertyDetails propertyDetails = propertyService.getPropertyDetails(1L);

        assertEquals("Ház", propertyDetails.getName());
        assertEquals("Budapest", propertyDetails.getCounty());
        assertEquals("Felújítandó", propertyDetails.getPropertyState());
    }

    @Test
    public void testCreateAndGetProperties(){
        PropertyForm property = new PropertyForm();
        property.setName("Ház");
        property.setArea(150.0);
        property.setNumberOfRooms(5);
        property.setBuildingYear(1999);
        property.setCounty("BUDAPEST");
        property.setPropertyType("HOUSE");
        property.setPropertyState("RENEWABLE");
        property.setCity("Budapest");
        property.setStreet("Csipke út");
        property.setStreetNumber("3");
        property.setZipCode(1125);
        property.setPrice(10000000);
        property.setDescription("");
        property.setImageUrl(Arrays.asList("image.jpg"));
        property.setLatCoord(47.507855);
        property.setLngCoord(18.987466);

        UserProperty user = new UserProperty();
        user.setMail("xy@xy.com");
        user.setId(1L);

        userRepository.save(user);

        propertyService.createProperty(property, user.getMail());

        List<PropertyListItem> properties = propertyService.getProperties();

        assertEquals(1, properties.size());
        assertEquals("Ház", properties.get(0).getName());
        assertEquals("image.jpg", properties.get(0).getImageUrl().get(0));
    }

    @Test
    public void testUpdateProperty() {
        PropertyForm property = new PropertyForm();
        property.setName("Ház");
        property.setArea(150.0);
        property.setBuildingYear(1999);
        property.setCounty("PEST");
        property.setPropertyType("HOUSE");
        property.setPropertyState("RENEWABLE");

        UserProperty user = new UserProperty();
        user.setMail("xy@xy.com");
        user.setId(1L);

        userRepository.save(user);
        propertyService.createProperty(property, user.getMail());

        PropertyForm propUpdate = new PropertyForm();
        propUpdate.setName("Nagy ház");
        propUpdate.setArea(150.0);
        propUpdate.setBuildingYear(1999);
        propUpdate.setCounty("BUDAPEST");
        propUpdate.setPropertyType("HOUSE");
        propUpdate.setPropertyState("RENEWED");

        Property updatedProperty = propertyService.updateProperty(propUpdate, 1L, user.getMail());

        assertEquals(propUpdate.getName(), updatedProperty.getName());
        assertEquals("Budapest", updatedProperty.getCounty().getDisplayName());
        assertEquals("Ház", updatedProperty.getPropertyType().getDisplayName());
        assertEquals("Felújított", updatedProperty.getPropertyState().getDisplayName());

    }
}
