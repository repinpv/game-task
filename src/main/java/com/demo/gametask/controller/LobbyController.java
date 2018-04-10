package com.demo.gametask.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LobbyController {

    @GetMapping({"/", "/lobby"})
    public ModelAndView lobby() {
        return new ModelAndView("lobby");
    }
}
