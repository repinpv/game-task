package com.demo.gametask.controller;

import com.demo.gametask.model.DuelState;
import com.demo.gametask.model.user.UserIdentity;
import com.demo.gametask.service.SecurityService;
import com.demo.gametask.service.duel.LobbyDuelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DuelController {

    private final SecurityService securityService;
    private final LobbyDuelService lobbyDuelService;

    public DuelController(SecurityService securityService, LobbyDuelService lobbyDuelService) {
        this.securityService = securityService;
        this.lobbyDuelService = lobbyDuelService;
    }

    @GetMapping("/duel")
    public String duel(Model model) {

        final UserIdentity user = securityService.getLoggedInUser();
        final DuelState duelState = lobbyDuelService.getDuelState(user.getId());
        final String viewName;
        switch (duelState) {
            case WAITING_FOR_OPPONENT:
                viewName = "duel_waiting";
                break;
            case DUELING:
                viewName = "duel_combat";
                break;
            default:
                viewName = "duel_lobby";
        }

        return viewName;
    }
}
