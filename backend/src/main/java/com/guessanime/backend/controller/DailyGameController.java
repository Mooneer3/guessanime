package com.guessanime.backend.controller;

import com.guessanime.backend.dto.AnimeSummaryResponse;
import com.guessanime.backend.dto.DailyChallengeResponse;
import com.guessanime.backend.dto.DailyChallengeScreenshotResponse;
import com.guessanime.backend.dto.DailyGameResponse;
import com.guessanime.backend.entity.DailyChallenge;
import com.guessanime.backend.entity.DailyChallengeScreenshot;
import com.guessanime.backend.entity.DailyGame;
import com.guessanime.backend.service.DailyGameService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/daily-games")
public class DailyGameController {

    private final DailyGameService dailyGameService;

    public DailyGameController(DailyGameService dailyGameService) {
        this.dailyGameService = dailyGameService;
    }

    @PostMapping("/{date}/generate")
    public DailyGameResponse generateDailyGame(
            @PathVariable LocalDate date
    ) {
        DailyGame dailyGame =
                dailyGameService.generateDailyGame(date);

        return toResponse(dailyGame);
    }

    @GetMapping("/{date}")
    public DailyGameResponse getDailyGame(
            @PathVariable LocalDate date
    ) {
        DailyGame dailyGame =
                dailyGameService.getDailyGame(date);

        return toResponse(dailyGame);
    }

    private DailyGameResponse toResponse(DailyGame dailyGame) {

        DailyGameResponse response = new DailyGameResponse();

        response.setId(dailyGame.getId());
        response.setGameDate(dailyGame.getGameDate());
        response.setStatus(dailyGame.getStatus());

        List<DailyChallengeResponse> challenges =
            dailyGame.getChallenges()
                    .stream()
                    .sorted(
                            java.util.Comparator.comparing(
                                    DailyChallenge::getChallengeOrder
                            )
                    )
                    .map(this::toChallengeResponse)
                    .toList();

        response.setChallenges(challenges);

        return response;
    }

    private DailyChallengeResponse toChallengeResponse(
            DailyChallenge challenge
    ) {

        DailyChallengeResponse response =
                new DailyChallengeResponse();

        response.setId(challenge.getId());
        response.setMode(challenge.getMode());
        response.setDifficulty(challenge.getDifficulty());
        response.setChallengeOrder(
                challenge.getChallengeOrder()
        );

        AnimeSummaryResponse anime =
                new AnimeSummaryResponse();

        anime.setId(challenge.getAnime().getId());
        anime.setAnilistId(
                challenge.getAnime().getAnilistId()
        );
        anime.setTitle(
                challenge.getAnime().getTitle()
        );
        anime.setFormat(
                challenge.getAnime().getFormat()
        );
        anime.setReleaseYear(
                challenge.getAnime().getReleaseYear()
        );
        anime.setPopularityRank(
                challenge.getAnime().getPopularityRank()
        );

        response.setAnime(anime);

        List<DailyChallengeScreenshotResponse> screenshots =
                challenge.getScreenshots()
                        .stream()
                        .sorted(
                                java.util.Comparator.comparing(
                                        DailyChallengeScreenshot::getHintOrder
                                )
                        )
                        .map(this::toScreenshotResponse)
                        .toList();

        response.setScreenshots(screenshots);

        return response;
    }

    private DailyChallengeScreenshotResponse toScreenshotResponse(
            DailyChallengeScreenshot challengeScreenshot
    ) {

        DailyChallengeScreenshotResponse response =
                new DailyChallengeScreenshotResponse();

        response.setHintOrder(
                challengeScreenshot.getHintOrder()
        );

        response.setScreenshotId(
                challengeScreenshot.getScreenshot().getId()
        );

        response.setOriginalUrl(
                challengeScreenshot
                        .getScreenshot()
                        .getOriginalUrl()
        );

        response.setPreviewUrl(
                challengeScreenshot
                        .getScreenshot()
                        .getPreviewUrl()
        );

        return response;
    }
    @DeleteMapping("/{date}")
    public void deleteDailyGame(
            @PathVariable LocalDate date
    ) {
        dailyGameService.deleteDailyGame(date);
    }
}

