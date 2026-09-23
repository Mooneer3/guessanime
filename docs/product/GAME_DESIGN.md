# Guessanime — Game Design Document

## 1. Overview

Guessanime is a daily anime guessing game where players can test
their knowledge through different game modes.

## 2. Game Modes

- Guess the Screenshot
- Guess the Character
- Guess the Opening
- Guess the Ending
- AniDle

## 3. Daily Game

Each day contains three rounds for each game mode:

- Easy
- Medium
- Hard

The daily game contains 15 challenges in total.

Players can freely switch between game modes and rounds.

## 4. Daily Anime Pool

The candidate pool consists of the 3,000 most popular eligible anime
from AniList.

Eligible anime:

- TV
- Movie
- Non-adult content

Difficulty is determined by popularity rank:

- 1–1000: Easy
- 1001–2000: Medium
- 2001–3000: Hard

The same anime cannot appear twice within the same game mode on the
same day, but can appear in different game modes.

Daily challenges are randomly selected.

## 5. Scoring

The maximum daily score is 100,000 points.

Each game mode is worth 20,000 points:

- Easy: 3,000 points
- Medium: 7,000 points
- Hard: 10,000 points

## 6. Screenshot Mode

Each round contains:

1. Initial screenshot
2. Hint 1: second screenshot
3. Hint 2: third screenshot
4. Hint 3: censored anime title

The third hint displays the first letter of the title and
the number of letters to complete.

Players can request hints manually.

A wrong answer automatically reveals the next available hint.

If no hints remain and the player answers incorrectly,
the round ends with 0 points.

## 7. Answers

The answer field provides autocomplete suggestions.

Alternative anime titles are accepted.

Anime identification is based on the selected anime ID rather
than raw text comparison.

## 8. Daily Pool

The daily pool is generated in advance and validated before
publication.

Once published, the daily pool is immutable.

All players receive the same challenges for a given day.