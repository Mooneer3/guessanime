package com.guessanime.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(
        name = "challenge_progress",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_session_challenge",
                columnNames = {
                        "game_session_id",
                        "daily_challenge_id"
                }
        )
)
public class ChallengeProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "game_session_id", nullable = false)
    private GameSession gameSession;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "daily_challenge_id", nullable = false)
    private DailyChallenge dailyChallenge;

    @Column(nullable = false)
    private Integer currentHint;

    @Column(nullable = false)
    private boolean completed;

    @Column(nullable = false)
    private Integer score;

    public ChallengeProgress() {
    }

    public Long getId() {
        return id;
    }

    public GameSession getGameSession() {
        return gameSession;
    }

    public void setGameSession(GameSession gameSession) {
        this.gameSession = gameSession;
    }

    public DailyChallenge getDailyChallenge() {
        return dailyChallenge;
    }

    public void setDailyChallenge(DailyChallenge dailyChallenge) {
        this.dailyChallenge = dailyChallenge;
    }

    public Integer getCurrentHint() {
        return currentHint;
    }

    public void setCurrentHint(Integer currentHint) {
        this.currentHint = currentHint;
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
}