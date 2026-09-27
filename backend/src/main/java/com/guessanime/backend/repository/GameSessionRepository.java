package com.guessanime.backend.repository;

import com.guessanime.backend.entity.GameSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameSessionRepository
        extends JpaRepository<GameSession, Long> {

    Optional<GameSession> findBySessionToken(String sessionToken);
}