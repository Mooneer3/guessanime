package com.guessanime.backend.dto;

import com.guessanime.backend.entity.Difficulty;
import com.guessanime.backend.entity.GameMode;

public class GameChallengeResponse {

    private Long challengeId;
    private GameMode mode;
    private Difficulty difficulty;
    private Integer challengeOrder;
    private Integer currentHint;
    private Integer maxHints;
    private boolean completed;
    private Integer score;

    private ScreenshotHintResponse screenshot;

    private String titleHint;

    public GameChallengeResponse() {
    }

    public Long getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(Long challengeId) {
        this.challengeId = challengeId;
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

    public Integer getCurrentHint() {
        return currentHint;
    }

    public void setCurrentHint(Integer currentHint) {
        this.currentHint = currentHint;
    }

    public Integer getMaxHints() {
        return maxHints;
    }

    public void setMaxHints(Integer maxHints) {
        this.maxHints = maxHints;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public ScreenshotHintResponse getScreenshot() {
        return screenshot;
    }

    public void setScreenshot(
            ScreenshotHintResponse screenshot
    ) {
        this.screenshot = screenshot;
    }

    public String getTitleHint() {
        return titleHint;
    }

    public void setTitleHint(String titleHint) {
        this.titleHint = titleHint;
    }
}