package com.demo.gametask.controller;

import com.demo.gametask.model.LobbyState;
import com.demo.gametask.model.combat.CombatModel;
import com.demo.gametask.model.combat.CombatState;
import com.demo.gametask.model.combat.PlayerCombatModel;
import com.demo.gametask.model.entity.UserCombatEntity;
import com.demo.gametask.model.entity.UserDuelEntity;
import com.demo.gametask.model.user.UserIdentity;
import com.demo.gametask.service.CombatService;
import com.demo.gametask.service.DuelRatingService;
import com.demo.gametask.service.LobbyService;
import com.demo.gametask.service.SecurityService;
import com.demo.gametask.utils.TimeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class DuelController {

    private final SecurityService securityService;
    private final LobbyService lobbyService;
    private final CombatService combatService;
    private final DuelRatingService duelRatingService;

    public DuelController(SecurityService securityService,
                          LobbyService lobbyService,
                          CombatService combatService,
                          DuelRatingService duelRatingService) {
        this.securityService = securityService;
        this.lobbyService = lobbyService;
        this.combatService = combatService;
        this.duelRatingService = duelRatingService;
    }

    @GetMapping("/duel")
    public String duel(@RequestParam(value = "action", required = false) String action, Model model) {

        final UserIdentity user = securityService.getLoggedInUser();
        final int userId = user.getId();

        processDuelAction(userId, action);

        final String viewName;
        final LobbyState lobbyState = lobbyService.getState(userId);
        switch (lobbyState) {
            case WAITING_FOR_OPPONENT:
                fillPlayerCombatModel(model, userId);
                viewName = "duel_waiting_for_opponent";
                break;
            case DUEL:
                final CombatModel combat = combatService.getCombat(userId);
                fillCombatModel(model, userId, combat);

                final CombatState combatState = combat.getState();
                switch (combatState) {
                    case WAITING:
                        fillMessage(model, "Бой начнется через " + TimeUtils.getLeftTime(combat.getBeginTime()) + " мс");
                        viewName = "duel_waiting_for_combat";
                        break;
                    case COMBAT:
                        final PlayerCombatModel myCombatModel = combat.getMyCombatModel(userId);
                        final long nextAttackTimeDiff = myCombatModel.getNextAttackTime() - TimeUtils.getCurrentTime();
                        if (nextAttackTimeDiff > 0) {
                            fillMessage(model, "Удар возможен через " + nextAttackTimeDiff + " мс");
                        } else {
                            fillMessage(model, "Можно бить");
                        }

                        fillAttackLog(model, combat.getAttackLog());
                        viewName = "duel_combat";
                        break;
                    default:
                        fillMessage(model, "Бой завершен - победил " + combat.getWinner().getUsername() + "!");
                        fillAttackLog(model, combat.getAttackLog());
                        viewName = "duel_result";
                }
                break;
            default:
                fillPlayerCombatModel(model, userId);
                final UserDuelEntity rating = duelRatingService.getRating(userId);
                fillMessage(model, "Ваш рейтинг - " + rating.getRating());
                viewName = "duel_lobby";
        }

        return viewName;
    }

    private void processDuelAction(int userId, String action) {
        if (action == null) {
            return;
        }

        if ("join".equals(action)) {
            lobbyService.joinDuel(userId);
        } else if ("cancel".equals(action)) {
            lobbyService.cancelDuel(userId);
        } else if ("attack".equals(action)) {
            combatService.attack(userId);
        } else if ("finish".equals(action)) {
            lobbyService.finishDuel(userId);
        }
    }

    private void fillPlayerCombatModel(Model model, int userId) {
        final UserCombatEntity userCombatEntity = combatService.getUserCombatEntity(userId);
        model.addAttribute("player", userCombatEntity);
    }

    private void fillCombatModel(Model model, int userId, CombatModel combat) {
        model.addAttribute("player", combat.getMyCombatModel(userId));
        model.addAttribute("opponent", combat.getOpponentCombatModel(userId));
    }

    private void fillMessage(Model model, String message) {
        model.addAttribute("message", message);
    }

    private void fillAttackLog(Model model, List<String> attackLog) {
        model.addAttribute("attackLog", attackLog);
    }
}
