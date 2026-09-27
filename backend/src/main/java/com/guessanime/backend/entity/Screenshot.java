package com.guessanime.backend.entity;

import jakarta.persistence.*;

@Entity
public class Screenshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "anime_id", nullable = false)
    private Anime anime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ScreenshotProvider provider;

    @Column(nullable = false, length = 1000)
    private String originalUrl;

    @Column(length = 1000)
    private String previewUrl;

    @Column(nullable = false)
    private String status;

    public Screenshot() {
    }

    public Long getId() {
        return id;
    }

    public Anime getAnime() {
        return anime;
    }

    public void setAnime(Anime anime) {
        this.anime = anime;
    }

    public ScreenshotProvider getProvider() {
        return provider;
    }

    public void setProvider(ScreenshotProvider provider) {
        this.provider = provider;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}