package com.guessanime.backend.controller;

import com.guessanime.backend.entity.Anime;
import com.guessanime.backend.entity.Screenshot;
import com.guessanime.backend.service.AnimeService;
import com.guessanime.backend.service.ScreenshotService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/animes")
public class AnimeController {

    private final AnimeService animeService;
    private final ScreenshotService screenshotService;

    public AnimeController(
            AnimeService animeService,
            ScreenshotService screenshotService
    ) {
        this.animeService = animeService;
        this.screenshotService = screenshotService;
    }

    @GetMapping
    public List<Anime> getAllAnimes() {
        return animeService.getAllAnimes();
    }

    @PostMapping
    public Anime createAnime(@RequestBody Anime anime) {
        return animeService.createAnime(anime);
    }

    @PostMapping("/{id}/screenshots/import")
    public List<Screenshot> importScreenshots(@PathVariable Long id) {

        Anime anime = animeService.getAnimeById(id);

        return screenshotService.importScreenshots(anime);
    }

    @PostMapping("/screenshots/import-pool")
    public String importPoolScreenshots() {

        screenshotService.importPoolScreenshots();

        return "Import screenshots terminé.";
    }
}