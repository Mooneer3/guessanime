"use client";

import { useEffect, useState } from "react";

const API_URL = "http://localhost:8080";
const GAME_DATE = "2026-09-28";
const SESSION_STORAGE_KEY = `guessanime-session-${GAME_DATE}`;

type Screenshot = {
  hintOrder: number;
  previewUrl: string;
  originalUrl: string;
};

type GameChallenge = {
  challengeId: number;
  mode: string;
  difficulty: string;
  challengeOrder: number;
  currentHint: number;
  maxHints: number;
  completed: boolean;
  score: number;
  screenshot: Screenshot | null;
  titleHint: string | null;
};

type GameSession = {
  sessionToken: string;
  gameDate: string;
  challenges: GameChallenge[];
};

export default function PlayPage() {
  const [session, setSession] = useState<GameSession | null>(null);
  const [answer, setAnswer] = useState("");
  const [loading, setLoading] = useState(true);
  const [submitting, setSubmitting] = useState(false);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    loadSession();
  }, []);

  async function loadSession() {
    try {
      setLoading(true);
      setError(null);

      const savedToken =
        window.localStorage.getItem(
          SESSION_STORAGE_KEY
        );

      if (savedToken) {
        const response = await fetch(
          `${API_URL}/api/game-sessions/${savedToken}`
        );

        if (response.ok) {
          const data: GameSession =
            await response.json();

          setSession(data);
          setLoading(false);
          return;
        }

        window.localStorage.removeItem(
          SESSION_STORAGE_KEY
        );
      }

      await createSession();

    } catch (err) {
      setError(
        err instanceof Error
          ? err.message
          : "Une erreur est survenue."
      );

      setLoading(false);
    }
  }

  async function createSession() {
    const response = await fetch(
      `${API_URL}/api/game-sessions/${GAME_DATE}`,
      {
        method: "POST",
      }
    );

    if (!response.ok) {
      throw new Error(
        `Impossible de créer la session (${response.status})`
      );
    }

    const data: GameSession =
      await response.json();

    window.localStorage.setItem(
      SESSION_STORAGE_KEY,
      data.sessionToken
    );

    setSession(data);
  }

  async function submitAnswer() {
    if (!session || !currentChallenge) {
      return;
    }

    if (!answer.trim() || submitting) {
      return;
    }

    try {
      setSubmitting(true);
      setError(null);

      const response = await fetch(
        `${API_URL}/api/game-sessions/${session.sessionToken}/challenges/${currentChallenge.challengeId}/answer`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            answer,
          }),
        }
      );

      if (!response.ok) {
        throw new Error(
          `Erreur lors de l'envoi de la réponse (${response.status})`
        );
      }

      const updatedChallenge: GameChallenge =
        await response.json();

      setSession((currentSession) => {
        if (!currentSession) {
          return currentSession;
        }

        return {
          ...currentSession,
          challenges:
            currentSession.challenges.map(
              (challenge) =>
                challenge.challengeId ===
                updatedChallenge.challengeId
                  ? updatedChallenge
                  : challenge
            ),
        };
      });

      setAnswer("");

    } catch (err) {
      setError(
        err instanceof Error
          ? err.message
          : "Une erreur est survenue."
      );
    } finally {
      setSubmitting(false);
    }
  }

  if (loading) {
    return (
      <main className="flex min-h-screen items-center justify-center bg-zinc-950 text-white">
        <p className="text-zinc-400">
          Chargement du jeu...
        </p>
      </main>
    );
  }

  if (error && !session) {
    return (
      <main className="flex min-h-screen items-center justify-center bg-zinc-950 px-6 text-white">
        <div className="text-center">
          <h1 className="text-2xl font-bold">
            Impossible de charger le jeu
          </h1>

          <p className="mt-3 text-zinc-400">
            {error}
          </p>

          <button
            onClick={loadSession}
            className="mt-6 rounded-lg bg-white px-5 py-3 font-medium text-black hover:bg-zinc-200"
          >
            Réessayer
          </button>
        </div>
      </main>
    );
  }

  if (!session) {
    return null;
  }

  const currentChallenge =
    session.challenges.find(
      (challenge) =>
        challenge.mode === "SCREENSHOT" &&
        !challenge.completed
    ) ?? session.challenges[0];

  const totalScore =
    session.challenges.reduce(
      (total, challenge) =>
        total + challenge.score,
      0
    );

  return (
    <main className="min-h-screen bg-zinc-950 text-white">
      <div className="mx-auto max-w-6xl px-6 py-8">

        <header className="flex items-center justify-between border-b border-zinc-800 pb-6">
          <a
            href="/"
            className="text-xl font-bold"
          >
            Guessanime
          </a>

          <div className="text-right">
            <p className="text-sm text-zinc-500">
              Score
            </p>

            <p className="text-2xl font-bold">
              {totalScore.toLocaleString("fr-FR")}
            </p>
          </div>
        </header>

        <div className="mt-8 grid gap-8 lg:grid-cols-[1fr_280px]">

          <section>
            <div className="mb-6 flex items-center justify-between">
              <div>
                <p className="text-sm uppercase tracking-wider text-zinc-500">
                  Screenshot
                </p>

                <h1 className="mt-1 text-3xl font-bold">
                  Devine l'anime
                </h1>
              </div>

              <div className="text-right">
                <p className="text-sm text-zinc-500">
                  Difficulté
                </p>

                <p className="font-semibold">
                  {currentChallenge.difficulty}
                </p>
              </div>
            </div>

            <div className="overflow-hidden rounded-2xl border border-zinc-800 bg-zinc-900">

              <div className="flex aspect-video items-center justify-center bg-black">
                {currentChallenge.screenshot ? (
                  <img
                    src={
                      currentChallenge.screenshot.originalUrl
                    }
                    alt="Screenshot de l'anime"
                    className="h-full w-full object-contain"
                  />
                ) : currentChallenge.titleHint ? (
                  <div className="text-center">
                    <p className="text-sm text-zinc-500">
                      Dernier indice
                    </p>

                    <p className="mt-3 text-4xl font-bold">
                      {currentChallenge.titleHint}
                    </p>
                  </div>
                ) : (
                  <p className="text-zinc-500">
                    Aucun indice disponible
                  </p>
                )}
              </div>

              <div className="p-6">

                <div className="mb-5 flex items-center justify-between">
                  <div>
                    <p className="text-sm text-zinc-500">
                      Indice
                    </p>

                    <p className="font-semibold">
                      {currentChallenge.currentHint} /{" "}
                      {currentChallenge.maxHints}
                    </p>
                  </div>

                  <div className="text-right">
                    <p className="text-sm text-zinc-500">
                      Points
                    </p>

                    <p className="font-semibold">
                      {currentChallenge.completed
                        ? currentChallenge.score.toLocaleString(
                            "fr-FR"
                          )
                        : "?"}
                    </p>
                  </div>
                </div>

                {!currentChallenge.completed ? (
                  <div className="flex gap-3">
                    <input
                      type="text"
                      value={answer}
                      onChange={(event) =>
                        setAnswer(event.target.value)
                      }
                      onKeyDown={(event) => {
                        if (event.key === "Enter") {
                          submitAnswer();
                        }
                      }}
                      placeholder="Quel est cet anime ?"
                      className="flex-1 rounded-lg border border-zinc-700 bg-zinc-950 px-4 py-3 text-white outline-none placeholder:text-zinc-600 focus:border-zinc-500"
                    />

                    <button
                      onClick={submitAnswer}
                      disabled={
                        submitting ||
                        !answer.trim()
                      }
                      className="rounded-lg bg-white px-6 py-3 font-medium text-black transition hover:bg-zinc-200 disabled:cursor-not-allowed disabled:opacity-40"
                    >
                      {submitting
                        ? "..."
                        : "Valider"}
                    </button>
                  </div>
                ) : (
                  <div className="rounded-lg bg-zinc-950 p-4">
                    <p className="font-semibold">
                      Challenge terminé
                    </p>

                    <p className="mt-1 text-sm text-zinc-500">
                      {currentChallenge.score > 0
                        ? `Tu as gagné ${currentChallenge.score.toLocaleString(
                            "fr-FR"
                          )} points.`
                        : "Aucun point gagné."}
                    </p>
                  </div>
                )}

                {error && (
                  <p className="mt-4 text-sm text-red-400">
                    {error}
                  </p>
                )}
              </div>
            </div>
          </section>

          <aside>
            <div className="rounded-2xl border border-zinc-800 bg-zinc-900 p-5">

              <h2 className="font-semibold">
                Défis du jour
              </h2>

              <p className="mt-1 text-sm text-zinc-500">
                {session.gameDate}
              </p>

              <div className="mt-5 space-y-2">
                {session.challenges.map(
                  (challenge) => (
                    <div
                      key={challenge.challengeId}
                      className={`rounded-lg border p-3 ${
                        challenge.challengeId ===
                        currentChallenge.challengeId
                          ? "border-zinc-500 bg-zinc-800"
                          : "border-zinc-800 bg-zinc-950"
                      }`}
                    >
                      <div className="flex items-center justify-between">
                        <div>
                          <p className="text-sm font-medium">
                            {challenge.mode}
                          </p>

                          <p className="text-xs text-zinc-500">
                            {challenge.difficulty}
                          </p>
                        </div>

                        <div>
                          {challenge.completed ? (
                            <span className="text-sm font-semibold">
                              {challenge.score}
                            </span>
                          ) : (
                            <span className="text-xs text-zinc-600">
                              —
                            </span>
                          )}
                        </div>
                      </div>
                    </div>
                  )
                )}
              </div>
            </div>
          </aside>

        </div>
      </div>
    </main>
  );
}