package com.progmasters.moovsmart.validation;

import com.progmasters.moovsmart.dto.CreateUserCommand;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class RegistrationValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return CreateUserCommand.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        CreateUserCommand newUser = (CreateUserCommand) o;
        if (newUser.getUserName() == null || newUser.getUserName().equals("")) {
            errors.rejectValue("userName", "newUser.userName.empty");
        }

        if (newUser.getUserName().length() < 2 || newUser.getUserName().length() > 30) {
            errors.rejectValue("userName", "newUser.userName.notproperlength");
        }

        if (newUser.getMail() == null || newUser.getMail().equals("")) {
            errors.rejectValue("mail", "newUser.mail.empty");
        }

        if (!newUser.getMail().matches("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")) {
            errors.rejectValue("mail", "newUser.mail.notvalid");
        }

        if (newUser.getPassword() == null || newUser.getPassword().equals("")) {
            errors.rejectValue("password", "newUser.password.empty");
        }

        if (!newUser.getPassword().matches("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,}")) {
            errors.rejectValue("password", "newUser.password.notvalid");
        }

    }
}
