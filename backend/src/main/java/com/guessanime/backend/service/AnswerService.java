package com.guessanime.backend.service;

import com.guessanime.backend.entity.Anime;
import org.springframework.stereotype.Service;

import java.text.Normalizer;

@Service
public class AnswerService {

    public boolean isCorrect(
            String answer,
            Anime anime
    ) {
        if (answer == null || answer.isBlank()) {
            return false;
        }

        String normalizedAnswer =
                normalize(answer);

        return normalizedAnswer.equals(
                normalize(anime.getTitle())
        )
                || normalizedAnswer.equals(
                        normalize(anime.getTitleRomaji())
                )
                || normalizedAnswer.equals(
                        normalize(anime.getTitleNative())
                );
    }

    private String normalize(String value) {

        if (value == null) {
            return "";
        }

        String normalized =
                Normalizer.normalize(
                        value,
                        Normalizer.Form.NFD
                );

        return normalized
                .replaceAll("\\p{M}", "")
                .toLowerCase()
                .replaceAll("[^\\p{L}\\p{N}]", "");
    }
}