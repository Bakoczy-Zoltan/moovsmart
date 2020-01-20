package com.progmasters.moovsmart.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.progmasters.moovsmart.domain.*;
import com.progmasters.moovsmart.dto.CreateFilteredCommand;
import com.progmasters.moovsmart.dto.PropertyDetails;
import com.progmasters.moovsmart.dto.PropertyForm;
import com.progmasters.moovsmart.dto.PropertyListItem;
import com.progmasters.moovsmart.exception.GlobalExceptionHandler;
import com.progmasters.moovsmart.repository.PropertyRepository;
import com.progmasters.moovsmart.service.PropertyService;
import com.progmasters.moovsmart.validation.PropertyFormValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.security.Principal;
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
public class PropertyControllerTest {
    private MockMvc mockMvc;

    @Mock
    private PropertyRepository propertyRepositoryMock;

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

        List<PropertyListItem> properties = Stream.of(property1, property2).map(PropertyListItem::new).collect(Collectors.toList());

        // when
        when(propertyServiceMock.getProperties()).thenReturn(properties);

        // then
        this.mockMvc.perform(get("/api/properties"))
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

        verify(propertyServiceMock, times(1)).getProperties();
        verifyNoMoreInteractions(propertyServiceMock);
    }


    @Test
    public void testGetFilteredList_WithRoom() throws Exception {
        // given
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

        List<PropertyListItem> properties = Stream.of(property1).map(PropertyListItem::new).collect(Collectors.toList());

        CreateFilteredCommand createFilteredCommand = new CreateFilteredCommand();
        createFilteredCommand.setNumberOfRooms(2);

        // when
        when(propertyServiceMock.getFilteredProperties(any(CreateFilteredCommand.class)))
                .thenReturn(properties);

        // then
        this.mockMvc.perform(post("/api/properties/filteredList")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(createFilteredCommand)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("House1")))
                .andExpect(jsonPath("$[0].price", is(10000000)))
                .andExpect(jsonPath("$[0].numberOfRooms", is(2)))
                .andExpect(jsonPath("$[0].area", is(50.0)));

        verify(propertyServiceMock, times(1))
                .getFilteredProperties(any(CreateFilteredCommand.class));
        verifyNoMoreInteractions(propertyServiceMock);
    }

    @Test
    public void testGetFilteredList_WithoutRoom() throws Exception {
        // given
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

        List<PropertyListItem> properties = Stream.of(property1, property2).map(PropertyListItem::new).collect(Collectors.toList());

        CreateFilteredCommand createFilteredCommand = new CreateFilteredCommand();
        createFilteredCommand.setMinPrice(5000000);
        createFilteredCommand.setMaxPrice(35000000);

        // when
        when(propertyServiceMock.getFilteredPropertiesWithoutRooms(any(CreateFilteredCommand.class)))
                .thenReturn(properties);

        // then
        this.mockMvc.perform(post("/api/properties/filteredList")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(createFilteredCommand)))
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

        verify(propertyServiceMock, times(1))
                .getFilteredPropertiesWithoutRooms(any(CreateFilteredCommand.class));
        verifyNoMoreInteractions(propertyServiceMock);
    }

    @Test
    public void testGetOwnProperties() throws Exception {
        // given
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("xy@xy.com");

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
        when(propertyServiceMock.getOwnProperties(mockPrincipal.getName())).thenReturn(properties);

        // then
        this.mockMvc.perform(get("/api/properties/authUser/myList")
                .principal(mockPrincipal))
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

        verify(propertyServiceMock, times(1)).getOwnProperties(mockPrincipal.getName());
        verifyNoMoreInteractions(propertyServiceMock);
    }

    @Test
    public void testGetPropertyDetails() throws Exception {
        // given
        UserProperty user = new UserProperty();
        user.setMail("xy@xy.com");
        user.setId(1L);

        Property property1 = new Property();
        property1.setId(1L);
        property1.setName("House1");
        property1.setNumberOfRooms(2);
        property1.setArea(50.0);
        property1.setPrice(10000000);
        property1.setPropertyType(PropertyType.HOUSE);
        property1.setCounty(County.BARANYA);
        property1.setOwner(user);

        PropertyDetails propertyDetails = new PropertyDetails(property1);

        // when
        when(propertyServiceMock.getPropertyDetails(any(Long.class))).thenReturn(propertyDetails);

        // then
        this.mockMvc.perform(get("/api/properties/authUser/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("House1")))
                .andExpect(jsonPath("$.price", is(10000000)))
                .andExpect(jsonPath("$.numberOfRooms", is(2)))
                .andExpect(jsonPath("$.area", is(50.0)));

        verify(propertyServiceMock, times(1)).getPropertyDetails(any(Long.class));
        verifyNoMoreInteractions(propertyServiceMock);

    }

    @Test
    public void testCreateProperty() throws Exception {
        // given
        UserDetails userDetails = Mockito.mock(UserDetails.class);
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        UserProperty user = new UserProperty();
        user.setMail("xy@xy.com");
        user.setPassword("pw");
//        user.setId(1L);

        Property property1 = new Property();
        property1.setId(1L);
        property1.setName("House1");
        property1.setNumberOfRooms(2);
        property1.setArea(50.0);
        property1.setPrice(10000000);
        property1.setPropertyType(PropertyType.HOUSE);
        property1.setPropertyState(PropertyState.RENEWABLE);
        property1.setCounty(County.BARANYA);
        property1.setDescription("Beautiful house");
        property1.setOwner(user);

        PropertyForm propertyForm = new PropertyForm(property1);

        doNothing().when(propertyServiceMock).createProperty(any(PropertyForm.class), any());

        this.mockMvc.perform(post("/api/properties/authUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(propertyForm)))
                .andExpect(status().isCreated());

        verify(propertyServiceMock, times(1)).createProperty(any(PropertyForm.class), any());
        verifyNoMoreInteractions(propertyServiceMock);
    }

    @Test
    public void testUpdateProperty() throws Exception {
        UserProperty user = new UserProperty();
        user.setMail("xy@xy.com");
        user.setPassword("pw");
//        user.setId(1L);

        UserDetails userDetails = Mockito.mock(UserDetails.class);
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        Property updateProperty1 = new Property();
        updateProperty1.setId(1L);
        updateProperty1.setName("UpdateHouse1");
        updateProperty1.setNumberOfRooms(3);
        updateProperty1.setArea(80.0);
        updateProperty1.setPrice(30000000);
        updateProperty1.setPropertyType(PropertyType.HOUSE);
        updateProperty1.setPropertyState(PropertyState.RENEWED);
        updateProperty1.setCounty(County.BARANYA);
        updateProperty1.setDescription("The most beautiful house");
        updateProperty1.setOwner(user);

        PropertyForm updatePropertyForm = new PropertyForm(updateProperty1);

        when(propertyServiceMock.updateProperty(any(PropertyForm.class),
                any(Long.class), any()))
                .thenReturn(updateProperty1);

        this.mockMvc.perform(put("/api/properties/authUser/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(updatePropertyForm)))
                .andExpect(status().isOk());

        verify(propertyServiceMock, times(1))
                .updateProperty(any(PropertyForm.class), any(Long.class), any());
        verifyNoMoreInteractions(propertyServiceMock);
    }

    @Test
    public void testUpdatePropertyReturnNull() throws Exception {
        UserProperty user = new UserProperty();
        user.setMail("xy@xy.com");
        user.setPassword("pw");
//        user.setId(1L);

        UserDetails userDetails = Mockito.mock(UserDetails.class);
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        Property updateProperty1 = new Property();
        updateProperty1.setId(1L);
        updateProperty1.setName("UpdateHouse1");
        updateProperty1.setNumberOfRooms(3);
        updateProperty1.setArea(80.0);
        updateProperty1.setPrice(30000000);
        updateProperty1.setPropertyType(PropertyType.HOUSE);
        updateProperty1.setPropertyState(PropertyState.RENEWED);
        updateProperty1.setCounty(County.BARANYA);
        updateProperty1.setDescription("The most beautiful house");
        updateProperty1.setOwner(user);

        PropertyForm updatePropertyForm = new PropertyForm(updateProperty1);

        when(propertyServiceMock.updateProperty(any(PropertyForm.class),
                any(Long.class), any()))
                .thenReturn(null);

        this.mockMvc.perform(put("/api/properties/authUser/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(updatePropertyForm)))
                .andExpect(status().isNotFound());

        verify(propertyServiceMock, times(1))
                .updateProperty(any(PropertyForm.class), any(Long.class), any());
        verifyNoMoreInteractions(propertyServiceMock);
    }

    @Test
    public void testDeleteProperty() throws Exception {
        UserDetails userDetails = Mockito.mock(UserDetails.class);
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        when(propertyServiceMock.deleteProperty(any(Long.class), any())).thenReturn(true);

        this.mockMvc.perform(delete("/api/properties/authUser/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(propertyServiceMock, times(1))
                .deleteProperty(any(Long.class), any());
        verifyNoMoreInteractions(propertyServiceMock);
    }

    @Test
    public void testDeletePropertyReturnNull() throws Exception {
        UserDetails userDetails = Mockito.mock(UserDetails.class);
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        when(propertyServiceMock.deleteProperty(any(Long.class), any())).thenReturn(false);

        this.mockMvc.perform(delete("/api/properties/authUser/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(propertyServiceMock, times(1))
                .deleteProperty(any(Long.class), any());
        verifyNoMoreInteractions(propertyServiceMock);
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
