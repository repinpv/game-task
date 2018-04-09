package com.demo.gametask.controller;

import com.demo.gametask.model.UserEntity;
import com.demo.gametask.service.SecurityService;
import com.demo.gametask.service.UserService;
import com.demo.gametask.validator.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.*;

@Controller
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    private final SecurityService securityService;

    private final UserValidator userValidator;

    private final MessageSource messageSource;

    public UserController(UserService userService, SecurityService securityService, UserValidator userValidator, MessageSource messageSource) {
        this.userService = userService;
        this.securityService = securityService;
        this.userValidator = userValidator;
        this.messageSource = messageSource;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new UserEntity());

        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") UserEntity userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            final Map<String, List<String>> errorMap = new HashMap<>();
            for (final ObjectError error : bindingResult.getAllErrors()) {
                if (error instanceof FieldError) {
                    FieldError fieldError = (FieldError) error;

                    final String key = "error-" + fieldError.getField();
                    List<String> errorList = errorMap.get(key);
                    if (errorList == null) {
                        errorList = new ArrayList<>();
                        errorMap.put(key, errorList);
                    }

                    final String message = messageSource.getMessage(error, Locale.ENGLISH);
                    errorList.add(message);
                }
            }
            model.addAllAttributes(errorMap);

            return "registration";
        }

        userService.save(userForm);

        securityService.autologin(userForm.getName(), userForm.getPassword());

        return "redirect:/";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        model.addAttribute("error", error != null ? "Your username and password is invalid." : "");
        model.addAttribute("message", logout != null ? "You have been logged out successfully." : "");

        return "login";
    }
}
