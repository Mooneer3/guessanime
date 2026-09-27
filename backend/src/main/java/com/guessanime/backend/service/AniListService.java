package com.guessanime.backend.service;

import com.guessanime.backend.dto.AniListAnime;
import com.guessanime.backend.dto.AniListPageResponse;
import com.guessanime.backend.dto.AniListResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AniListService {

    private final RestClient restClient;

    public AniListService(
            RestClient.Builder restClientBuilder,
            @Value("${anilist.api.url}") String apiUrl
    ) {
        this.restClient = restClientBuilder
                .baseUrl(apiUrl)
                .build();
    }

    public AniListAnime getAnimeById(Long anilistId) {

        String query = """
                query ($id: Int) {
                    Media(id: $id, type: ANIME) {
                        id
                        idMal
                        title {
                            romaji
                            english
                            native
                        }
                        type
                        format
                        popularity
                        episodes
                        seasonYear
                    }
                }
                """;

        Map<String, Object> request = Map.of(
                "query", query,
                "variables", Map.of("id", anilistId)
        );

        AniListResponse response = restClient.post()
                .body(request)
                .retrieve()
                .body(AniListResponse.class);

        return response.getData().getMedia();
    }

    public AniListPageResponse getPopularAnimes(int page, int perPage) {

        String query = """
                query ($page: Int, $perPage: Int) {
                    Page(page: $page, perPage: $perPage) {
                        pageInfo {
                            currentPage
                            hasNextPage
                            perPage
                        }
                        media(
                            type: ANIME
                            format_in: [TV, MOVIE]
                            isAdult: false
                            sort: POPULARITY_DESC
                        ) {
                            id
                            idMal
                            title {
                                romaji
                                english
                                native
                            }
                            type
                            format
                            popularity
                            episodes
                            seasonYear
                        }
                    }
                }
                """;

        Map<String, Object> request = Map.of(
                "query", query,
                "variables", Map.of(
                        "page", page,
                        "perPage", perPage
                )
        );

        return restClient.post()
                .body(request)
                .retrieve()
                .body(AniListPageResponse.class);
    }

    public List<AniListAnime> getTopAnimes(int limit) {

        int perPage = 50;
        int totalPages = (int) Math.ceil((double) limit / perPage);

        List<AniListAnime> animes = new ArrayList<>();

        for (int page = 1; page <= totalPages; page++) {

            AniListPageResponse response = getPopularAnimes(page, perPage);

            List<AniListAnime> pageAnimes = response
                    .getData()
                    .getPage()
                    .getMedia();

            animes.addAll(pageAnimes);

            System.out.println(
                    "AniList : page " + page + "/" + totalPages
                            + " - " + animes.size() + "/" + limit
                            + " anime récupérés"
            );

            if (!response.getData().getPage().getPageInfo().isHasNextPage()) {
                break;
            }

            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(
                        "Import AniList interrompu",
                        e
                );
            }
        }

        if (animes.size() > limit) {
            return animes.subList(0, limit);
        }

        return animes;
    }
}