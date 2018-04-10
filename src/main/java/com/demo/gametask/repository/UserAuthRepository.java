package com.demo.gametask.repository;

import com.demo.gametask.model.entity.UserAuthEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthRepository extends CrudRepository<UserAuthEntity, Integer> {
}
