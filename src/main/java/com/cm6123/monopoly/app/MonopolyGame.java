package com.cm6123.monopoly.app;
import com.cm6123.monopoly.Board;
import com.cm6123.monopoly.Game;
import com.cm6123.monopoly.Player;
import com.cm6123.monopoly.Property;
import com.cm6123.monopoly.UndergroundStation;
import com.cm6123.monopoly.game.Welcome;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class MonopolyGame {
private static Logger logger = LoggerFactory.getLogger(MonopolyGame.class);


 /** Prompts the user to enter the size of the board.
  * Validates the input given it falls in the range of 10 and 50.
  * @param scanner the Scanner used to read the user input
 * @return the valid board size
 */
private static int getBoardSize(final Scanner scanner) {
    int boardSize = 0;
    do {
        System.out.print("Enter the size of your board (10 - 50): ");
        try {
            boardSize = scanner.nextInt();
            scanner.nextLine();
            if (boardSize < 10 || boardSize > 50) {
                System.out.println("Number must be between 10 and 50. Try Again!");
            }
        } catch (InputMismatchException e) {
            System.out.println("That wasn't a number!");
            scanner.nextLine();
        } catch (Exception all) {
            System.out.println("Something went wrong!");
            scanner.nextLine();
        }
    } while (boardSize < 10 || boardSize > 50);
    return boardSize;
}

/**
 * Prompts the user to enter a value for the number of players in the game.
 * Validates the input given in falls in the range of 2 and 10.
 * @param scanner is used to read the user's input
 * @return the valid integer of players
 */
public static int getnumOfPlayers(final Scanner scanner) {
    int numOfPlayers = 0;
    do {
        System.out.print("Enter the number of players in this game (2 - 10): ");
        try {
            numOfPlayers = scanner.nextInt();
            scanner.nextLine();
            if (numOfPlayers < 2 || numOfPlayers > 10) {
                System.out.println("Number must be between 2 and 10. Try Again!");
            }
        } catch (InputMismatchException e) {
            System.out.println("That wasn't a number!");
            scanner.nextLine();
        } catch (Exception all) {
            System.out.println("Something went wrong!");
            scanner.nextLine();
        }
    } while (numOfPlayers < 2 || numOfPlayers > 10);
    return numOfPlayers;
}

/**
 * Creates the board with properties based on the board size.
 * Properties are randomly generated with prices based on their position.
 * @param boardSize the number of squares on the board
 * @param random the Random pbject used for generating prices
 * @param propertyList the list of property names to assign
 * @return the property object list to the board
 */
public static List<Property> getcreateBoard(final int boardSize, final Random random, final List<String> propertyList) {
    List<Property> squares = new ArrayList<>(boardSize);
    List<Integer> usedPriceValues = new ArrayList<>();

    //creates used price values list
    for (int i = 0; i < boardSize; i++) {
        //creates BoardSize number of squares
        Property square = createSquare(i, boardSize, random, propertyList, usedPriceValues);
        //Property var to store square
        //calss createSquare method that creates one square
        squares.add(square);
        //adds square to the list
    }
    return squares;
}

/**
 * Generates a purchase price for a property based on its position on the board.
 * Prices are higher towards the end of the board like real monopoly and prices are rounded to the nearest 10.
 * @param i the position of the square on the board
 * @param boardSize the total number of squares on the board
 * @param random the Random object used the generate the price
 * @return te generated purchase price
 */
private static int generatePurchasePrice(final int i, final int boardSize, final Random random) {
    //helper method so priivate
    int purchasePrice = 0;
    if (i < boardSize / 3) {
        //If the player lands on the first third of the board
        purchasePrice = 10 + random.nextInt(191);
        //The price will be between 10 and 200
    } else if (i < 2 * (boardSize / 3)) {
        // if the player lands on the second third of the board
        purchasePrice = 200 + random.nextInt(111);
        //The price will be between 200 and 310

    } else {
        //Last third of the board do end with else
        purchasePrice = 310 + random.nextInt(191);
        //The price is between 310 and 500
    }
    purchasePrice = (purchasePrice / 10) * 10;
    //rounded to the nearest 10th
    //Controlled pricing randomness
    return purchasePrice;
}

/**
 * Creaes a single square for the board and decides if it is a road or property.
 * If it's a property, it assigns a name from the propertyList.
 * @param i the position of the square on the board
 * @param boardSize the total number of squares on the board
 * @param random the Random object used to generate prices
 * @param propertyList the list of property names to assign
 * @param usedPriceValues the lis of prices already used to avoid unessarry duplication
 * @return the property Object representing the square
 */
private static Property createSquare(final int i, final int boardSize, final Random random, final List<String> propertyList, final List<Integer> usedPriceValues) {
    int purchasePrice = generatePurchasePrice(i, boardSize, random);
    //calls helper method to get a price based on position
    int rentalPrice = purchasePrice / 10;

    if (random.nextDouble() < 0.10) {
        return new Property("Road", 0, 0);
    } else if (random.nextDouble() < 0.60) {
        int index = random.nextInt(propertyList.size());
        String name = propertyList.get(index);
        propertyList.remove(index);
        return new Property(name, purchasePrice, rentalPrice);
    } else {
        return new Property("Road", 0, 0);
    }
}

/**
 * Creates a list of players based on the given number.
 * @param numOfPlayers the number of players to create
 * @return the list of player objects
 */
private static List<Player> createPlayers(final int numOfPlayers) {
    List<Player> playerList = new ArrayList<>();
    for (int i = 0; i < numOfPlayers; i++) {
        Player player = new Player();
        playerList.add(player);
    }
    return playerList;
}

/**
 * Creates a list of stations based on the size of the board.
 * Ensures the number of stations abides by the minimum requirements of the pro forma and is reasonable.
 * @param board the board abjuect with all squares
 * @param boardSize the size of the board
 */
private static void addStations(final Board board, final int boardSize) {
    int numOfUndergroundStations = boardSize / 6;
    if (numOfUndergroundStations < 2) {
        numOfUndergroundStations = 2;
    }
    for (int j = 0; j < numOfUndergroundStations; j++) {
        int stationPosition = 1 + j * (boardSize / numOfUndergroundStations);
        if (stationPosition >= boardSize) {
            stationPosition = boardSize - 1;
        }
        if (stationPosition == 0) {
            stationPosition = 1;
        }
        UndergroundStation station = new UndergroundStation(stationPosition);
        board.addUndergroundStation(station);
    }
}

/**
 * The game runs using the given board and players.
 * Displays the welcome message, plays rounds and declares the winner.
 * @param board the board object with all squares
 * @param playerList the list of players in the game
 * @param args the command line arguments passed to the application
 */
public static void runGame(final Board board, final List<Player> playerList, final String[] args) {

    //movement logic
    Game game = new Game(board, playerList);
    //game needs the board and players to start
    //I need playerList because I need all the people playing

    Welcome welcome = new Welcome();
    // Print the welcome message to the console. The content comes from the game package.
    System.out.println(welcome.welcomeMessage());
    //puting this before the for loop to stop it repeating

    // Find and display the winner
    Player winner = game.getWinner();
    int winnerAssets = game.calculateAssets(winner);
    System.out.println("The winner is " + winner + " with total assets of £" + winnerAssets + "!");

    logger.info("Application Started with args:{}", String.join(",", args));
    logger.info("Application closing");
}
    public void start(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int boardSize = getBoardSize(scanner);
        System.out.println("Your board has " + boardSize + " squares");

        int numOfPlayers = getnumOfPlayers(scanner);
        System.out.println("There are " + numOfPlayers + " players in this game!");

        List<String> propertyList = new ArrayList<>();
        String[] propertyName = {"Fleet Street", "Bond Street", "Dragon Alley", "Kent Road", "Mayfair", "Park Lane", "Oxford Street", "Regent Street", "Piccadilly", "Strand", "Cambridge Crescent", "Cardiff Road", "Belfast Street", "Edinburgh Drive", "York Way", "Surrey Street", "Bristol Avenue", "Oak Lane", "Maple Avenue", "Willow Road", "River Street", "Meadow Way", "Hilltop Drive", "Sunset Boulevard", "Victory Road", "Fortune Square", "Quantum Street", "Pixel Street", "Code Avenue", "Logic Lane", "Platinum Way", "Sapphire Street", "Emerald Avenue", "Diamond Drive", "Royal Crescent"};
        Random random = new Random();
        for (String name : propertyName) {
            propertyList.add(name);
        }

        List<Property> squares = getcreateBoard(boardSize, random, propertyList);
        Board board = new Board(squares);
        List<Player> playerList = createPlayers(numOfPlayers);
        addStations(board, boardSize);
        runGame(board, playerList, args);
    }
}
