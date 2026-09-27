package com.guessanime.backend.service;

import com.guessanime.backend.dto.GameChallengeResponse;
import com.guessanime.backend.dto.GameSessionResponse;
import com.guessanime.backend.dto.ScreenshotHintResponse;
import com.guessanime.backend.entity.ChallengeProgress;
import com.guessanime.backend.entity.DailyChallenge;
import com.guessanime.backend.entity.DailyChallengeScreenshot;
import com.guessanime.backend.entity.DailyGame;
import com.guessanime.backend.entity.Difficulty;
import com.guessanime.backend.entity.GameMode;
import com.guessanime.backend.entity.GameSession;
import com.guessanime.backend.repository.ChallengeProgressRepository;
import com.guessanime.backend.repository.DailyGameRepository;
import com.guessanime.backend.repository.GameSessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.text.Normalizer;
import java.util.Comparator;
import java.util.List;

@Service
public class GameSessionService {

    private static final int MAX_HINTS = 4;

    private final GameSessionRepository gameSessionRepository;
    private final ChallengeProgressRepository challengeProgressRepository;
    private final DailyGameRepository dailyGameRepository;

    public GameSessionService(
            GameSessionRepository gameSessionRepository,
            ChallengeProgressRepository challengeProgressRepository,
            DailyGameRepository dailyGameRepository
    ) {
        this.gameSessionRepository = gameSessionRepository;
        this.challengeProgressRepository =
                challengeProgressRepository;
        this.dailyGameRepository = dailyGameRepository;
    }

