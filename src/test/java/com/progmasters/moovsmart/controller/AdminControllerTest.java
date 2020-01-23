package com.progmasters.moovsmart.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.progmasters.moovsmart.domain.County;
import com.progmasters.moovsmart.domain.Property;
import com.progmasters.moovsmart.domain.PropertyState;
import com.progmasters.moovsmart.domain.PropertyType;
import com.progmasters.moovsmart.dto.CreateQueryByDatesCommand;
import com.progmasters.moovsmart.dto.PropertyForm;
import com.progmasters.moovsmart.dto.PropertyListItem;
import com.progmasters.moovsmart.exception.GlobalExceptionHandler;
import com.progmasters.moovsmart.service.MailSenderService;
import com.progmasters.moovsmart.service.PropertyService;
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

import javax.persistence.Table;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
public class AdminControllerTest {
    private MockMvc mockMvc;

    @Mock
    private PropertyService propertyServiceMock;

    @Mock
    private MailSenderService mailSenderServiceMock;

    @BeforeEach
    public void setUp() {
        AdminController adminController = new AdminController(propertyServiceMock, mailSenderServiceMock);

        mockMvc = MockMvcBuilders.standaloneSetup(adminController)
                .setControllerAdvice(new GlobalExceptionHandler(messageSource()))
                .build();

    }

    @AfterEach
    public void validate() {
        validateMockitoUsage();
    }

    @Test
    public void testGetAllHoldingProperty() throws Exception {
        // given
        Property property1 = new Property();
        property1.setId(1L);
        property1.setName("House1");
        property1.setNumberOfRooms(2);
        property1.setArea(50.0);
        property1.setPrice(10000000);
        property1.setPropertyType(PropertyType.HOUSE);
        property1.setPropertyState(PropertyState.RENEWABLE);
        property1.setCounty(County.BARANYA);

        Property property2 = new Property();
        property2.setId(2L);
        property2.setName("House2");
        property2.setNumberOfRooms(3);
        property2.setArea(80.0);
        property2.setPrice(30000000);
        property2.setPropertyType(PropertyType.HOUSE);
        property2.setPropertyState(PropertyState.NEW);
        property2.setCounty(County.BEKES);

        List<PropertyForm> propertyForms = Stream.of(property1, property2).map(PropertyForm::new).collect(Collectors.toList());

        //when
        when(propertyServiceMock.getAllHoldingProperty()).thenReturn(propertyForms);

        // then
        this.mockMvc.perform(get("/api/properties/admin/propertyListForApproval"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("House1")))
                .andExpect(jsonPath("$[0].price", is(10000000)))
                .andExpect(jsonPath("$[0].numberOfRooms", is(2)))
                .andExpect(jsonPath("$[0].area", is(50.0)))
                .andExpect(jsonPath("$[1].name", is("House2")))
                .andExpect(jsonPath("$[1].price", is(30000000)))
                .andExpect(jsonPath("$[1].numberOfRooms", is(3)))
                .andExpect(jsonPath("$[1].area", is(80.0)));

        verify(propertyServiceMock, times(1)).getAllHoldingProperty();
        verifyNoMoreInteractions(propertyServiceMock);

    }

