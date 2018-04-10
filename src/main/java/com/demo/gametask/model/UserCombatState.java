package com.demo.gametask.model;

import com.demo.gametask.model.entity.UserCombatEntity;

public class UserCombatState {

    public static UserCombatState create(UserCombatEntity userCombatEntity) {
        return create(userCombatEntity, 0);
    }

    public static UserCombatState create(UserCombatEntity userCombatEntity, int additionalHealthPoints) {
        final UserCombatState userCombatState = new UserCombatState();
        userCombatState.setUserId(userCombatEntity.getUserId());
        userCombatState.setAttackDamage(userCombatEntity.getAttackDamage());
        userCombatState.setInitialHealthPoints(userCombatEntity.getHealthPoints());
        userCombatState.setAdaptedHealthPoints(userCombatEntity.getHealthPoints() + additionalHealthPoints);
        return userCombatState;
    }

    private int userId;
    private int attackDamage;
    private int initialHealthPoints;
    private int adaptedHealthPoints;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public int getInitialHealthPoints() {
        return initialHealthPoints;
    }

    public void setInitialHealthPoints(int initialHealthPoints) {
        this.initialHealthPoints = initialHealthPoints;
    }

    public int getAdaptedHealthPoints() {
        return adaptedHealthPoints;
    }

    public void setAdaptedHealthPoints(int adaptedHealthPoints) {
        this.adaptedHealthPoints = adaptedHealthPoints;
    }
}