    @Transactional
    public GameSessionResponse createSession(LocalDate date) {

        DailyGame dailyGame = dailyGameRepository
                .findByGameDate(date)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Aucun DailyGame pour la date " + date
                ));

        GameSession session = new GameSession();
        session.setSessionToken(GameSession.generateToken());
        session.setDailyGame(dailyGame);
        session.setCreatedAt(
                java.time.LocalDateTime.now()
        );

        for (DailyChallenge challenge :
                dailyGame.getChallenges()) {

            ChallengeProgress progress =
                    new ChallengeProgress();

            progress.setDailyChallenge(challenge);
            progress.setCurrentHint(1);
            progress.setCompleted(false);
            progress.setScore(0);

            session.addProgress(progress);
        }

        GameSession savedSession =
                gameSessionRepository.save(session);

        return toResponse(savedSession);
    }

    @Transactional(readOnly = true)
    public GameSessionResponse getSession(
            String sessionToken
    ) {

        GameSession session = gameSessionRepository
                .findBySessionToken(sessionToken)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Session introuvable."
                ));

        return toResponse(session);
    }

    @Transactional
    public GameChallengeResponse submitAnswer(
            String sessionToken,
            Long challengeId,
            String answer
    ) {

        GameSession session = gameSessionRepository
                .findBySessionToken(sessionToken)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Session introuvable."
                ));

        ChallengeProgress progress =
                challengeProgressRepository
                        .findByGameSessionAndDailyChallengeId(
                                session,
                                challengeId
                        )
                        .orElseThrow(() -> new IllegalArgumentException(
                                "Challenge introuvable dans cette session."
                        ));

        DailyChallenge challenge =
                progress.getDailyChallenge();

        if (challenge.getMode() != GameMode.SCREENSHOT) {
            throw new IllegalStateException(
                    "Le mode " + challenge.getMode()
                            + " n'est pas encore disponible."
            );
        }

        if (progress.isCompleted()) {
            throw new IllegalStateException(
                    "Ce challenge est déjà terminé."
            );
        }

        boolean correct = normalize(answer)
                .equals(normalize(challenge.getAnime().getTitle()));

        if (correct) {

            progress.setCompleted(true);

            progress.setScore(
                    getScore(
                            challenge.getDifficulty(),
                            progress.getCurrentHint()
                    )
            );

        } else if (progress.getCurrentHint() < MAX_HINTS) {

            progress.setCurrentHint(
                    progress.getCurrentHint() + 1
            );

        } else {

            progress.setCompleted(true);
            progress.setScore(0);
        }

        challengeProgressRepository.save(progress);

        return toChallengeResponse(progress);
    }

    private GameSessionResponse toResponse(
            GameSession session
    ) {

        GameSessionResponse response =
                new GameSessionResponse();

        response.setSessionToken(
                session.getSessionToken()
        );

        response.setGameDate(
                session.getDailyGame().getGameDate()
        );

        List<GameChallengeResponse> challenges =
                session.getProgresses()
                        .stream()
                        .map(this::toChallengeResponse)
                        .sorted(
                                Comparator.comparing(
                                        GameChallengeResponse
                                                ::getChallengeOrder
                                )
                        )
                        .toList();

        response.setChallenges(challenges);

        return response;
    }

    private GameChallengeResponse toChallengeResponse(
            ChallengeProgress progress
    ) {

        DailyChallenge challenge =
                progress.getDailyChallenge();

        GameChallengeResponse response =
                new GameChallengeResponse();

        response.setChallengeId(
                challenge.getId()
        );

        response.setMode(
                challenge.getMode()
        );

        response.setDifficulty(
                challenge.getDifficulty()
        );

        response.setChallengeOrder(
                challenge.getChallengeOrder()
        );

        response.setCurrentHint(
                progress.getCurrentHint()
        );

        response.setMaxHints(MAX_HINTS);

        response.setCompleted(
                progress.isCompleted()
        );

        response.setScore(
                progress.getScore()
        );

        /*
         * Pour le moment, seul le mode Screenshot
         * possède des indices disponibles.
         */
        if (challenge.getMode() == GameMode.SCREENSHOT
                && !progress.isCompleted()) {

            if (progress.getCurrentHint() <= 3) {

                DailyChallengeScreenshot selectedScreenshot =
                        challenge.getScreenshots()
                                .stream()
                                .filter(
                                        screenshot ->
                                                screenshot
                                                        .getHintOrder()
                                                        .equals(
                                                                progress
                                                                        .getCurrentHint()
                                                        )
                                )
                                .findFirst()
                                .orElseThrow(() ->
                                        new IllegalStateException(
                                                "Screenshot introuvable pour le hint "
                                                        + progress
                                                                .getCurrentHint()
                                        )
                                );

                ScreenshotHintResponse screenshot =
                        new ScreenshotHintResponse();

                screenshot.setHintOrder(
                        selectedScreenshot.getHintOrder()
                );

                screenshot.setPreviewUrl(
                        selectedScreenshot
                                .getScreenshot()
                                .getPreviewUrl()
                );

                screenshot.setOriginalUrl(
                        selectedScreenshot
                                .getScreenshot()
                                .getOriginalUrl()
                );

                response.setScreenshot(screenshot);

            } else {

                String title =
                        challenge.getAnime().getTitle();

                response.setTitleHint(
                        buildTitleHint(title)
                );
            }
        }

        return response;
    }

    private String buildTitleHint(String title) {

        if (title == null || title.isBlank()) {
            return "";
        }

        String normalized = title.trim();

        int letterCount = normalized
                .replaceAll("[^\\p{L}\\p{N}]", "")
                .length();

        String firstLetter =
                normalized.substring(0, 1);

        return firstLetter + " (" + letterCount + " lettres)";
    }

    private int getScore(
            Difficulty difficulty,
            int hint
    ) {

        return switch (difficulty) {

            case EASY -> switch (hint) {
                case 1 -> 3000;
                case 2 -> 2500;
                case 3 -> 1500;
                default -> 500;
            };

            case MEDIUM -> switch (hint) {
                case 1 -> 7000;
                case 2 -> 5500;
                case 3 -> 3500;
                default -> 1500;
            };

            case HARD -> switch (hint) {
                case 1 -> 10000;
                case 2 -> 8000;
                case 3 -> 5000;
                default -> 2000;
            };
        };
    }

    private String normalize(String value) {

        if (value == null) {
            return "";
        }

        String normalized =
                Normalizer.normalize(
                        value,
                        Normalizer.Form.NFD
                );

        return normalized
                .replaceAll("\\p{M}", "")
                .toLowerCase()
                .replaceAll("[^a-z0-9]", "");
    }
}