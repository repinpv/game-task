package com.demo.gametask.service;

import com.demo.gametask.model.combat.CombatModel;
import com.demo.gametask.model.combat.DuelModel;
import com.demo.gametask.model.entity.UserCombatEntity;
import com.demo.gametask.model.entity.UserEntity;
import com.demo.gametask.repository.UserCombatRepository;
import com.demo.gametask.repository.UserRepository;
import com.demo.gametask.utils.ConfigUtils;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CombatServiceImpl implements CombatService {

    private final UserRepository userRepository;
    private final UserCombatRepository userCombatRepository;
    private final Map<Integer, CombatModel> combatModels = new ConcurrentHashMap<>();

    public CombatServiceImpl(UserRepository userRepository,
                             UserCombatRepository userCombatRepository) {
        this.userRepository = userRepository;
        this.userCombatRepository = userCombatRepository;
    }

    @Override
    public UserCombatEntity getUserCombatEntity(int userId) {
        final Optional<UserCombatEntity> userCombatEntity = userCombatRepository.findById(userId);
        return userCombatEntity.orElseThrow(NullPointerException::new);
    }

    @Override
    public void createDuel(int firstPlayerId, int secondPlayerId) {
        final UserEntity firstPlayerEntity = userRepository
                .findById(firstPlayerId).orElseThrow(NullPointerException::new);
        final UserEntity secondPlayerEntity = userRepository
                .findById(secondPlayerId).orElseThrow(NullPointerException::new);
        final UserCombatEntity firstCombatEntity = getUserCombatEntity(firstPlayerId);
        final UserCombatEntity secondCombatEntity = getUserCombatEntity(secondPlayerId);

        final DuelModel duelModel = new DuelModel(
                ConfigUtils.PLAYER_CONFIG, firstPlayerEntity, secondPlayerEntity, firstCombatEntity, secondCombatEntity);
        combatModels.put(firstPlayerId, duelModel);
        combatModels.put(secondPlayerId, duelModel);
    }

    @Override
    public CombatModel getCombat(int userId) {
        return combatModels.get(userId);
    }

    @Override
    public void removeCombat(int userId) {
        combatModels.remove(userId);
    }

    @Override
    public void attack(int userId) {
        final CombatModel combat = getCombat(userId);
        combat.attack(userId);
    }
}
