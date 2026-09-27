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

Main Components
Frontend

Next.js application responsible for:

User interface
Game screens
Navigation
Answer autocomplete
Player interactions
Backend

Spring Boot application responsible for:

Game logic
Score calculation
Answer validation
Daily game generation
User management
External API integrations
Database

PostgreSQL stores:

Anime data
Screenshots
Daily games
Challenges
Users
Scores
Game history
External Providers
AniList

Source for anime metadata.

Shikimori

Screenshot provider for Screenshot mode.


---

## 4. Vérifie Git

Dans le terminal :

```bash
git status