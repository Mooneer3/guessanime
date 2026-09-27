export default function Home() {
  const modes = [
    {
      title: "Screenshot",
      description: "Devine l'anime à partir de screenshots.",
      available: true,
    },
    {
      title: "Personnage",
      description: "Devine le personnage.",
      available: false,
    },
    {
      title: "Opening",
      description: "Reconnais l'opening.",
      available: false,
    },
    {
      title: "Ending",
      description: "Reconnais l'ending.",
      available: false,
    },
    {
      title: "AniDle",
      description: "Trouve l'anime grâce aux indices.",
      available: false,
    },
  ];

  return (
    <main className="min-h-screen bg-zinc-950 text-white">
      <div className="mx-auto flex min-h-screen max-w-6xl flex-col px-6 py-12">
        <header className="mb-16">
          <h1 className="text-5xl font-bold tracking-tight">
            Guessanime
          </h1>

          <p className="mt-4 max-w-2xl text-lg text-zinc-400">
            Le jeu quotidien pour tester tes connaissances en anime.
          </p>
        </header>

        <section>
          <h2 className="mb-6 text-2xl font-semibold">
            Modes de jeu
          </h2>

          <div className="grid gap-5 sm:grid-cols-2 lg:grid-cols-3">
            {modes.map((mode) => (
              <div
                key={mode.title}
                className={`rounded-2xl border p-6 transition ${
                  mode.available
                    ? "border-zinc-700 bg-zinc-900 hover:border-zinc-500"
                    : "border-zinc-800 bg-zinc-900/50 opacity-50"
                }`}
              >
                <h3 className="text-xl font-semibold">
                  {mode.title}
                </h3>

                <p className="mt-3 min-h-12 text-sm text-zinc-400">
                  {mode.description}
                </p>

                {mode.available ? (
                  <a
                    href="/play"
                    className="mt-6 inline-block rounded-lg bg-white px-4 py-2 text-sm font-medium text-black transition hover:bg-zinc-200"
                  >
                    Jouer
                  </a>
                ) : (
                  <span className="mt-6 inline-block text-sm text-zinc-500">
                    Bientôt disponible
                  </span>
                )}
              </div>
            ))}
          </div>
        </section>
      </div>
    </main>
  );
}