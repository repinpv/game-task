package com.demo.gametask.model.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class UserDetailsImpl extends User implements UserIdentity {

    private final int id;

    public UserDetailsImpl(UserIdentity userIdentity,
                           String password,
                           Collection<? extends GrantedAuthority> authorities) {
        super(userIdentity.getUsername(), password, authorities);
        this.id = userIdentity.getId();
    }

    @Override
    public int getId() {
        return id;
    }
}
