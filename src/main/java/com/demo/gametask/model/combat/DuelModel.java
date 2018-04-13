package com.demo.gametask.model.combat;

import com.demo.gametask.model.config.PlayerConfig;
import com.demo.gametask.model.entity.UserCombatEntity;
import com.demo.gametask.model.entity.UserEntity;
import com.demo.gametask.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class DuelModel implements CombatModel {

    private final static int COMBAT_DELAY = 30000;

    private final int attackInterval;
    private final long beginTime = TimeUtils.getCurrentTime() + COMBAT_DELAY;
    private final AtomicReference<CombatState> state = new AtomicReference<>(CombatState.WAITING);
    private final int firstPlayerId;
    private final int secondPlayerId;
    private final PlayerCombatModel firstPlayerCombatModel;
    private final PlayerCombatModel secondPlayerCombatModel;
    private final AtomicReference<PlayerCombatModel> winner = new AtomicReference<>();
    private final List<String> attackLog = new ArrayList<>();

    public DuelModel(PlayerConfig playerConfig,
                     UserEntity firstPlayerEntity,
                     UserEntity secondPlayerEntity,
                     UserCombatEntity firstCombatEntity,
                     UserCombatEntity secondCombatEntity) {
        this.attackInterval = playerConfig.getAttackInterval();
        this.firstPlayerId = firstPlayerEntity.getId();
        this.secondPlayerId = secondPlayerEntity.getId();
        this.firstPlayerCombatModel = PlayerCombatModel.create(
                firstPlayerEntity, firstCombatEntity, 0);
        this.secondPlayerCombatModel = PlayerCombatModel.create(
                secondPlayerEntity, secondCombatEntity, 0);
        attackLog.add("Бой начался!");
    }

    public int getFirstPlayerId() {
        return firstPlayerId;
    }

    public int getSecondPlayerId() {
        return secondPlayerId;
    }

    @Override
    public CombatState getState() {
        final CombatState combatState = state.get();
        // start combat while check state
        if (combatState == CombatState.WAITING && getBeginTime() <= TimeUtils.getCurrentTime()) {
            state.set(CombatState.COMBAT);
            return CombatState.COMBAT;
        }
        return combatState;
    }

    @Override
    public long getBeginTime() {
        return beginTime;
    }

    @Override
    public PlayerCombatModel getMyCombatModel(int userId) {
        if (firstPlayerId == userId) {
            return firstPlayerCombatModel;
        }

        if (secondPlayerId == userId) {
            return secondPlayerCombatModel;
        }

        throw new RuntimeException("Server error");
    }

    @Override
    public PlayerCombatModel getOpponentCombatModel(int userId) {
        if (firstPlayerId == userId) {
            return secondPlayerCombatModel;
        }

        if (secondPlayerId == userId) {
            return firstPlayerCombatModel;
        }

        throw new RuntimeException("Server error");
    }

    @Override
    public PlayerCombatModel getWinner() {
        return winner.get();
    }

    @Override
    public List<String> getAttackLog() {
        return attackLog;
    }

    @Override
    public void attack(int userId) {
        if (getState() != CombatState.COMBAT) {
            return;
        }

        final long currentTime = TimeUtils.getCurrentTime();
        final PlayerCombatModel myCombatModel = getMyCombatModel(userId);
        final long nextAttackTime = myCombatModel.getNextAttackTime();
        if (currentTime < nextAttackTime || !myCombatModel.isAlive()) {
            // myCombatModel.isAlive() checked because player may be killed after getState() != CombatState.COMBAT line
            return;
        }

        final PlayerCombatModel opponentCombatModel = getOpponentCombatModel(userId);
        final int damage = myCombatModel.getAttackDamage();
        opponentCombatModel.takeAttack(damage);
        myCombatModel.setNextAttackTime(currentTime + attackInterval);

        if (!opponentCombatModel.isAlive()) {
            // both players may be killed simultaneously, first one - winner
            if (state.compareAndSet(CombatState.COMBAT, CombatState.FINISHED)) {
                winner.set(myCombatModel);
            }
        }

        // string operation is heavy one - make after concurrency code
        attackLog.add(myCombatModel.getUsername() + " ударил " + opponentCombatModel.getUsername() + " на " + damage + " урона");
        if (!opponentCombatModel.isAlive()) {
            attackLog.add(myCombatModel.getUsername() + " убил " + opponentCombatModel.getUsername());
        }
    }

    @Override
    public boolean isWinner(int userId) {
        return userId == getWinner().getUserId();
    }
}
