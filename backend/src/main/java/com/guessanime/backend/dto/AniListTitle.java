package com.guessanime.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AniListTitle {

    private String romaji;
    private String english;

    @JsonProperty("native")
    private String nativeTitle;

    public String getRomaji() {
        return romaji;
    }

    public void setRomaji(String romaji) {
        this.romaji = romaji;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getNativeTitle() {
        return nativeTitle;
    }

    public void setNativeTitle(String nativeTitle) {
        this.nativeTitle = nativeTitle;
    }
}