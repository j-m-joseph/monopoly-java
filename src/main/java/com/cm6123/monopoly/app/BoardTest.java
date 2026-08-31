package com.cm6123.monopoly.app;

import com.cm6123.monopoly.*;
import java.util.*;

public final class BoardTest {

    private BoardTest() {
        // Empty constructor
    }

    public static void main(String[] args) {
        System.out.println("=== MONOPOLY BOARD TESTS ===");
        System.out.println();

        int[][] testConfigs = {
                {10, 2, 5},
                {15, 3, 5},
                {20, 4, 5},
                {30, 6, 5},
                {40, 8, 5},
                {50, 10, 5}
        };

        int passed = 0;
        int failed = 0;

        for (int[] config : testConfigs) {
            int boardSize = config[0];
            int numPlayers = config[1];
            int rounds = config[2];

            boolean result = runTest(boardSize, numPlayers, rounds);

            if (result) {
                passed = passed + 1;
            } else {
                failed = failed + 1;
            }
        }

        System.out.println();
        System.out.println("=== TEST SUMMARY ===");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
    }

    private static boolean runTest(int boardSize, int numPlayers, int rounds) {
        System.out.println("--- Testing Board Size: " + boardSize +
                ", Players: " + numPlayers + ", Rounds: " + rounds + " ---");

        try {
            // Step 1: Create the board with properties
            List<Property> squares = createTestBoard(boardSize);

            // Step 2: Create the Board object
            Board board = new Board(squares);

            // Step 3: Add underground stations
            addStations(board, boardSize);

            // Step 4: Create the players
            List<Player> players = createPlayers(numPlayers);

            // Step 5: Create the Game object
            Game game = new Game(board, players);

            // Step 6: Run the game
            // Create a Scanner that automatically says "yes" to every question
            // This allows the test to run without waiting for user input
            String yesInput = "yes\n".repeat(100);
            Scanner testScanner = new Scanner(yesInput);
            game.playRounds(rounds, testScanner);

            System.out.println("PASSED");
            System.out.println();
            return true;

        } catch (Exception e) {
            System.out.println("FAILED: " + e.getMessage());
            e.printStackTrace();
            System.out.println();
            return false;
        }
    }

    private static List<Property> createTestBoard(int boardSize) {
        List<Property> squares = new ArrayList<>(boardSize);

        Random random = new Random(42);

        String[] propertyNames = {
                "Fleet Street", "Bond Street", "Dragon Alley",
                "Kent Road", "Mayfair", "Park Lane",
                "Oxford Street", "Regent Street", "Piccadilly",
                "Strand", "Cambridge Crescent", "Cardiff Road",
                "Belfast Street", "Edinburgh Drive", "York Way",
                "Surrey Street", "Bristol Avenue", "Oak Lane",
                "Maple Avenue", "Willow Road"
        };

        List<String> availableNames = new ArrayList<>(Arrays.asList(propertyNames));

        for (int i = 0; i < boardSize; i++) {
            int purchasePrice;

            if (i < boardSize / 3) {
                purchasePrice = 10 + random.nextInt(191);
            } else if (i < 2 * (boardSize / 3)) {
                purchasePrice = 200 + random.nextInt(111);
            } else {
                purchasePrice = 310 + random.nextInt(191);
            }

            purchasePrice = (purchasePrice / 10) * 10;
            int rentalPrice = purchasePrice / 10;

            double roll = random.nextDouble();

            if (roll < 0.10) {
                squares.add(new Property("Road", 0, 0));
            } else if (roll < 0.60 && !availableNames.isEmpty()) {
                int index = random.nextInt(availableNames.size());
                String name = availableNames.remove(index);
                squares.add(new Property(name, purchasePrice, rentalPrice));
            } else {
                squares.add(new Property("Road", 0, 0));
            }
        }

        return squares;
    }

    private static void addStations(Board board, int boardSize) {
        int numStations = Math.max(2, boardSize / 6);

        for (int j = 0; j < numStations; j++) {
            int stationPosition = 1 + j * (boardSize / numStations);

            if (stationPosition >= boardSize) {
                stationPosition = boardSize - 1;
            }

            if (stationPosition == 0) {
                stationPosition = 1;
            }

            board.addUndergroundStation(new UndergroundStation(stationPosition));
        }
    }

    private static List<Player> createPlayers(int numPlayers) {
        List<Player> players = new ArrayList<>();

        for (int i = 0; i < numPlayers; i++) {
            players.add(new Player());
        }

        return players;
    }
}