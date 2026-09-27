package com.guessanime.backend.repository;

import com.guessanime.backend.entity.DailyChallengeScreenshot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DailyChallengeScreenshotRepository
        extends JpaRepository<DailyChallengeScreenshot, Long> {

    List<DailyChallengeScreenshot> findByDailyChallengeOrderByHintOrder(
            Long dailyChallengeId
    );
}