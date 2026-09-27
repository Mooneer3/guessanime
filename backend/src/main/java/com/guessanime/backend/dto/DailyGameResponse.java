package com.guessanime.backend.dto;

import com.guessanime.backend.entity.DailyGameStatus;

import java.time.LocalDate;
import java.util.List;

public class DailyGameResponse {

    private Long id;
    private LocalDate gameDate;
    private DailyGameStatus status;
    private List<DailyChallengeResponse> challenges;

    public DailyGameResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<DailyChallengeResponse> getChallenges() {
        return challenges;
    }

    public void setChallenges(List<DailyChallengeResponse> challenges) {
        this.challenges = challenges;
    }
}