package com.guessanime.backend.repository;

import com.guessanime.backend.entity.DailyChallenge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyChallengeRepository
        extends JpaRepository<DailyChallenge, Long> {
}