package com.demo.gametask.service;

import com.demo.gametask.form.RegistrationForm;
import com.demo.gametask.model.entity.UserEntity;

import java.util.Optional;

public interface UserService {

    void save(RegistrationForm registrationForm);

    Optional<UserEntity> findByName(String name);
}
