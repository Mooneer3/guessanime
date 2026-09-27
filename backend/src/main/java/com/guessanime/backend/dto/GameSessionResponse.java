package com.guessanime.backend.dto;

import java.time.LocalDate;
import java.util.List;

public class GameSessionResponse {

    private String sessionToken;
    private LocalDate gameDate;
    private List<GameChallengeResponse> challenges;

    public GameSessionResponse() {
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public LocalDate getGameDate() {
        return gameDate;
    }

    public void setGameDate(LocalDate gameDate) {
        this.gameDate = gameDate;
    }

    public List<GameChallengeResponse> getChallenges() {
        return challenges;
    }

    public void setChallenges(
            List<GameChallengeResponse> challenges
    ) {
        this.challenges = challenges;
    }
}