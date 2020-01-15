package com.progmasters.moovsmart.controller;

import com.progmasters.moovsmart.domain.Property;
import com.progmasters.moovsmart.domain.PropertyType;
import com.progmasters.moovsmart.dto.PropertyListItem;
import com.progmasters.moovsmart.exception.GlobalExceptionHandler;
import com.progmasters.moovsmart.repository.PropertyRepository;
import com.progmasters.moovsmart.service.PropertyService;
import com.progmasters.moovsmart.validation.PropertyFormValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

//@ExtendWith(MockitoExtension.class)
public class PropertyControllerTest {
    private MockMvc mockMvc;

    @Mock
    PropertyRepository propertyRepositoryMock;

    @Mock
    private PropertyService propertyServiceMock;

    @BeforeEach
    public void setUp() {
        PropertyFormValidator propertyFormValidator = new PropertyFormValidator(propertyRepositoryMock);

        PropertyController propertyController = new PropertyController(propertyServiceMock, propertyFormValidator);

        mockMvc = MockMvcBuilders.standaloneSetup(propertyController)
                .setControllerAdvice(new GlobalExceptionHandler(messageSource()))
                .build();
    }

    @AfterEach
    public void validate() {
        validateMockitoUsage();
    }

    @Test
    public void testGetProperties() throws Exception {
        // given
        Property property1 = new Property();
        property1.setName("House1");
        property1.setPropertyType(PropertyType.HOUSE);
        property1.setPrice(10000000);

        Property property2 = new Property();
        property2.setName("House2");
        property2.setPropertyType(PropertyType.APARTMENT);

//        List<PropertyListItem> properties = Stream.of(property1, property2)
//                .map(PropertyListItem::new).collect(Collectors.toList());

        // when
//        when(propertyServiceMock.getProperties()).thenReturn(properties);

        // then
//        this.mockMvc.perform(get("/api/properties"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$", hasSize(2)))
//                .andExpect(jsonPath("$[0].name", is("House1")))
//                .andExpect(jsonPath("$[0].propertyType", is("Ház")))
//                .andExpect(jsonPath("$[0].price", is(10000000)))
//                .andExpect(jsonPath("$[1].name", is("House2")))
//                .andExpect(jsonPath("$[1].propertyType", is("Lakás")));
//
//        verify(propertyServiceMock, times(1)).getProperties();
//        verifyNoMoreInteractions(propertyServiceMock);



    }

    private MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();

        messageSource.setBasename("messages");
        messageSource.setUseCodeAsDefaultMessage(true);

        return messageSource;
    }
}
