package com.guessanime.backend.controller;

import com.guessanime.backend.dto.GameChallengeResponse;
import com.guessanime.backend.dto.GameSessionResponse;
import com.guessanime.backend.service.GameSessionService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/game-sessions")
public class GameSessionController {

    private final GameSessionService gameSessionService;

    public GameSessionController(
            GameSessionService gameSessionService
    ) {
        this.gameSessionService = gameSessionService;
    }

    @PostMapping("/{date}")
    public GameSessionResponse createSession(
            @PathVariable LocalDate date
    ) {
        return gameSessionService.createSession(date);
    }

    @GetMapping("/{sessionToken}")
    public GameSessionResponse getSession(
            @PathVariable String sessionToken
    ) {
        return gameSessionService.getSession(sessionToken);
    }

    @PostMapping("/{sessionToken}/challenges/{challengeId}/answer")
    public GameChallengeResponse submitAnswer(
            @PathVariable String sessionToken,
            @PathVariable Long challengeId,
            @RequestBody Map<String, String> body
    ) {

        String answer = body.get("answer");

        return gameSessionService.submitAnswer(
                sessionToken,
                challengeId,
                answer
        );
    }
}