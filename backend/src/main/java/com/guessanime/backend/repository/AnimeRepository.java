package com.guessanime.backend.repository;

import com.guessanime.backend.entity.Anime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnimeRepository extends JpaRepository<Anime, Long> {

    Optional<Anime> findByAnilistId(Long anilistId);

    List<Anime> findByPopularityRankBetween(int minRank, int maxRank);
}