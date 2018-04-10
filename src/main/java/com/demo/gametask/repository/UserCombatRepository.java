package com.demo.gametask.repository;

import com.demo.gametask.model.entity.UserCombatEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCombatRepository extends CrudRepository<UserCombatEntity, Integer> {
}