    @Test
    public void testGetPropertyDetailsForApproval() throws Exception {
        // given
        Property property1 = new Property();
        property1.setId(1L);
        property1.setName("House1");
        property1.setNumberOfRooms(2);
        property1.setArea(50.0);
        property1.setPrice(10000000);
        property1.setPropertyType(PropertyType.HOUSE);
        property1.setPropertyState(PropertyState.RENEWABLE);
        property1.setCounty(County.BARANYA);

        PropertyForm propertyForm = new PropertyForm(property1);

        //when
        when(propertyServiceMock.getPropertyDetailsForApproval(any(Long.class))).thenReturn(propertyForm);

        //then
        this.mockMvc.perform(get("/api/properties/admin/propertyDetailsForApproval/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("House1")))
                .andExpect(jsonPath("$.price", is(10000000)))
                .andExpect(jsonPath("$.numberOfRooms", is(2)))
                .andExpect(jsonPath("$.area", is(50.0)));

        verify(propertyServiceMock, times(1)).getPropertyDetailsForApproval(any(Long.class));
        verifyNoMoreInteractions(propertyServiceMock);

    }

    @Test
    public void testGetArchivedProperties() throws Exception {
        // given
        Property property1 = new Property();
        property1.setId(1L);
        property1.setName("House1");
        property1.setNumberOfRooms(2);
        property1.setArea(50.0);
        property1.setPrice(10000000);
        property1.setPropertyType(PropertyType.HOUSE);
        property1.setPropertyState(PropertyState.RENEWABLE);
        property1.setCounty(County.BARANYA);
        property1.setLocalDateTime(LocalDateTime.of(2020, Month.JANUARY,05, 11,20,0));

        Property property2 = new Property();
        property2.setId(2L);
        property2.setName("House2");
        property2.setNumberOfRooms(3);
        property2.setArea(80.0);
        property2.setPrice(30000000);
        property2.setPropertyType(PropertyType.HOUSE);
        property2.setPropertyState(PropertyState.NEW);
        property2.setCounty(County.BEKES);
        property2.setLocalDateTime(LocalDateTime.of(2020, Month.JANUARY,12, 11,20,0));

        List<PropertyForm> propertyForms = Stream.of(property1, property2).map(PropertyForm::new).collect(Collectors.toList());

        CreateQueryByDatesCommand createQueryByDatesCommand = new CreateQueryByDatesCommand(
                LocalDateTime.of(2020, Month.JANUARY, 01, 19, 30, 40),
                LocalDateTime.of(2020, Month.JANUARY, 15, 19, 30, 40));

        //when
//        when(propertyServiceMock.getArchivedProperties(createQueryByDatesCommand)).thenReturn(propertyForms);

        // then

//        this.mockMvc.perform(post("/api/properties/admin/getArchivedProperties")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(asJsonString(createQueryByDatesCommand)))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$", hasSize(2)))
//                .andExpect(jsonPath("$[0].name", is("House1")))
//                .andExpect(jsonPath("$[0].price", is(10000000)))
//                .andExpect(jsonPath("$[0].numberOfRooms", is(2)))
//                .andExpect(jsonPath("$[0].area", is(50.0)))
//                .andExpect(jsonPath("$[1].name", is("House2")))
//                .andExpect(jsonPath("$[1].price", is(30000000)))
//                .andExpect(jsonPath("$[1].numberOfRooms", is(3)))
//                .andExpect(jsonPath("$[1].area", is(80.0)));

//        verify(propertyServiceMock, times(1)).getArchivedProperties(createQueryByDatesCommand);
//        verifyNoMoreInteractions(propertyServiceMock);
    }

    @Test
    public void testGetPropertyListByUserMail() throws Exception {
        Property property1 = new Property();
        property1.setId(1L);
        property1.setName("House1");
        property1.setNumberOfRooms(2);
        property1.setArea(50.0);
        property1.setPrice(10000000);

        Property property2 = new Property();
        property2.setId(2L);
        property2.setName("House2");
        property2.setNumberOfRooms(3);
        property2.setArea(80.0);
        property2.setPrice(30000000);

        List<PropertyListItem> properties = Stream.of(property1, property2).map(PropertyListItem::new)
                .collect(Collectors.toList());

        // when
        when(propertyServiceMock.getAllPropertyByMail(any(String.class))).thenReturn(properties);

        // then
        this.mockMvc.perform(get("/api/properties/admin/getPropertyListByUserMail/xxx"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("House1")))
                .andExpect(jsonPath("$[0].price", is(10000000)))
                .andExpect(jsonPath("$[0].numberOfRooms", is(2)))
                .andExpect(jsonPath("$[0].area", is(50.0)))
                .andExpect(jsonPath("$[1].name", is("House2")))
                .andExpect(jsonPath("$[1].price", is(30000000)))
                .andExpect(jsonPath("$[1].numberOfRooms", is(3)))
                .andExpect(jsonPath("$[1].area", is(80.0)));

        verify(propertyServiceMock, times(1)).getAllPropertyByMail(any(String.class));
        verifyNoMoreInteractions(propertyServiceMock);
    }

    @Test
    public void testMakePropertyActivated() throws Exception {
        when(propertyServiceMock.activateProperty(any(Long.class))).thenReturn(true);

        this.mockMvc.perform(put("/api/properties/admin/activateProperty/1"))
                .andExpect(status().isOk());

        verify(propertyServiceMock, times(1)).activateProperty(any(Long.class));
    }

    @Test
    public void testForbiddenProperty() throws Exception {
        when(propertyServiceMock.forbiddenProperty(any(Long.class))).thenReturn(true);

        this.mockMvc.perform(put("/api/properties/admin/forbiddenProperty/1"))
                .andExpect(status().isOk());

        verify(propertyServiceMock, times(1)).forbiddenProperty(any(Long.class));
    }



    private MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();

        messageSource.setBasename("messages");
        messageSource.setUseCodeAsDefaultMessage(true);

        return messageSource;
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
