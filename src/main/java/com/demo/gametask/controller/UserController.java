package com.demo.gametask.controller;

import com.demo.gametask.form.RegistrationForm;
import com.demo.gametask.form.RegistrationFormValidator;
import com.demo.gametask.service.SecurityService;
import com.demo.gametask.service.UserService;
import com.demo.gametask.utils.FormErrorAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

    private final UserService userService;
    private final SecurityService securityService;
    private final RegistrationFormValidator registrationFormValidator;
    private final FormErrorAdapter formErrorAdapter;

    public UserController(UserService userService,
                          SecurityService securityService,
                          RegistrationFormValidator registrationFormValidator,
                          FormErrorAdapter formErrorAdapter) {
        this.userService = userService;
        this.securityService = securityService;
        this.registrationFormValidator = registrationFormValidator;
        this.formErrorAdapter = formErrorAdapter;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("registrationForm", new RegistrationForm());

        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("registrationForm") RegistrationForm registrationForm, BindingResult bindingResult, Model model) {
        registrationFormValidator.validate(registrationForm, bindingResult);

        if (bindingResult.hasErrors()) {
            formErrorAdapter.processErrors(bindingResult, model);

            return "registration";
        }

        userService.save(registrationForm);

        securityService.autoLogin(registrationForm.getUsername(), registrationForm.getPassword());

        return "redirect:/lobby";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        model.addAttribute("error", error != null ? "Your username and password is invalid." : "");
        model.addAttribute("message", logout != null ? "You have been logged out successfully." : "");

        return "login";
    }
}
