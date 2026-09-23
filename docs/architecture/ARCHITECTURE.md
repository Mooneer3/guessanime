# Guessanime — Architecture

## High-Level Architecture

```text
                 ┌───────────────┐
                 │    Browser    │
                 └───────┬───────┘
                         │
                         ▼
                 ┌───────────────┐
                 │   Frontend    │
                 │ Next.js / TS  │
                 └───────┬───────┘
                         │
                      REST API
                         │
                         ▼
                 ┌───────────────┐
                 │    Backend    │
                 │ Spring Boot   │
                 └───────┬───────┘
                         │
             ┌───────────┼───────────┐
             │           │           │
             ▼           ▼           ▼
        PostgreSQL    AniList    Screenshot
                                  Provider
                                      │
                                      ▼
                                  Shikimori