package com.demo.gametask.controller;

import com.demo.gametask.model.DuelState;
import com.demo.gametask.model.user.UserIdentity;
import com.demo.gametask.service.SecurityService;
import com.demo.gametask.service.duel.DuelService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DuelController {

    private final SecurityService securityService;
    private final DuelService duelService;

    public DuelController(SecurityService securityService, DuelService duelService) {
        this.securityService = securityService;
        this.duelService = duelService;
    }

    @GetMapping("/duel")
    public ModelAndView duel() {
        final ModelAndView modelAndView = new ModelAndView();

        final UserIdentity user = securityService.getLoggedInUser();
        final DuelState duelState = duelService.getDuelState(user.getId());
        switch (duelState) {
            case NOT_PARTICIPANT:
                modelAndView.setViewName("duel_lobby");
                break;
            case WAITING_FOR_OPPONENT:
                modelAndView.setViewName("duel_waiting");
                break;
            case DUELING:
                modelAndView.setViewName("duel_combat");
                break;
        }

        return modelAndView;
    }
}
