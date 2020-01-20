package com.progmasters.moovsmart.service;

import com.progmasters.moovsmart.domain.Property;
import com.progmasters.moovsmart.domain.PropertyState;
import com.progmasters.moovsmart.dto.PropertyForm;
import com.progmasters.moovsmart.repository.PropertyRepository;
import com.progmasters.moovsmart.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PropertyServiceTest {

    private PropertyService propertyService;

    @Mock
    private PropertyRepository propertyRepositoryMock;

    @Mock
    private UserRepository userRepositoryMock;

    @BeforeEach
    public void setUp() {
        propertyService = new PropertyService(propertyRepositoryMock, userRepositoryMock);
    }

    @Test
    public void testUpdateProperty() {
        //given
        Property property = new Property();
        property.setName("house1");
        property.setPropertyState(PropertyState.RENEWABLE);
        property.setArea(50.0);
        property.setPrice(10000000);

        PropertyForm propertyForm = new PropertyForm();
        propertyForm.setName("updateHouse1");
        propertyForm.setPropertyState("RENEWED");
        propertyForm.setPropertyType("HOUSE");
        propertyForm.setCounty("BUDAPEST");
        propertyForm.setArea(75.0);
        propertyForm.setPrice(2000000);

        when(propertyRepositoryMock.findById(1L)).thenReturn(Optional.of(property));
        when(propertyRepositoryMock.save(any(Property.class))).thenAnswer(returnsFirstArg());

        //when
        Property updatedProperty = propertyService.updateProperty(propertyForm, 1L, "xy@mail.com");

         //then
        assertEquals("updateHouse1", updatedProperty.getName());
        assertEquals(PropertyState.RENEWED, updatedProperty.getPropertyState());

    }

}
