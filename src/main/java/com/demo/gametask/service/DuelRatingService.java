package com.demo.gametask.service;

import com.demo.gametask.model.entity.UserDuelEntity;
import com.demo.gametask.repository.UserDuelRepository;
import org.springframework.stereotype.Service;

@Service
public class DuelRatingService {

    private final UserDuelRepository userDuelRepository;

    public DuelRatingService(UserDuelRepository userDuelRepository) {
        this.userDuelRepository = userDuelRepository;
    }


    public UserDuelEntity getRating(int userId) {
        return userDuelRepository.findById(userId).orElseThrow(NullPointerException::new);
    }
}
