package com.demo.gametask.model.entity;

import com.demo.gametask.model.combat.PlayerCombatStats;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_combat")
public class UserCombatEntity implements PlayerCombatStats {

    private static final int INITIAL_ATTACK_DAMAGE = 10;
    private static final int INITIAL_HEALTH_POINTS = 100;

    public static UserCombatEntity create(final UserEntity user) {
        final UserCombatEntity userCombatEntity = new UserCombatEntity();
        userCombatEntity.setUserId(user.getId());
        userCombatEntity.setAttackDamage(INITIAL_ATTACK_DAMAGE);
        userCombatEntity.setHealthPoints(INITIAL_HEALTH_POINTS);
        return userCombatEntity;
    }

    @Id
    @Column(name = "user_id")
    private int userId;
    @Column(name = "attack_damage")
    private int attackDamage;
    @Column(name = "health_points")
    private int healthPoints;

    @Override
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String getUsername() {
        return "MUST NOT BE SHOWN";
    }

    @Override
    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    @Override
    public int getHealthPoints() {
        return healthPoints;
    }

    @Override
    public int getHealthPointsLeft() {
        return healthPoints;
    }

    @Override
    public int getHealthPointsLeftPercent() {
        return 100;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }
}
