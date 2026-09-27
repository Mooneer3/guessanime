package com.guessanime.backend.dto;

public class AniListAnime {

    private Long id;
    private Long idMal;
    private AniListTitle title;
    private String type;
    private String format;
    private Integer popularity;
    private Integer episodes;
    private Integer seasonYear;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdMal() {
        return idMal;
    }

    public void setIdMal(Long idMal) {
        this.idMal = idMal;
    }

    public AniListTitle getTitle() {
        return title;
    }

    public void setTitle(AniListTitle title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Integer getPopularity() {
        return popularity;
    }

    public void setPopularity(Integer popularity) {
        this.popularity = popularity;
    }

    public Integer getEpisodes() {
        return episodes;
    }

    public void setEpisodes(Integer episodes) {
        this.episodes = episodes;
    }

    public Integer getSeasonYear() {
        return seasonYear;
    }

    public void setSeasonYear(Integer seasonYear) {
        this.seasonYear = seasonYear;
    }
}