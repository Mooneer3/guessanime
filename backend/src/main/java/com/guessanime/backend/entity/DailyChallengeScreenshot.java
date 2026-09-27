package com.guessanime.backend.entity;

import jakarta.persistence.*;

@Entity
public class DailyChallengeScreenshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "daily_challenge_id", nullable = false)
    private DailyChallenge dailyChallenge;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "screenshot_id", nullable = false)
    private Screenshot screenshot;

    @Column(nullable = false)
    private Integer hintOrder;

    public DailyChallengeScreenshot() {
    }

    public Long getId() {
        return id;
    }

    public DailyChallenge getDailyChallenge() {
        return dailyChallenge;
    }

    public void setDailyChallenge(DailyChallenge dailyChallenge) {
        this.dailyChallenge = dailyChallenge;
    }

    public Screenshot getScreenshot() {
        return screenshot;
    }

    public void setScreenshot(Screenshot screenshot) {
        this.screenshot = screenshot;
    }

    public Integer getHintOrder() {
        return hintOrder;
    }

    public void setHintOrder(Integer hintOrder) {
        this.hintOrder = hintOrder;
    }
}