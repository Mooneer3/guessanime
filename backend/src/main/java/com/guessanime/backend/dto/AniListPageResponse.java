package com.guessanime.backend.dto;

public class AniListPageResponse {

    private AniListPageData data;

    public AniListPageData getData() {
        return data;
    }

    public void setData(AniListPageData data) {
        this.data = data;
    }

    public static class AniListPageData {

        private AniListPage Page;

        public AniListPage getPage() {
            return Page;
        }

        public void setPage(AniListPage page) {
            Page = page;
        }
    }

    public static class AniListPage {

        private AniListPageInfo pageInfo;
        private java.util.List<AniListAnime> media;

        public AniListPageInfo getPageInfo() {
            return pageInfo;
        }

        public void setPageInfo(AniListPageInfo pageInfo) {
            this.pageInfo = pageInfo;
        }

        public java.util.List<AniListAnime> getMedia() {
            return media;
        }

        public void setMedia(java.util.List<AniListAnime> media) {
            this.media = media;
        }
    }

    public static class AniListPageInfo {

        private boolean hasNextPage;
        private int currentPage;
        private int perPage;

        public boolean isHasNextPage() {
            return hasNextPage;
        }

        public void setHasNextPage(boolean hasNextPage) {
            this.hasNextPage = hasNextPage;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public int getPerPage() {
            return perPage;
        }

        public void setPerPage(int perPage) {
            this.perPage = perPage;
        }
    }
}