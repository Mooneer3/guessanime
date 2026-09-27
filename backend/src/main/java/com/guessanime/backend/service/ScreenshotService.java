package com.guessanime.backend.service;

import com.guessanime.backend.dto.ShikimoriScreenshot;
import com.guessanime.backend.entity.Anime;
import com.guessanime.backend.entity.DailyChallenge;
import com.guessanime.backend.entity.DailyChallengeScreenshot;
import com.guessanime.backend.entity.GameMode;
import com.guessanime.backend.entity.Screenshot;
import com.guessanime.backend.entity.ScreenshotProvider;
import com.guessanime.backend.repository.AnimeRepository;
import com.guessanime.backend.repository.DailyChallengeScreenshotRepository;
import com.guessanime.backend.repository.ScreenshotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ScreenshotService {

    private static final int MINIMUM_SCREENSHOTS = 3;

    private final ScreenshotRepository screenshotRepository;
    private final ShikimoriScreenshotProvider shikimoriProvider;
    private final AnimeRepository animeRepository;
    private final DailyChallengeScreenshotRepository
            dailyChallengeScreenshotRepository;

    public ScreenshotService(
            ScreenshotRepository screenshotRepository,
            ShikimoriScreenshotProvider shikimoriProvider,
            AnimeRepository animeRepository,
            DailyChallengeScreenshotRepository
                    dailyChallengeScreenshotRepository
    ) {
        this.screenshotRepository = screenshotRepository;
        this.shikimoriProvider = shikimoriProvider;
        this.animeRepository = animeRepository;
        this.dailyChallengeScreenshotRepository =
                dailyChallengeScreenshotRepository;
    }

    @Transactional
    public List<Screenshot> importScreenshots(Anime anime) {

        if (anime.getMalId() == null) {
            throw new IllegalStateException(
                    "L'anime " + anime.getTitle()
                            + " n'a pas de malId."
            );
        }

        List<Screenshot> existingScreenshots =
                screenshotRepository.findByAnime(anime);

        if (!existingScreenshots.isEmpty()) {
            return existingScreenshots;
        }

        List<ShikimoriScreenshot> remoteScreenshots =
                shikimoriProvider.getScreenshots(anime.getMalId());

        List<Screenshot> importedScreenshots = new ArrayList<>();

        for (ShikimoriScreenshot remote : remoteScreenshots) {

            String originalUrl =
                    shikimoriProvider.buildImageUrl(
                            remote.getOriginal()
                    );

            String previewUrl =
                    shikimoriProvider.buildImageUrl(
                            remote.getPreview()
                    );

            if (originalUrl == null) {
                continue;
            }

            Screenshot screenshot = new Screenshot();

            screenshot.setAnime(anime);
            screenshot.setProvider(ScreenshotProvider.SHIKIMORI);
            screenshot.setOriginalUrl(originalUrl);
            screenshot.setPreviewUrl(previewUrl);
            screenshot.setStatus("AVAILABLE");

            importedScreenshots.add(screenshot);
        }

        if (importedScreenshots.size() < MINIMUM_SCREENSHOTS) {
            throw new IllegalStateException(
                    "L'anime " + anime.getTitle()
                            + " possède seulement "
                            + importedScreenshots.size()
                            + " screenshots utilisables."
            );
        }

        return screenshotRepository.saveAll(importedScreenshots);
    }

    @Transactional(readOnly = true)
    public List<Screenshot> getScreenshots(Anime anime) {
        return screenshotRepository.findByAnime(anime);
    }

    @Transactional
    public void importPoolScreenshots() {

        List<Anime> animes = animeRepository.findAll();

        int success = 0;
        int failed = 0;

        for (Anime anime : animes) {

            try {
                importScreenshots(anime);

                success++;

                System.out.println(
                        "Screenshots : " + success
                                + "/" + animes.size()
                                + " - " + anime.getTitle()
                );

            } catch (Exception e) {

                failed++;

                System.out.println(
                        "Screenshots : échec - "
                                + anime.getTitle()
                                + " - "
                                + e.getMessage()
                );
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();

                throw new RuntimeException(
                        "Import screenshots interrompu",
                        e
                );
            }
        }

        System.out.println(
                "Import screenshots terminé : "
                        + success + " succès, "
                        + failed + " échecs."
        );
    }

    public void assignScreenshotsToChallenge(
            DailyChallenge challenge
    ) {

        if (challenge.getMode() != GameMode.SCREENSHOT) {
            return;
        }

        List<Screenshot> screenshots =
                screenshotRepository.findByAnime(
                        challenge.getAnime()
                );

        if (screenshots.size() < MINIMUM_SCREENSHOTS) {
            throw new IllegalStateException(
                    "Pas assez de screenshots pour "
                            + challenge.getAnime().getTitle()
            );
        }

        Collections.shuffle(screenshots);

        for (int i = 0; i < MINIMUM_SCREENSHOTS; i++) {

            DailyChallengeScreenshot challengeScreenshot =
                    new DailyChallengeScreenshot();

            challengeScreenshot.setScreenshot(
                    screenshots.get(i)
            );

            challengeScreenshot.setHintOrder(i + 1);

            challenge.addScreenshot(challengeScreenshot);
        }
    }
}