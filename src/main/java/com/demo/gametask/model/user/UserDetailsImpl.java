package com.demo.gametask.model.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class UserDetailsImpl extends User implements UserIdentity {

    private final int id;

    public UserDetailsImpl(int id,
                           String username,
                           String password,
                           Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }
}
