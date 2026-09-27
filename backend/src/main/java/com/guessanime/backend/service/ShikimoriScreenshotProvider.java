package com.guessanime.backend.service;

import com.guessanime.backend.dto.ShikimoriScreenshot;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;

@Service
public class ShikimoriScreenshotProvider {

    private static final String SHIKIMORI_BASE_URL =
            "https://shikimori.one";

    private final RestClient restClient;

    public ShikimoriScreenshotProvider(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder
                .baseUrl(SHIKIMORI_BASE_URL)
                .build();
    }

    public List<ShikimoriScreenshot> getScreenshots(Long malId) {

        ShikimoriScreenshot[] response = restClient.get()
                .uri("/api/animes/{id}/screenshots", malId)
                .retrieve()
                .body(ShikimoriScreenshot[].class);

        if (response == null) {
            return List.of();
        }

        return Arrays.asList(response);
    }

    public String buildImageUrl(String path) {

        if (path == null || path.isBlank()) {
            return null;
        }

        if (path.startsWith("http://")
                || path.startsWith("https://")) {
            return path;
        }

        return SHIKIMORI_BASE_URL + path;
    }
}