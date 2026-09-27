package com.guessanime.backend.dto;

import com.guessanime.backend.entity.Difficulty;
import com.guessanime.backend.entity.GameMode;

import java.util.List;

public class DailyChallengeResponse {

    private Long id;
    private GameMode mode;
    private Difficulty difficulty;
    private Integer challengeOrder;
    private AnimeSummaryResponse anime;
    private List<DailyChallengeScreenshotResponse> screenshots;

    public DailyChallengeResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GameMode getMode() {
        return mode;
    }

    public void setMode(GameMode mode) {
        this.mode = mode;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Integer getChallengeOrder() {
        return challengeOrder;
    }

    public void setChallengeOrder(Integer challengeOrder) {
        this.challengeOrder = challengeOrder;
    }

    public AnimeSummaryResponse getAnime() {
        return anime;
    }

    public void setAnime(AnimeSummaryResponse anime) {
        this.anime = anime;
    }

    public List<DailyChallengeScreenshotResponse> getScreenshots() {
        return screenshots;
    }

    public void setScreenshots(
            List<DailyChallengeScreenshotResponse> screenshots
    ) {
        this.screenshots = screenshots;
    }
}