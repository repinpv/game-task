package com.demo.gametask.model.combat;

public interface PlayerCombatStats {

    int getUserId();

    String getUsername();

    int getAttackDamage();

    int getHealthPoints();

    int getHealthPointsLeft();

    int getHealthPointsLeftPercent();
}
