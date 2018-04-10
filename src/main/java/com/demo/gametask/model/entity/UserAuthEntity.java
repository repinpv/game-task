package com.demo.gametask.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_auth")
public class UserAuthEntity {

    public static UserAuthEntity create(UserEntity user, String encodedPassword) {
        final UserAuthEntity userAuthEntity = new UserAuthEntity();
        userAuthEntity.setUserId(user.getId());
        userAuthEntity.setPassword(encodedPassword);
        return userAuthEntity;
    }

    @Id
    @Column(name ="user_id")
    private int userId;
    @Column(name ="password")
    private String password = "";

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
