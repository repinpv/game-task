package com.demo.gametask.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LobbyController {

    @GetMapping({"/", "/lobby"})
    public String lobby() {
        return "lobby";
    }
}
