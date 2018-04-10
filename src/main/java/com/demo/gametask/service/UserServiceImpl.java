package com.demo.gametask.service;

import com.demo.gametask.form.RegistrationForm;
import com.demo.gametask.model.entity.UserAuthEntity;
import com.demo.gametask.model.entity.UserCombatEntity;
import com.demo.gametask.model.entity.UserDuelEntity;
import com.demo.gametask.model.entity.UserEntity;
import com.demo.gametask.repository.UserAuthRepository;
import com.demo.gametask.repository.UserCombatRepository;
import com.demo.gametask.repository.UserDuelRepository;
import com.demo.gametask.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserAuthRepository userAuthRepository;
    private final UserCombatRepository userCombatRepository;
    private final UserDuelRepository userDuelRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           UserAuthRepository userAuthRepository,
                           UserCombatRepository userCombatRepository,
                           UserDuelRepository userDuelRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.userAuthRepository = userAuthRepository;
        this.userCombatRepository = userCombatRepository;
        this.userDuelRepository = userDuelRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void save(RegistrationForm registrationForm) {

        final UserEntity user = UserEntity.create(registrationForm.getUsername());
        userRepository.save(user);

        final UserAuthEntity userAuthEntity = UserAuthEntity.create(
                user, bCryptPasswordEncoder.encode(registrationForm.getPassword()));
        userAuthRepository.save(userAuthEntity);

        final UserCombatEntity userCombatEntity = UserCombatEntity.create(user);
        userCombatRepository.save(userCombatEntity);

        final UserDuelEntity userDuelEntity = UserDuelEntity.create(user);
        userDuelRepository.save(userDuelEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserEntity> findByName(String name) {
        return userRepository.findByUsername(name);
    }
}
