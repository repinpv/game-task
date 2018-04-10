package com.demo.gametask.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_duel")
public class UserDuelEntity {

    private static final int INITIAL_RATING = 0;

    public static UserDuelEntity create(UserEntity user) {
        final UserDuelEntity userDuelEntity = new UserDuelEntity();
        userDuelEntity.setUserId(user.getId());
        userDuelEntity.setRating(INITIAL_RATING);
        return userDuelEntity;
    }

    @Id
    @Column(name ="user_id")
    private int userId;
    @Column(name ="rating")
    private int rating;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
