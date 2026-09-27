package com.guessanime.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "game_session")
public class GameSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String sessionToken;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "daily_game_id", nullable = false)
    private DailyGame dailyGame;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(
            mappedBy = "gameSession",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<ChallengeProgress> progresses = new LinkedHashSet<>();

    public GameSession() {
    }

    public Long getId() {
        return id;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public DailyGame getDailyGame() {
        return dailyGame;
    }

    public void setDailyGame(DailyGame dailyGame) {
        this.dailyGame = dailyGame;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Set<ChallengeProgress> getProgresses() {
        return progresses;
    }

    public void setProgresses(Set<ChallengeProgress> progresses) {
        this.progresses = progresses;
    }

    public void addProgress(ChallengeProgress progress) {
        progresses.add(progress);
        progress.setGameSession(this);
    }

    public static String generateToken() {
        return UUID.randomUUID().toString();
    }
}