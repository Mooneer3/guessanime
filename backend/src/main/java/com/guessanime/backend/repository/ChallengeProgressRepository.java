package com.guessanime.backend.repository;

import com.guessanime.backend.entity.ChallengeProgress;
import com.guessanime.backend.entity.GameSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChallengeProgressRepository
        extends JpaRepository<ChallengeProgress, Long> {

    Optional<ChallengeProgress> findByGameSessionAndDailyChallengeId(
            GameSession gameSession,
            Long dailyChallengeId
    );
}