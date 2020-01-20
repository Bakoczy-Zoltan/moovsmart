package com.progmasters.moovsmart.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.progmasters.moovsmart.domain.RoleType;
import com.progmasters.moovsmart.domain.UserProperty;
import com.progmasters.moovsmart.dto.CreateUserCommand;
import com.progmasters.moovsmart.dto.PropertyForm;
import com.progmasters.moovsmart.dto.PropertyListItem;
import com.progmasters.moovsmart.exception.GlobalExceptionHandler;
import com.progmasters.moovsmart.repository.UserRepository;
import com.progmasters.moovsmart.service.MailSenderService;
import com.progmasters.moovsmart.service.UserService;
import com.progmasters.moovsmart.validation.RegistrationValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    private MockMvc mockMvc;

    @Mock
    UserRepository userRepositoryMock;

    @Mock
    private UserService userServiceMock;

    @Mock
    private MailSenderService mailSenderServiceMock;

    @BeforeEach
    public void setUp() {
        RegistrationValidator registrationValidator = new RegistrationValidator();

        UserController userController =
                new UserController(mailSenderServiceMock, userServiceMock, registrationValidator);

        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(new GlobalExceptionHandler(messageSource()))
                .build();
    }

    @AfterEach
    public void validate() {
        validateMockitoUsage();
    }

    @Test
    public void testCreateUser() throws Exception {
        CreateUserCommand createUserCommand = new CreateUserCommand();
        createUserCommand.setUserName("XY");
        createUserCommand.setMail("xy@xy.com");
//        createUserCommand.setPassword("pw");
//        createUserCommand.setRole(Arrays.asList("ROLE_USER"));

        when(userServiceMock.makeUser(any(CreateUserCommand.class))).thenReturn(1L);
        doNothing().when(mailSenderServiceMock).sendMailByTokenRegistration(createUserCommand.getUserName(),
                createUserCommand.getMail());

        this.mockMvc.perform(post("/api/user/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(createUserCommand)))
                .andExpect(status().isCreated());

        verify(userServiceMock, times(1)).makeUser(any(CreateUserCommand.class));
        verifyNoMoreInteractions(userServiceMock);
        verify(mailSenderServiceMock, times(1)).sendMailByTokenRegistration(createUserCommand.getUserName(),
                createUserCommand.getMail());
        verifyNoMoreInteractions(mailSenderServiceMock);

    }

    @Test
    public void testCreateUserFailed() throws Exception {
        CreateUserCommand createUserCommand = new CreateUserCommand();
        createUserCommand.setUserName("XY");
        createUserCommand.setMail("xy@xy.com");

        when(userServiceMock.makeUser(any(CreateUserCommand.class))).thenReturn(null);

        this.mockMvc.perform(post("/api/user/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(createUserCommand)))
                .andExpect(status().isBadRequest());

        verify(userServiceMock, times(1)).makeUser(any(CreateUserCommand.class));
        verifyNoMoreInteractions(userServiceMock);

    }

    @Test
    public void testValidateUser() throws Exception {
        CreateUserCommand createUserCommand = new CreateUserCommand();
        createUserCommand.setUserName("XY");
        createUserCommand.setMail("xy@xy.com");
        createUserCommand.setRole(Arrays.asList("ROLE_USER"));

        UserProperty userProperty = new UserProperty(createUserCommand);
        userProperty.setId(1L);

        when(mailSenderServiceMock.getUserByToken(any(String.class))).thenReturn(userProperty);
        when(userServiceMock.validateUser(1L)).thenReturn(createUserCommand.getRole());

        this.mockMvc.perform(get("/api/user/validuser/1"))
                .andExpect(status().isOk());

        verify(mailSenderServiceMock, times(1)).getUserByToken(any(String.class));
        verifyNoMoreInteractions(mailSenderServiceMock);
        verify(userServiceMock, times(1)).validateUser(1L);
        verifyNoMoreInteractions(userServiceMock);
    }

    @Test
    public void testValidateUserFailed() throws Exception {

        when(mailSenderServiceMock.getUserByToken(any(String.class))).thenReturn(null);

        this.mockMvc.perform(get("/api/user/validuser/1"))
                .andExpect(status().isNotFound());

        verify(mailSenderServiceMock, times(1)).getUserByToken(any(String.class));
        verifyNoMoreInteractions(mailSenderServiceMock);
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
