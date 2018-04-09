package com.demo.gametask.service;

import com.demo.gametask.model.UserEntity;
import com.demo.gametask.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void save(UserEntity user) {
        userRepository.save(user);
    }

    @Override
    public UserEntity findByName(String name) {
        return userRepository.findByName(name);
    }
}
