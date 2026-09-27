package com.guessanime.backend.dto;

public class AnimeSummaryResponse {

    private Long id;
    private Long anilistId;
    private String title;
    private String format;
    private Integer releaseYear;
    private Integer popularityRank;

    public AnimeSummaryResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAnilistId() {
        return anilistId;
    }

    public void setAnilistId(Long anilistId) {
        this.anilistId = anilistId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Integer getPopularityRank() {
        return popularityRank;
    }

    public void setPopularityRank(Integer popularityRank) {
        this.popularityRank = popularityRank;
    }
}