package com.guessanime.backend.service;

import com.guessanime.backend.entity.Anime;
import com.guessanime.backend.entity.DailyChallenge;
import com.guessanime.backend.entity.DailyGame;
import com.guessanime.backend.entity.DailyGameStatus;
import com.guessanime.backend.entity.Difficulty;
import com.guessanime.backend.entity.GameMode;
import com.guessanime.backend.repository.AnimeRepository;
import com.guessanime.backend.repository.DailyChallengeRepository;
import com.guessanime.backend.repository.DailyGameRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DailyGameService {

    private final DailyGameRepository dailyGameRepository;
    private final AnimeRepository animeRepository;
    private final ScreenshotService screenshotService;

    public DailyGameService(
            DailyGameRepository dailyGameRepository,
            DailyChallengeRepository dailyChallengeRepository,
            AnimeRepository animeRepository,
            ScreenshotService screenshotService
    ) {
        this.dailyGameRepository = dailyGameRepository;
        this.animeRepository = animeRepository;
        this.screenshotService = screenshotService;
    }

    @Transactional
    public DailyGame generateDailyGame(LocalDate date) {

        if (dailyGameRepository.findByGameDate(date).isPresent()) {
            throw new IllegalStateException(
                    "Un DailyGame existe déjà pour la date " + date
            );
        }

        List<Anime> easyAnimes =
                animeRepository.findByPopularityRankBetween(1, 1000);

        List<Anime> mediumAnimes =
                animeRepository.findByPopularityRankBetween(1001, 2000);

        List<Anime> hardAnimes =
                animeRepository.findByPopularityRankBetween(2001, 3000);

        validatePool(easyAnimes, Difficulty.EASY);
        validatePool(mediumAnimes, Difficulty.MEDIUM);
        validatePool(hardAnimes, Difficulty.HARD);

        DailyGame dailyGame = new DailyGame();
        dailyGame.setGameDate(date);
        dailyGame.setStatus(DailyGameStatus.DRAFT);

        int challengeOrder = 1;

        for (GameMode mode : GameMode.values()) {

            Set<Long> usedAnimeIds = new HashSet<>();

            for (Difficulty difficulty : Difficulty.values()) {

                Anime anime = selectRandomAnime(
                        getPoolForDifficulty(
                                difficulty,
                                easyAnimes,
                                mediumAnimes,
                                hardAnimes
                        ),
                        usedAnimeIds
                );

                DailyChallenge challenge = new DailyChallenge();

                challenge.setAnime(anime);
                challenge.setMode(mode);
                challenge.setDifficulty(difficulty);
                challenge.setChallengeOrder(challengeOrder++);

                dailyGame.addChallenge(challenge);

                if (mode == GameMode.SCREENSHOT) {
                    screenshotService.assignScreenshotsToChallenge(
                            challenge
                    );
                }

                usedAnimeIds.add(anime.getId());
            }
        }

        dailyGame.setStatus(DailyGameStatus.PUBLISHED);

        return dailyGameRepository.save(dailyGame);
    }

    @Transactional(readOnly = true)
    public DailyGame getDailyGame(LocalDate date) {
        return dailyGameRepository
                .findByGameDate(date)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Aucun DailyGame pour la date " + date
                ));
    }

    private List<Anime> getPoolForDifficulty(
            Difficulty difficulty,
            List<Anime> easyAnimes,
            List<Anime> mediumAnimes,
            List<Anime> hardAnimes
    ) {
        return switch (difficulty) {
            case EASY -> easyAnimes;
            case MEDIUM -> mediumAnimes;
            case HARD -> hardAnimes;
        };
    }

    private Anime selectRandomAnime(
            List<Anime> pool,
            Set<Long> usedAnimeIds
    ) {
        List<Anime> availableAnimes = new ArrayList<>();

        for (Anime anime : pool) {
            if (!usedAnimeIds.contains(anime.getId())) {
                availableAnimes.add(anime);
            }
        }

        if (availableAnimes.isEmpty()) {
            throw new IllegalStateException(
                    "Aucun anime disponible pour générer le challenge."
            );
        }

        Collections.shuffle(availableAnimes);

        return availableAnimes.get(0);
    }

    private void validatePool(
            List<Anime> pool,
            Difficulty difficulty
    ) {
        if (pool.size() < 5) {
            throw new IllegalStateException(
                    "Le pool " + difficulty + " contient seulement "
                            + pool.size() + " anime."
            );
        }
    }
    @Transactional
    public void deleteDailyGame(LocalDate date) {
        DailyGame dailyGame = dailyGameRepository
                .findByGameDate(date)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Aucun DailyGame pour la date " + date
                ));

        dailyGameRepository.delete(dailyGame);
    }
}