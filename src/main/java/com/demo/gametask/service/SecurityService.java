package com.demo.gametask.service;

import com.demo.gametask.model.user.UserIdentity;

public interface SecurityService {
    UserIdentity getLoggedInUser();

    void autoLogin(String username, String password);
}
