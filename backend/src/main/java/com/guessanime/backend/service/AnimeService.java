package com.guessanime.backend.service;

import com.guessanime.backend.dto.AniListAnime;
import com.guessanime.backend.entity.Anime;
import com.guessanime.backend.repository.AnimeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnimeService {

    private final AnimeRepository animeRepository;

    public AnimeService(AnimeRepository animeRepository) {
        this.animeRepository = animeRepository;
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

        anime.setAnilistId(aniListAnime.getId());
        anime.setMalId(aniListAnime.getIdMal());

        anime.setTitle(
                aniListAnime.getTitle().getEnglish() != null
                        ? aniListAnime.getTitle().getEnglish()
                        : aniListAnime.getTitle().getRomaji()
        );

        anime.setPopularity(aniListAnime.getPopularity());
        anime.setType(aniListAnime.getType());
        anime.setFormat(aniListAnime.getFormat());
        anime.setReleaseYear(aniListAnime.getSeasonYear());

        return animeRepository.save(anime);
    }

    public List<Anime> importPopularPool(List<AniListAnime> animes) {

        List<Anime> importedAnimes = new ArrayList<>();

        for (int i = 0; i < animes.size(); i++) {

            AniListAnime aniListAnime = animes.get(i);

            Anime anime = animeRepository
                    .findByAnilistId(aniListAnime.getId())
                    .orElseGet(Anime::new);

            anime.setAnilistId(aniListAnime.getId());
            anime.setMalId(aniListAnime.getIdMal());

            anime.setTitle(
                    aniListAnime.getTitle().getEnglish() != null
                            ? aniListAnime.getTitle().getEnglish()
                            : aniListAnime.getTitle().getRomaji()
            );

            anime.setPopularity(aniListAnime.getPopularity());
            anime.setPopularityRank(i + 1);
            anime.setType(aniListAnime.getType());
            anime.setFormat(aniListAnime.getFormat());
            anime.setReleaseYear(aniListAnime.getSeasonYear());

            importedAnimes.add(anime);
        }

        return animeRepository.saveAll(importedAnimes);
    }

    public void clearAllAnimes() {
        animeRepository.deleteAll();
    }
}