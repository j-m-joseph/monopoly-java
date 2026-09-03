package com.cm6123.monopoly.app;

public final class Application {
    private Application() {
    }

    public static void main(final String[] args) {
        MonopolyGame game = new MonopolyGame();
        game.start(args);
    }
}