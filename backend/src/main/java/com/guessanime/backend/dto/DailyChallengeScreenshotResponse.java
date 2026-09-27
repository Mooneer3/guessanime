package com.guessanime.backend.dto;

public class DailyChallengeScreenshotResponse {

    private Integer hintOrder;
    private Long screenshotId;
    private String originalUrl;
    private String previewUrl;

    public DailyChallengeScreenshotResponse() {
    }

    public Integer getHintOrder() {
        return hintOrder;
    }

    public void setHintOrder(Integer hintOrder) {
        this.hintOrder = hintOrder;
    }

    public Long getScreenshotId() {
        return screenshotId;
    }

    public void setScreenshotId(Long screenshotId) {
        this.screenshotId = screenshotId;
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
}