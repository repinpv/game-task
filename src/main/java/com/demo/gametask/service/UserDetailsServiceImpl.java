package com.demo.gametask.service;

import com.demo.gametask.model.entity.UserAuthEntity;
import com.demo.gametask.model.entity.UserEntity;
import com.demo.gametask.model.user.UserDetailsImpl;
import com.demo.gametask.repository.UserAuthRepository;
import com.demo.gametask.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final List<SimpleGrantedAuthority> DEFAULT_AUTHORITY
            = Collections.singletonList(new SimpleGrantedAuthority("USER"));

    private final UserRepository userRepository;
    private final UserAuthRepository userAuthRepository;

    public UserDetailsServiceImpl(UserRepository userRepository, UserAuthRepository userAuthRepository) {
        this.userRepository = userRepository;
        this.userAuthRepository = userAuthRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Optional<UserEntity> userOptional = userRepository.findByUsername(username);
        final UserEntity user = userOptional.orElseThrow(() -> new UsernameNotFoundException(username));
        final Optional<UserAuthEntity> userAuthOptional = userAuthRepository.findById(user.getId());

        //noinspection ConstantConditions
        return new UserDetailsImpl(user, userAuthOptional.get().getPassword(), DEFAULT_AUTHORITY);
    }
}
