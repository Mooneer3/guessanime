package com.guessanime.backend.controller;

import com.guessanime.backend.dto.AniListAnime;
import com.guessanime.backend.entity.Anime;
import com.guessanime.backend.service.AniListService;
import com.guessanime.backend.service.AnimeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/anilist")
public class AniListController {

    private final AniListService aniListService;
    private final AnimeService animeService;

    public AniListController(
            AniListService aniListService,
            AnimeService animeService
    ) {
        this.aniListService = aniListService;
        this.animeService = animeService;
    }

    @GetMapping("/anime/{id}")
    public AniListAnime getAnime(@PathVariable Long id) {
        return aniListService.getAnimeById(id);
    }

    @PostMapping("/anime/{id}/import")
    public Anime importAnime(@PathVariable Long id) {
        AniListAnime aniListAnime = aniListService.getAnimeById(id);
        return animeService.importFromAniList(aniListAnime);
    }

    @GetMapping("/popular")
    public List<AniListAnime> getPopularAnimes(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int perPage
    ) {
        return aniListService
                .getPopularAnimes(page, perPage)
                .getData()
                .getPage()
                .getMedia();
    }

    @PostMapping("/pool/import")
    public List<Anime> importPopularPool() {
        List<AniListAnime> animes = aniListService.getTopAnimes(3000);

        return animeService.importPopularPool(animes);
    }
    
}