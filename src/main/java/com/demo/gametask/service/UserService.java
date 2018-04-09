package com.demo.gametask.service;

import com.demo.gametask.model.UserEntity;

public interface UserService {

    void save(UserEntity user);

    UserEntity findByName(String name);
}
