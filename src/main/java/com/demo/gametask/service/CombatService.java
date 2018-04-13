package com.demo.gametask.service;

import com.demo.gametask.model.combat.CombatModel;
import com.demo.gametask.model.entity.UserCombatEntity;

public interface CombatService {

    UserCombatEntity getUserCombatEntity(int userId);

    void createDuel(int firstPlayerId, int secondPlayerId);

    CombatModel getCombat(int userId);

    void removeCombat(int userId);

    void attack(int userId);
}
