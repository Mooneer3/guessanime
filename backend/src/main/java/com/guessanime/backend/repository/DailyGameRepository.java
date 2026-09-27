package com.guessanime.backend.repository;

import com.guessanime.backend.entity.DailyGame;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface DailyGameRepository extends JpaRepository<DailyGame, Long> {

    @EntityGraph(attributePaths = {
            "challenges",
            "challenges.anime",
            "challenges.screenshots",
            "challenges.screenshots.screenshot"
    })
    Optional<DailyGame> findByGameDate(LocalDate gameDate);
}