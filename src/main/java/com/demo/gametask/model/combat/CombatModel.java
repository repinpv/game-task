package com.demo.gametask.model.combat;

import java.util.List;

public interface CombatModel {

    CombatState getState();

    long getBeginTime();

    PlayerCombatModel getMyCombatModel(int userId);

    PlayerCombatModel getOpponentCombatModel(int userId);

    PlayerCombatModel getWinner();

    List<String> getAttackLog();

    void attack(int userId);

    boolean isWinner(int userId);
}
