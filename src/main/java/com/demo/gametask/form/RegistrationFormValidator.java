package com.demo.gametask.form;

import com.demo.gametask.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class RegistrationFormValidator implements Validator {

    private final UserService userService;

    public RegistrationFormValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return RegistrationForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        RegistrationForm registrationForm = (RegistrationForm) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        if (registrationForm.getUsername().length() < 3 || registrationForm.getUsername().length() > 32) {
            errors.rejectValue("username", "Size.registrationForm.username");
        }

        userService
                .findByName(registrationForm.getUsername())
                .ifPresent(user -> errors.rejectValue("username", "Duplicate.registrationForm.username"));

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (registrationForm.getPassword().length() < 3 || registrationForm.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.registrationForm.password");
        }

        if (!registrationForm.getPasswordConfirm().equals(registrationForm.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.registrationForm.passwordConfirm");
        }
    }
}
