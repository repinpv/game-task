package com.demo.gametask.service.duel;

import com.demo.gametask.model.DuelState;
import org.springframework.stereotype.Service;

@Service
public class DuelService {
    public DuelState getDuelState(int userId) {
        return DuelState.NOT_PARTICIPANT;
    }
}
