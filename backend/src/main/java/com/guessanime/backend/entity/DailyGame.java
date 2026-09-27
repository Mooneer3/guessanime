package com.guessanime.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class DailyGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private LocalDate gameDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DailyGameStatus status;

    @OneToMany(
            mappedBy = "dailyGame",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<DailyChallenge> challenges =
            new LinkedHashSet<>();

    public DailyGame() {
    }

    public Long getId() {
        return id;
    }

    public LocalDate getGameDate() {
        return gameDate;
    }

    public void setGameDate(LocalDate gameDate) {
        this.gameDate = gameDate;
    }

    public DailyGameStatus getStatus() {
        return status;
    }

    public void setStatus(DailyGameStatus status) {
        this.status = status;
    }

    public Set<DailyChallenge> getChallenges() {
        return challenges;
    }

    public void setChallenges(
            Set<DailyChallenge> challenges
    ) {
        this.challenges = challenges;
    }

    public void addChallenge(
            DailyChallenge challenge
    ) {
        challenges.add(challenge);
        challenge.setDailyGame(this);
    }
}