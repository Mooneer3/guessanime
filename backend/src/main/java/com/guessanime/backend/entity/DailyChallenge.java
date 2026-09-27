package com.guessanime.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class DailyChallenge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "daily_game_id", nullable = false)
    @JsonIgnore
    private DailyGame dailyGame;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "anime_id", nullable = false)
    private Anime anime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GameMode mode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Difficulty difficulty;

    @Column(nullable = false)
    private Integer challengeOrder;

    @OneToMany(
            mappedBy = "dailyChallenge",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnore
    private Set<DailyChallengeScreenshot> screenshots =
            new LinkedHashSet<>();

    public DailyChallenge() {
    }

    public Long getId() {
        return id;
    }

    public DailyGame getDailyGame() {
        return dailyGame;
    }

    public void setDailyGame(DailyGame dailyGame) {
        this.dailyGame = dailyGame;
    }

    public Anime getAnime() {
        return anime;
    }

    public void setAnime(Anime anime) {
        this.anime = anime;
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

    public Set<DailyChallengeScreenshot> getScreenshots() {
        return screenshots;
    }

    public void setScreenshots(
            Set<DailyChallengeScreenshot> screenshots
    ) {
        this.screenshots = screenshots;
    }

    public void addScreenshot(
            DailyChallengeScreenshot screenshot
    ) {
        screenshots.add(screenshot);
        screenshot.setDailyChallenge(this);
    }
}