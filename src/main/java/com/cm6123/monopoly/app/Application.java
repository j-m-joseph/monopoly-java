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

public final class Application {
    /**
     * Create a logger for the class.
     */
    private static Logger logger = LoggerFactory.getLogger(Application.class);


    private Application() {
    }

    /**
     * main entry point. If args provided, result is displayed and program exists. Otherwise, user is prompted for
     * input.
     *
     * @param args command line args.
     */
    public static void main(final String[] args) {
        Scanner scanner = new Scanner(System.in);

        //method calls
        int boardSize = getBoardSize(scanner);
        System.out.println("Your board has " + boardSize + " squares");
        //shows the result to the user and checkstyle reasons means leave print in main
        int numOfPlayers = getnumOfPlayers(scanner);
        System.out.println("There are " + numOfPlayers + " players in this game!");


        List<String> propertyList = new ArrayList<>();
        //create Property List
        String[] propertyName = {"Fleet Street", "Bond Street", "Dragon Alley", "Kent Road", "Mayfair", "Park Lane", "Oxford Street", "Regent Street", "Piccadilly", "Strand", "Cambridge Crescent", "Cardiff Road", "Belfast Street", "Edinburgh Drive", "York Way", "Surrey Street", "Bristol Avenue", "Oak Lane", "Maple Avenue", "Willow Road", "River Street", "Meadow Way", "Hilltop Drive", "Sunset Boulevard", "Victory Road", "Fortune Square", "Quantum Street", "Pixel Street", "Code Avenue", "Logic Lane", "Platinum Way", "Sapphire Street", "Emerald Avenue", "Diamond Drive", "Royal Crescent"};
        Random random = new Random();
        for (String name : propertyName) {
            propertyList.add(name);
        }

        List<Property> squares = getcreateBoard(boardSize, random, propertyList);
        //Board creation
        Board board = new Board(squares);
        //Board object var board filled with squares that are all properties

        List<Player> playerList = createPlayers(numOfPlayers);
        //created playerList in main
        //This returns something and does something
        //If a method returns something, I store it.
        //In this case in the playerList

        addStations(board, boardSize);
        //This returns nothing but does something
        runGame(board, playerList, args);
        //calls runGame method and passes it through the paramaters

        // DEBUG CODE - TEMPORARY - TO BE REMOVE AFTER TESTING
//        System.out.println("UNDERGROUND STATIONS");
//        for (int i = 0; i < board.getSize(); i++) {
//            if (board.getUndergroundStation(i)) {
//                System.out.println("Station at position: " + i);
//            }
//        }

//        //TO TEST THE RENT THEN IT WILL BE COMMENTED OUT
//        Player player1 = playerList.get(0);
//        Player player2 = playerList.get(1);
//        Property testProperty = (Property) board.getSquare(3);
//        testProperty.setPropertyOwner(player1);
//        player2.setPosition(3);
//        board.getSquare(3);
//        // Just to confirm the square exists
//
//        // Then manually trigger the rent check
//        if (board.getSquare(3) instanceof Property && ((Property) board.getSquare(3)).getPropertyOwner() != null && ((Property) board.getSquare(3)).getPropertyOwner() != player2) {
//            // Deduct rent from player2, add to owner
//            player2.setBalance(player2.getBalance() - ((Property) board.getSquare(3)).getPropertyRentalPrice());
//            player1.setBalance(player1.getBalance() + ((Property) board.getSquare(3)).getPropertyRentalPrice());
//            //Add rent to player1
//            System.out.println("Player 2 pays £" + ((Property) board.getSquare(3)).getPropertyRentalPrice() + " rent to Player 1");
//        //Print confirmation
//        }
//            Game testrentgame = new Game(board, playerList);
//        testrentgame.playRounds(1, scanner);
//        //END OF TEST


//        // TO TEST BANKRUPTCY
//        Player player1 = playerList.get(0);
//        Player player2 = playerList.get(1);
//
//        // Give player1 properties with REAL prices
//        // Find properties on the board — these vary but are NOT Road/Station
//        Property testProperty1 = (Property) board.getSquare(0); // Home — check if it's a property
//        Property testProperty2 = (Property) board.getSquare(3); // Try square 3
//        Property testProperty3 = (Property) board.getSquare(5); // Try square 5
//
//        // Make sure they're not null and are actual properties
//        if (testProperty1 != null && testProperty1.getPropertyPurchasePrice() > 0) {
//            testProperty1.setPropertyOwner(player1);
//        }
//        if (testProperty2 != null && testProperty2.getPropertyPurchasePrice() > 0) {
//            testProperty2.setPropertyOwner(player1);
//        }
//        if (testProperty3 != null && testProperty3.getPropertyPurchasePrice() > 0) {
//            testProperty3.setPropertyOwner(player1);
//        }
//
//        // Print what properties player1 owns
//        System.out.println("Player 1 owns:");
//        for (int i = 0; i < board.getSize(); i++) {
//            Object square = board.getSquare(i);
//            if (square instanceof Property && ((Property) square).getPropertyOwner() == player1) {
//                System.out.println("  - " + ((Property) square).getPropertyName() + " (£" + ((Property) square).getPropertyPurchasePrice() + ")");
//            }
//        }
//        // loop through the board to check
//
//        // Force player1 into debt
//        player1.setBalance(-500);
//
//        Game testBankruptcyGame = new Game(board, playerList);
//        testBankruptcyGame.playRounds(1, scanner);
//        // END OF TES


//        //Cycle test — Scenario 1: Player starts on cycle race square
//        List<Property> testSquares = new ArrayList<>(squares);
//        //Creates a copy of the real board's squares list
//        testSquares.set(3, null);
//        //Sets square 3 to empty (null) in the copy only
//        Board testBoard = new Board(testSquares);
//        //Creates a new board using the modified copy
//        Player player1 = playerList.get(0);
//        // First player
//        Game testCycleGame = new Game(testBoard, playerList);
//        // Create game object using the test board
//        player1.setPosition(3);
//        // Force Player 1 to start on square 3
//        System.out.println("Player 1 position before game: " + player1.getPosition());
//        testCycleGame.setCycleRaceSquare(3);
//        // Set square 3 as a cycle race square
//
//        // Run the game — Player 1 is already on square 3
//        testCycleGame.playRounds(2, scanner);
//        // Run 2 rounds to see the restriction in action
//        // END OF SCENARIO 1


//        //Scenario 2: Roll 4+ so player should move 0
//        List<Property> testSquares = new ArrayList<>(squares);
//        //Creates a copy of the real board's squares list
//        testSquares.set(3, null);
//        //Sets square 3 to empty (null) in the copy only
//        Board testBoard = new Board(testSquares);
//        //Creates a new board using the modified copy
//        Player player1 = playerList.get(0);
//        // First player
//        Game testCycleGame = new Game(testBoard, playerList);
//        // Create game object using the test board
//        player1.setPosition(3);
//        // Force Player 1 to start on square 3
//        testCycleGame.setCycleRaceSquare(3);
//        // Set square 3 as a cycle race square
//        testCycleGame.setNextDiceRoll(4);
//        // set total roll to 3
//         testCycleGame.playRounds(2, scanner);
//        // Run 2 rounds to see the restriction in action
//        // END OF SCENARIO


//        //Scenario 3: Roll 3 OR less so player should move normally
//        List<Property> testSquares = new ArrayList<>(squares);
//        //Creates a copy of the real board's squares list
//        testSquares.set(3, null);
//        //Sets square 3 to empty (null) in the copy only
//        Board testBoard = new Board(testSquares);
//        //Creates a new board using the modified copy
//        Player player1 = playerList.get(0);
//        // First player
//        Game testCycleGame = new Game(testBoard, playerList);
//        // Create game object using the test board
//        player1.setPosition(3);
//        // Force Player 1 to start on square 3
//        testCycleGame.setCycleRaceSquare(3);
//        // Set square 3 as a cycle race square
//        testCycleGame.setNextDiceRoll(1);
//        // Force total roll to 1 (3 or less — should move normally)
//        testCycleGame.playRounds(2, scanner);
//        // Run 2 rounds to see the restriction in action
//        // END OF SCENARIO 3


//        //To test Underground station
//        List<Property> stationTestSquares = new ArrayList<>(squares);
//        Board stationTestBoard = new Board(stationTestSquares);
//
//        //Add the stations to the test board
//        stationTestBoard.addUndergroundStation(new UndergroundStation(2));
//        stationTestBoard.addUndergroundStation(new UndergroundStation(7));
//        stationTestBoard.addUndergroundStation(new UndergroundStation(9));
//
//        //get Players for testing
//        Player testPlayer1 = playerList.get(0);
//        Player testPlayer2 = playerList.get(1);
//
//        Game stationTestGame = new Game(stationTestBoard, playerList);

//        //Scenario 1: Teleporting between 2 stations
//        System.out.println("Basic teleport between 2 station");
//        testPlayer1.setPosition(2);
//        System.out.println("Is square 2 a station?: " + stationTestBoard.getUndergroundStation(2));
//        //this should print true
//        System.out.println("Player 1 position before teleport: " + testPlayer1.getPosition());
//        //position before teleport
//        System.out.println("Player 1 balance before teleport: " + testPlayer1.getBalance());
//        //balance before teleport
//        stationTestGame.playRounds(1, scanner);
//        System.out.println("Player 1 position after teleport: " + testPlayer1.getPosition());
//        //position after teleport
//        System.out.println("Player 1 balance after teleport: " + testPlayer1.getBalance());
//        //balance after teleport
//        //END OF TEST

        //Scenario 2: Home bolus moving forward

//        Game homeGame = new Game(board, playerList);
//        //Create game for this scenario
//
//        // Clear existing stations (only for testing)
//        board.clearUndergroundStations();
//
//        System.out.println("SCENARIO 2: Teleport 9 → 2 (Should give Home bonus)");
//
//        // Make sure it has stations at 9 and 2
//        board.addUndergroundStation(new UndergroundStation(9));
//        board.addUndergroundStation(new UndergroundStation(2));
//
//        // Get first player
//        Player testPlayer = playerList.get(0);
//
//        // Set testPlayer at position 9
//        testPlayer.setPosition(9);
//        System.out.println("Board size: " + BoardSize);
//        System.out.println("Player position before: " + testPlayer.getPosition());
//        System.out.println("Player balance before: " + testPlayer.getBalance());
//
//        // Run 1 round
//        homeGame.playRounds(1, scanner);
//
//        System.out.println("Player position after: " + testPlayer.getPosition());
//        System.out.println("Player balance after: " + testPlayer.getBalance());


//        //Scenario 3: Home bonus moving backward - NO bonus
//        System.out.println("SCENARIO 3: Teleport 2 → 9 (Should NOT give Home bonus)");
//
//        // Clear existing stations (only for testing)
//        board.clearUndergroundStations();
//
//        // Make sure it has stations at 2 and 9
//        board.addUndergroundStation(new UndergroundStation(2));
//        board.addUndergroundStation(new UndergroundStation(9));
//
//
//        //Create game after stations are set
//        Game noReward = new Game(board, playerList);
//        // Get first player
//         testBackwardsPlayer = playerList.get(0);
//
//        // Set testPlayer at position 2
//        testBackwardsPlayer.setPosition(2);
//        System.out.println("Board size: " + BoardSize);
//        System.out.println("Player position before: " + testBackwardsPlayer.getPosition());
//        System.out.println("Player balance before: " + testBackwardsPlayer.getBalance());
//
//        // Run 1 round
//        noReward.playRounds(1, scanner);
//
//        System.out.println("Player position after: " + testBackwardsPlayer.getPosition());
//        System.out.println("Player balance after: " + testBackwardsPlayer.getBalance());
//        //END OF TEST


//        //Scenario 4: Multiple stations - random selection
//        System.out.println("SCENARIO 4: 3 stations - random teleport");
//
//        // Clear existing stations
//        board.clearUndergroundStations();
//
//        // Add 3 stations at different positions
//        board.addUndergroundStation(new UndergroundStation(2));
//        board.addUndergroundStation(new UndergroundStation(7));
//        board.addUndergroundStation(new UndergroundStation(9));
//
//        // Create game after stations are set
//        Game stationGame = new Game(board, playerList);
//
//        // Get the first player
//        Player stationPlayer = playerList.get(0);
//
//        // Set player at position 2
//        stationPlayer.setPosition(2);
//        System.out.println("Board size: " + BoardSize);
//        System.out.println("Player position before: " + stationPlayer.getPosition());
//        System.out.println("Player balance before: " + stationPlayer.getBalance());
//
//        // Run 1 round
//        stationGame.playRounds(1, scanner);
//
//        System.out.println("Player position after: " + stationPlayer.getPosition());
//        System.out.println("Player balance after: " + stationPlayer.getBalance());
//        //END OF TEST

//        //Scenario 5: Multiple stations with Home bonus
//        System.out.println("SCENARIO 5: 3 stations + Home bonus test");
//
//        // Clear existing stations
//        board.clearUndergroundStations();
//
//        // Add 3 stations
//        board.addUndergroundStation(new UndergroundStation(9));
//        // Starting position
//        board.addUndergroundStation(new UndergroundStation(2));
//        // Passes Home (9→2)
//        board.addUndergroundStation(new UndergroundStation(7));
//        // Does NOT pass Home (9→7)
//
//        // Create game after stations are set
//        Game combinedGame = new Game(board, playerList);
//
//        // Get first player
//        Player combinedPlayer = playerList.get(0);
//
//        // Set player at position 9
//        combinedPlayer.setPosition(9);
//        System.out.println("Board size: " + BoardSize);
//        System.out.println("Player position before: " + combinedPlayer.getPosition());
//        System.out.println("Player balance before: " + combinedPlayer.getBalance());
//
//        // Run 1 round
//        combinedGame.playRounds(1, scanner);
//
//        System.out.println("Player position after: " + combinedPlayer.getPosition());
//        System.out.println("Player balance after: " + combinedPlayer.getBalance());
//        //End of test

//            for (Player player : playerList) {
//
//                System.out.println("Before move: Player " + (playerList.indexOf(player) + 1) + " is on square " + player.getPosition());
//                //to see the players stuff before the move
//
//                System.out.println(player.getPosition());
//                //they all start from 0/Home
//                System.out.println(player.getBalance());
//                //They all start with 1000 but also rememeber
//                //will change later when buying/paying rent
//
//                System.out.println("After move: Player " + (playerList.indexOf(player) + 1) + " is now on square " + player.getPosition());
//                //after the player moves their stats
    }

    /**
     * Prompts the user to enter the size of the board.
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
}
