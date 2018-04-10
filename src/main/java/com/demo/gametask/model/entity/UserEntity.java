package com.demo.gametask.model.entity;

import com.demo.gametask.model.user.UserIdentity;

import javax.persistence.*;

@Entity
@Table(name = "user", indexes = {@Index(columnList = "username")})
public class UserEntity implements UserIdentity {

    public static UserEntity create(final String name) {
        final UserEntity userEntity = new UserEntity();
        userEntity.setUsername(name);
        return userEntity;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
    @SequenceGenerator(name = "user_id_seq", sequenceName = "user_id_seq", allocationSize = 10)
    @Column(name = "id")
    private int id;
    @Column(name = "username")
    private String username;

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
