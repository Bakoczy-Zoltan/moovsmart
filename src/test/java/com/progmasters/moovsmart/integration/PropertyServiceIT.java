package com.progmasters.moovsmart.integration;

import com.progmasters.moovsmart.domain.*;
import com.progmasters.moovsmart.dto.*;
import com.progmasters.moovsmart.repository.PropertyRepository;
import com.progmasters.moovsmart.repository.UserRepository;
import com.progmasters.moovsmart.service.PropertyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

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
        List<PropertyListItem> properties = propertyService.getProperties();
        Long propertyId = properties.get(0).getId();

        PropertyDetails propertyDetails = propertyService.getPropertyDetails(propertyId);

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

        List<PropertyListItem> properties = propertyService.getProperties();
        Long propertyId = properties.get(0).getId();

        PropertyForm propUpdate = new PropertyForm();
        propUpdate.setName("Nagy ház");
        propUpdate.setArea(150.0);
        propUpdate.setBuildingYear(1999);
        propUpdate.setCounty("BUDAPEST");
        propUpdate.setPropertyType("HOUSE");
        propUpdate.setPropertyState("RENEWED");

        Property updatedProperty = propertyService.updateProperty(propUpdate, propertyId, user.getMail());

        assertEquals(propUpdate.getName(), updatedProperty.getName());
        assertEquals("Budapest", updatedProperty.getCounty().getDisplayName());
        assertEquals("Ház", updatedProperty.getPropertyType().getDisplayName());
        assertEquals("Felújított", updatedProperty.getPropertyState().getDisplayName());

    }

    @Test
    public void testDeleteProperty() {
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

        List<PropertyListItem> properties = propertyService.getProperties();
        Long propertyId = properties.get(0).getId();

        boolean isDeleted = propertyService.deleteProperty(propertyId, user.getMail());

        assertTrue(isDeleted);
        assertFalse(userRepository.findUserPropertiesByMail(user.getMail()).get().getIsActive());
    }

    @Test
    public void testGetFilteredList_WithoutRoom() throws Exception {
        UserProperty user = new UserProperty();
        user.setMail("xy@xy.com");
        user.setId(1L);
        userRepository.save(user);

        PropertyForm property1 = new PropertyForm();
        property1.setName("House1");
        property1.setNumberOfRooms(2);
        property1.setArea(50.0);
        property1.setPrice(10000000);
        property1.setPropertyType("HOUSE");
        property1.setPropertyState("NEW");
        property1.setCity("Budapest");
        property1.setCounty("BUDAPEST");

        propertyService.createProperty(property1, user.getMail());

        PropertyForm property2 = new PropertyForm();
        property2.setName("House2");
        property2.setNumberOfRooms(3);
        property2.setArea(80.0);
        property2.setPrice(30000000);
        property2.setPropertyType("HOUSE");
        property2.setPropertyState("NEW");
        property2.setCounty("FEJER");
        property2.setCity("Székesfehérvár");

        propertyService.createProperty(property2, user.getMail());

        CreateFilteredCommand createFilteredCommand = new CreateFilteredCommand();
        createFilteredCommand.setMinPrice(5000000);
        createFilteredCommand.setMaxPrice(35000000);
        createFilteredCommand.setMinSize(40.0);
        createFilteredCommand.setMaxSize(100.0);
//        createFilteredCommand.setPropertyState(PropertyState.NEW);
//        createFilteredCommand.setPropertyType(PropertyType.HOUSE);
        List<PropertyListItem> propertyListItems = propertyService.getFilteredPropertiesWithoutRooms(createFilteredCommand);
        assertEquals(2, propertyListItems.size());

        createFilteredCommand.setCity("Székesfehérvár");
        propertyListItems = propertyService.getFilteredPropertiesWithoutRooms(createFilteredCommand);
        assertEquals(1, propertyListItems.size());

    }

    @Test
    public void testGetFilteredList() throws Exception {
        UserProperty user = new UserProperty();
        user.setMail("xy@xy.com");
        user.setId(1L);
        userRepository.save(user);

        PropertyForm property1 = new PropertyForm();
        property1.setName("House1");
        property1.setNumberOfRooms(2);
        property1.setArea(50.0);
        property1.setPrice(10000000);
        property1.setPropertyType("HOUSE");
        property1.setPropertyState("NEW");
        property1.setCity("Budapest");
        property1.setCounty("BUDAPEST");

        propertyService.createProperty(property1, user.getMail());

        PropertyForm property2 = new PropertyForm();
        property2.setName("House2");
        property2.setNumberOfRooms(3);
        property2.setArea(80.0);
        property2.setPrice(30000000);
        property2.setPropertyType("HOUSE");
        property2.setPropertyState("NEW");
        property2.setCounty("FEJER");
        property2.setCity("Székesfehérvár");

        propertyService.createProperty(property2, user.getMail());

        CreateFilteredCommand createFilteredCommand = new CreateFilteredCommand();
        createFilteredCommand.setMinPrice(5000000);
        createFilteredCommand.setMaxPrice(35000000);
        createFilteredCommand.setMinSize(40.0);
        createFilteredCommand.setMaxSize(100.0);
        createFilteredCommand.setNumberOfRooms(3);

        List<PropertyListItem> propertyListItems = propertyService.getFilteredProperties(createFilteredCommand);
        assertEquals(1, propertyListItems.size());

        createFilteredCommand.setCity("Budapest");
        propertyListItems = propertyService.getFilteredProperties(createFilteredCommand);
        assertEquals(0, propertyListItems.size());

    }

    @Test
    public void testGetOwnProperties() {
        PropertyForm property1 = new PropertyForm();
        property1.setName("Ház");
        property1.setCounty("BUDAPEST");
        property1.setPropertyType("HOUSE");
        property1.setPropertyState("RENEWABLE");

        PropertyForm property2 = new PropertyForm();
        property2.setName("Ház2");
        property2.setCounty("BARANYA");
        property2.setPropertyType("APARTMENT");
        property2.setPropertyState("RENEWED");

        UserProperty user = new UserProperty();
        user.setMail("xy@xy.com");
        user.setId(1L);

        userRepository.save(user);

        propertyService.createProperty(property1, user.getMail());
        propertyService.createProperty(property2, user.getMail());

        List<PropertyListItem> properties = propertyService.getOwnProperties(user.getMail());

        assertEquals(2, properties.size());
    }

    @Test
    public void testGetAllHoldingProperty() {
        PropertyForm property1 = new PropertyForm();
        property1.setName("Ház");
        property1.setCounty("BUDAPEST");
        property1.setPropertyType("HOUSE");
        property1.setPropertyState("RENEWABLE");

        UserProperty user = new UserProperty();
        user.setMail("xy@xy.com");
        user.setId(1L);

        userRepository.save(user);

        propertyService.createProperty(property1, user.getMail());

        List<PropertyForm> properties = propertyService.getAllHoldingProperty();

        assertEquals(1, properties.size());

        Long propertyId = propertyService.getProperties().get(0).getId();
        propertyService.activateProperty(propertyId);

        properties = propertyService.getAllHoldingProperty();

        assertEquals(0, properties.size());
    }

    @Test
    public void testGetArchivedProperties() {
        Property property1 = new Property();
        property1.setName("Ház");
        property1.setCounty(County.valueOf("BUDAPEST"));
        property1.setPropertyType(PropertyType.valueOf("HOUSE"));
        property1.setPropertyState(PropertyState.valueOf("RENEWABLE"));
        PropertyForm pf1 = new PropertyForm(property1);

        UserProperty user = new UserProperty();
        user.setMail("xy@xy.com");
        user.setId(1L);

        userRepository.save(user);

        propertyService.createProperty(pf1, user.getMail());

        CreateQueryByDatesCommand command = new CreateQueryByDatesCommand(
                LocalDateTime.of(2020, Month.JANUARY, 01, 19, 30, 40),
                LocalDateTime.of(2020, Month.JANUARY, 15, 19, 30, 40));

        List<PropertyForm> archivedProperties = propertyService.getArchivedProperties(command);

        assertEquals(0, archivedProperties.size());

        List<PropertyListItem> properties = propertyService.getProperties();
        Property property = propertyRepository.findById(properties.get(0).getId()).get();
        property.setLocalDateTime(LocalDateTime.of(2020, Month.JANUARY, 5, 19, 30, 40));
        property.setValid(false);
        propertyRepository.save(property);

        archivedProperties = propertyService.getArchivedProperties(command);

        assertEquals(1, archivedProperties.size());
    }
}
