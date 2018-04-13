package com.demo.gametask.model.combat;

import com.demo.gametask.model.entity.UserCombatEntity;
import com.demo.gametask.model.entity.UserEntity;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class PlayerCombatModel implements PlayerCombatStats {

    public static PlayerCombatModel create(UserEntity userEntity,
                                           UserCombatEntity userCombatEntity,
                                           int additionalHealthPoints) {
        return new PlayerCombatModel(userEntity, userCombatEntity, additionalHealthPoints);
    }

    private final int userId;
    private final String username;
    private final int attackDamage;
    private final int initialHealthPoints;
    private final int additionalHealthPoints;
    private final AtomicInteger adaptedHealthPoints;
    private final AtomicLong nextAttackTime = new AtomicLong();

    private PlayerCombatModel(UserEntity userEntity,
                              UserCombatEntity userCombatEntity,
                              int additionalHealthPoints) {
        userId = userEntity.getId();
        username = userEntity.getUsername();
        attackDamage = userCombatEntity.getAttackDamage();
        initialHealthPoints = userCombatEntity.getHealthPoints();
        this.additionalHealthPoints = additionalHealthPoints;
        adaptedHealthPoints = new AtomicInteger(
                userCombatEntity.getHealthPoints() + additionalHealthPoints);
    }

    @Override
    public int getUserId() {
        return userId;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    @Override
    public int getHealthPoints() {
        return initialHealthPoints;
    }

    @Override
    public int getHealthPointsLeft() {
        final int left = adaptedHealthPoints.get() - additionalHealthPoints;
        if (left < 0) {
            return 0;
        }
        return left;
    }

    @Override
    public int getHealthPointsLeftPercent() {
        return 100 * getHealthPointsLeft() / getHealthPoints();
    }

    public int getAdaptedHealthPoints() {
        return adaptedHealthPoints.get();
    }

    public void setAdaptedHealthPoints(int adaptedHealthPoints) {
        this.adaptedHealthPoints.set(adaptedHealthPoints);
    }

    public long getNextAttackTime() {
        return nextAttackTime.get();
    }

    public void setNextAttackTime(long nextAttackTime) {
        this.nextAttackTime.set(nextAttackTime);
    }

    public boolean isAlive() {
        return getHealthPointsLeft() > 0;
    }

    public void takeAttack(int attackDamage) {
        adaptedHealthPoints.addAndGet(-attackDamage);
    }
}
