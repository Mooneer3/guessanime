package com.guessanime.backend.repository;

import com.guessanime.backend.entity.Anime;
import com.guessanime.backend.entity.Screenshot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScreenshotRepository extends JpaRepository<Screenshot, Long> {

    List<Screenshot> findByAnime(Anime anime);

    long countByAnime(Anime anime);
}