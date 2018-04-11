package com.demo.gametask.service.duel;

import com.demo.gametask.model.DuelState;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LobbyDuelService {

    private final Map<Integer, DuelState> duelStateMap = new ConcurrentHashMap<>();

    public DuelState getDuelState(int userId) {
        return duelStateMap.getOrDefault(userId, DuelState.NOT_PARTICIPANT);
    }
}
