package com.demo.gametask.service;

import com.demo.gametask.model.LobbyState;
import com.demo.gametask.model.combat.CombatModel;
import com.demo.gametask.model.combat.CombatState;
import com.demo.gametask.model.entity.UserCombatEntity;
import com.demo.gametask.model.entity.UserDuelEntity;
import com.demo.gametask.repository.UserCombatRepository;
import com.demo.gametask.repository.UserDuelRepository;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class LobbyService {

    private final static int NO_PLAYER_ID = 0;

    private final UserCombatRepository userCombatRepository;
    private final UserDuelRepository userDuelRepository;
    private final CombatService combatService;
    private final ConcurrentMap<Integer, LobbyState> lobbyStateMap = new ConcurrentHashMap<>();
    private int waitingPlayerId = NO_PLAYER_ID;

    public LobbyService(UserCombatRepository userCombatRepository,
                        UserDuelRepository userDuelRepository,
                        CombatService combatService) {
        this.userCombatRepository = userCombatRepository;
        this.userDuelRepository = userDuelRepository;
        this.combatService = combatService;
    }

    public LobbyState getState(int userId) {
        // NOT_PARTICIPANT state don't store in map
        return lobbyStateMap.getOrDefault(userId, LobbyState.NOT_PARTICIPANT);
    }

    public void joinDuel(int userId) {
        // can join if do nothing
        if (lobbyStateMap.putIfAbsent(userId, LobbyState.WAITING_FOR_OPPONENT) == null) {
            int opponentId = NO_PLAYER_ID;
            synchronized (this) {
                if (waitingPlayerId == NO_PLAYER_ID) {
                    waitingPlayerId = userId;
                } else {
                    opponentId = waitingPlayerId;
                    waitingPlayerId = NO_PLAYER_ID;
                    lobbyStateMap.put(opponentId, LobbyState.DUEL_CREATING);
                    lobbyStateMap.put(userId, LobbyState.DUEL_CREATING);
                }
            }

            if (opponentId != NO_PLAYER_ID) {
                combatService.createDuel(opponentId, userId);
                lobbyStateMap.put(opponentId, LobbyState.DUEL);
                lobbyStateMap.put(userId, LobbyState.DUEL);
            }
        }
    }

    public void cancelDuel(int userId) {
        final LobbyState lobbyState = getState(userId);
        if (lobbyState == LobbyState.WAITING_FOR_OPPONENT) {
            synchronized (this) {
                if (waitingPlayerId == userId) {
                    waitingPlayerId = NO_PLAYER_ID;
                    lobbyStateMap.remove(userId);
                }
            }
        }
    }

    public void finishDuel(int userId) {
        final LobbyState lobbyState = getState(userId);
        if (lobbyState != LobbyState.DUEL) {
            return;
        }

        final CombatModel combat = combatService.getCombat(userId);
        final CombatState combatState = combat.getState();
        if (combatState != CombatState.FINISHED) {
            return;
        }

        // finish game just one time
        if (!lobbyStateMap.replace(userId, LobbyState.DUEL, LobbyState.DUEL_FINISHING)) {
            return;
        }
        combatService.removeCombat(userId);

        // change duel rating
        final UserDuelEntity userDuelEntity = userDuelRepository
                .findById(userId).orElseThrow(NullPointerException::new);
        final int rating = userDuelEntity.getRating();
        if (combat.isWinner(userId)) {
            userDuelEntity.setRating(rating + 1);
        } else if (rating > 0) {
            userDuelEntity.setRating(rating - 1);
        }

        // change combat stats
        final UserCombatEntity userCombatEntity = userCombatRepository
                .findById(userId).orElseThrow(NullPointerException::new);
        userCombatEntity.setAttackDamage(userCombatEntity.getAttackDamage() + 1);
        userCombatEntity.setHealthPoints(userCombatEntity.getHealthPoints() + 1);

        lobbyStateMap.remove(userId);
    }
}
