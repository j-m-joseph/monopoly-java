package com.cm6123.monopoly.app;

public final class Application {
    private Application() {
    }

    /**
     * The entry point of the Monopoly game application.
     * Creates a new game and starts it.
     * @param args command line arguments (unused)
     */
    public static void main(final String[] args) {
        MonopolyGame game = new MonopolyGame();
        game.start(args);
    }
}
