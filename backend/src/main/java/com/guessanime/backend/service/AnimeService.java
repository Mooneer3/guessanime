package com.guessanime.backend.service;

import com.guessanime.backend.dto.AniListAnime;
import com.guessanime.backend.entity.Anime;
import com.guessanime.backend.repository.AnimeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnimeService {

    private final AnimeRepository animeRepository;
    private final AniListService aniListService;

    public AnimeService(
            AnimeRepository animeRepository,
            AniListService aniListService
    ) {
        this.animeRepository = animeRepository;
        this.aniListService = aniListService;
    }

    public List<Anime> getAllAnimes() {
        return animeRepository.findAll();
    }

    public Anime getAnimeById(Long id) {
        return animeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Anime introuvable : " + id
                ));
    }

    public Anime createAnime(Anime anime) {
        return animeRepository.save(anime);
    }

    public Anime importFromAniList(AniListAnime aniListAnime) {

        Anime anime = animeRepository
                .findByAnilistId(aniListAnime.getId())
                .orElseGet(Anime::new);

        anime.setAnilistId(
                aniListAnime.getId()
        );

        anime.setMalId(
                aniListAnime.getIdMal()
        );

        anime.setTitle(
                aniListAnime.getTitle().getEnglish() != null
                        ? aniListAnime.getTitle().getEnglish()
                        : aniListAnime.getTitle().getRomaji()
        );

        anime.setTitleRomaji(
                aniListAnime.getTitle().getRomaji()
        );

        anime.setTitleNative(
                aniListAnime.getTitle().getNativeTitle()
        );

        anime.setPopularity(
                aniListAnime.getPopularity()
        );

        anime.setType(
                aniListAnime.getType()
        );

        anime.setFormat(
                aniListAnime.getFormat()
        );

        anime.setReleaseYear(
                aniListAnime.getSeasonYear()
        );

        return animeRepository.save(anime);
    }

    public List<Anime> importPopularPool(
            List<AniListAnime> animes
    ) {

        List<Anime> importedAnimes =
                new ArrayList<>();

        for (int i = 0; i < animes.size(); i++) {

            AniListAnime aniListAnime =
                    animes.get(i);

            Anime anime = animeRepository
                    .findByAnilistId(
                            aniListAnime.getId()
                    )
                    .orElseGet(Anime::new);

            anime.setAnilistId(
                    aniListAnime.getId()
            );

            anime.setMalId(
                    aniListAnime.getIdMal()
            );

            anime.setTitle(
                    aniListAnime.getTitle().getEnglish() != null
                            ? aniListAnime.getTitle().getEnglish()
                            : aniListAnime.getTitle().getRomaji()
            );

            anime.setTitleRomaji(
                    aniListAnime.getTitle().getRomaji()
            );

            anime.setTitleNative(
                    aniListAnime.getTitle().getNativeTitle()
            );

            anime.setPopularity(
                    aniListAnime.getPopularity()
            );

            anime.setPopularityRank(
                    i + 1
            );

            anime.setType(
                    aniListAnime.getType()
            );

            anime.setFormat(
                    aniListAnime.getFormat()
            );

            anime.setReleaseYear(
                    aniListAnime.getSeasonYear()
            );

            importedAnimes.add(anime);
        }

        return animeRepository.saveAll(
                importedAnimes
        );
    }

    @Transactional
    public int synchronizeTitlesFromAniList() {

        List<Anime> databaseAnimes =
                animeRepository.findAll();

        System.out.println(
                "Début synchronisation des titres : "
                        + databaseAnimes.size()
                        + " anime en base."
        );

        List<AniListAnime> aniListAnimes =
                aniListService.getTopAnimes(
                        databaseAnimes.size()
                );

        Map<Long, AniListAnime> aniListById =
                new HashMap<>();

        for (AniListAnime aniListAnime : aniListAnimes) {
            aniListById.put(
                    aniListAnime.getId(),
                    aniListAnime
            );
        }

        int synchronizedCount = 0;
        int notFoundCount = 0;

        for (Anime anime : databaseAnimes) {

            AniListAnime aniListAnime =
                    aniListById.get(
                            anime.getAnilistId()
                    );

            if (aniListAnime == null) {

                notFoundCount++;

                System.out.println(
                        "Anime non trouvé dans le pool AniList : "
                                + anime.getTitle()
                                + " (AniList ID : "
                                + anime.getAnilistId()
                                + ")"
                );

                continue;
            }

            if (aniListAnime.getTitle() == null) {
                continue;
            }

            anime.setTitle(
                    aniListAnime.getTitle().getEnglish() != null
                            ? aniListAnime.getTitle().getEnglish()
                            : aniListAnime.getTitle().getRomaji()
            );

            anime.setTitleRomaji(
                    aniListAnime.getTitle().getRomaji()
            );

            anime.setTitleNative(
                    aniListAnime.getTitle().getNativeTitle()
            );

            synchronizedCount++;
        }

        animeRepository.saveAll(databaseAnimes);

        System.out.println(
                "Synchronisation terminée : "
                        + synchronizedCount
                        + " anime synchronisés, "
                        + notFoundCount
                        + " non trouvés."
        );

        return synchronizedCount;
    }

    public void clearAllAnimes() {
        animeRepository.deleteAll();
    }
}