package com.demo.gametask.service;

public interface SecurityService {
    String findLoggedInUsername();

    void autologin(String username, String password);
}
