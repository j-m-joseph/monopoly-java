package com.cm6123.monopoly.app;
import com.cm6123.monopoly.*;
import com.cm6123.monopoly.dice.Dice;
import com.cm6123.monopoly.game.Welcome;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

public final class Application {
    /**
     * Create a logger for the class.
     */
    private static Logger logger = LoggerFactory.getLogger(Application.class);


    private Application() {
    }

    /**
     * main entry point.  If args provided, result is displayed and program exists. Otherwise, user is prompted for
     * input.
     *
     * @param args command line args.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int BoardSize = 0;
        do {
            System.out.print("Enter the size of your board (10 - 50): ");
            try {
                BoardSize = scanner.nextInt();
                scanner.nextLine();
                if (BoardSize < 10 || BoardSize > 50) {
                    System.out.println("Number must be between 10 and 50. Try Again!");
                }
            } catch (InputMismatchException e) {
                System.out.println("That wasn't a number!");
                scanner.nextLine();
                //clear invalid input
            } catch (Exception all) {
                System.out.println("Something went wrong!");
                scanner.nextLine();
            }
        } while (BoardSize < 10 || BoardSize > 50);
        System.out.println("Your board has " + BoardSize + " squares");

        int NumOfPlayers = 0;
        do {
            System.out.print("Enter the number of players in this game (2 - 10): ");
            try {
                NumOfPlayers = scanner.nextInt();
                scanner.nextLine();
                if (NumOfPlayers < 2 || NumOfPlayers > 10) {
                    System.out.println("Number must be between 2 and 10. Try Again!");
                }
            } catch (InputMismatchException e) {
                System.out.println("That wasn't a number!");
                scanner.nextLine();
            } catch (Exception all) {
                System.out.println("Something went wrong!");
                scanner.nextLine();
            }
        } while (NumOfPlayers < 2 || NumOfPlayers > 10);
        System.out.println("There are " + NumOfPlayers + " players in this game!");

        //creates a list of properties
        List<Property> squares = new ArrayList<>(BoardSize);
        //creates the lst that holds BoardSize propert items
        //16 replaced with BoardSize because the size can change

        List<String> propertyList = new ArrayList<>();
        //create Property List
        String[] propertyName = {"Fleet Street", "Bond Street", "Dragon Alley", "Kent Road", "Mayfair", "Park Lane", "Oxford Street", "Regent Street", "Piccadilly", "Strand", "Cambridge Crescent", "Cardiff Road", "Belfast Street", "Edinburgh Drive", "York Way", "Surrey Street", "Bristol Avenue", "Oak Lane", "Maple Avenue", "Willow Road", "River Street", "Meadow Way", "Hilltop Drive", "Sunset Boulevard", "Victory Road", "Fortune Square", "Quantum Street", "Pixel Street", "Code Avenue", "Logic Lane", "Platinum Way", "Sapphire Street", "Emerald Avenue", "Diamond Drive", "Royal Crescent"};
        Random random = new Random();
        for (String name : propertyName) {
            propertyList.add(name);
        }

        List<Integer> usedPriceValues = new ArrayList<>();
        //creates used price values list
        for (int i = 0; i < BoardSize; i++) {
            //creates BoardSize number of squares

            //Controlled pricing randomness
            int purchasePrice = 0;
            int attempts = 0;
            int maxAttempts = BoardSize;
            //tries to depend on BoardSize

            do {
                if (i < BoardSize / 3) {
                    //If the player lands on the first third of the board
                    purchasePrice = 10 + random.nextInt(191);
                    //The price will be between 10 and 200
                } else if (i < 2 * (BoardSize / 3)) {
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

                attempts++;
                //add to the attempt counter by 1
                //The number of times I have tried to find a unique number

            } while (usedPriceValues.contains(purchasePrice) && attempts < maxAttempts);
            //the loop keeps running until it is a price that hasn't been used
            //after propertial amount of tries loop stops
            // it has run through all the possible numbers within the ranges

            usedPriceValues.add(purchasePrice);
            //once a new value is found added to the list of usedPriceValues

            int rentalPrice = purchasePrice / 10;
            //for simplicity rental price is always just exactly 10% of purchase price

            if (random.nextDouble() < 0.10) {
                squares.add(new Property("Road", 0, 0));
            } else if (random.nextDouble() < 0.60) {
                int index = random.nextInt(propertyList.size());
                String name = propertyList.get(index);
                propertyList.remove(index);
                squares.add(new Property(name, purchasePrice, rentalPrice));
            } else {
                squares.add(new Property("Road", 0, 0));
            }
        }
        //Board creation
        Board board = new Board(squares);
        //Board object var board filled with squares that are all properties

        //Stations being added
        int numOfUndergroundStations = BoardSize / 6;
        //calculates the number of stations based on board size
        if (numOfUndergroundStations < 2) {
            numOfUndergroundStations = 2;
        }
        //ensures the board always has at least 2 stations
        for (int j = 0; j < numOfUndergroundStations; j++) {
            //index loop that runs numOfUndergroundStations times from 0
            int stationPosition = 1 + j * (BoardSize / numOfUndergroundStations);
            //j instead of i and calculation for station

            //Make sure station position is valid
            if (stationPosition >= BoardSize) {
                stationPosition = BoardSize - 1;
            }
            // Make sure position 0 is NEVER a station
            if (stationPosition == 0) {
                stationPosition = 1;
            }
            UndergroundStation station = new UndergroundStation(stationPosition);
            //new object
            board.addUndergroundStation(station);
        }

        // DEBUG CODE - TEMPORARY - TO BE REMOVE AFTER TESTING
//        System.out.println("UNDERGROUND STATIONS");
//        for (int i = 0; i < board.getSize(); i++) {
//            if (board.getUndergroundStation(i)) {
//                System.out.println("Station at position: " + i);
//            }
//        }

        //Plyaer creation
        //Array list for PLayers
        List<Player> playerList = new ArrayList<>();
        //not a String but a Player object
        //traditional/counting/index for loop
        for (int i = 0; i < NumOfPlayers; i++) {
            //create each player and loops through one by one
            Player player = new Player();
            //adding a Player object called player
            String name = "Player " + (i + 1);
            //default names for the plauers e.g., Player 1.  Plauer 2 etc...
            playerList.add(player);
        }

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
//        // Get first player
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

            logger.info("Application Started with args:{}", String.join(",", args));
            // Create a new instance of the Welcome class. This is an example of
            // how the UI gives and takes information from the game.

            logger.info("Application closing");
            }
        }
