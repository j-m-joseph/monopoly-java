package com.cm6123.monopoly;

//A list is more flexible
import java.util.List;
import java.util.Scanner;
//Dice import
import com.cm6123.monopoly.dice.Dice;

public final class Game {

    /**
     * What board players are using.
     * Board has a size that game uses to wrap positions when players move.
     */
    private final Board board;

    /**
     * Which players are playing in what order.
     * loops through them for each round.
     */
    private final List<Player> players;

    /**
     * The dice used for rolling one for now stage1.
     */
    private final Dice dice;

    /**
     * Array of size 16 to set certain squares as CycleRaceSquares.
     */
    private final boolean[] cycleRacePositions;
    // each element is TRUE/FALSE
    //no longer 16 now it the array size matches the boardSize

    /**
     * Marks the square as a CycleRace square.
     *
     * @param position the index of the square on the board
     *                 if a 4, 5 or 6 is rolled the player doesn't move
     */
    public void setCycleRaceSquare(final int position) {
        //cycle race square restriction check
        if (position < 0 || position >= board.getSize() || position == 0 || board.getSquare(position) instanceof Property || board.getSquare(position) instanceof UndergroundStation) {
            //use position it is the paramater in the method
            //position is the Home square
            //position is a property
            return;
            //exit the method and do not set the square
        }
        cycleRacePositions[position] = true;
    }

    /**
     * Checks if a given square is a CycleRaceSquare.
     *
     * @param position the index of the square on the board
     * @return true if the square is a cycleRaceSquare and false if not
     */
    public boolean getCycleRacePosition(final int position) {
        return cycleRacePositions[position];
    }

    /**
     * Contrustor line.
     *
     * @param gameboard  the game board
     * @param playerList the list of players
     */
    public Game(final Board gameboard, final List<Player> playerList) {
        this.board = gameboard;
        this.players = playerList;
        this.dice = new Dice(6);
        // 6 faces for a standard die
        this.cycleRacePositions = new boolean[board.getSize()];
        //any square can be a cycleRacePosition square
    }

    /**
     * Forces the next dice roll to be a specific value to test.
     *
     * @param value the value the dice shout return 1 through 6
     */
    public void setNextDiceRoll(final int value) {
        dice.setNextRoll(value);
    }

    /**
     * Forces the next dice roll to a specific value for testing.
     * This method delegates to the Dice object's forceNextRoll method.
     *
     * @param roll the value to force the next dice roll to
     *
     */
    public void forceNextDiceRoll(final int roll) {
        this.dice.forceNextRoll(roll);
    }

    /**
     * Run the game for a specified number of rounds.
     * For each round every player takes one turn.
     * The game stops early if only one player remains.
     * After the game ends, the winner is declared.
     * @param numberOfRounds the number of rounds to play
     * @param scanner the Scanner for user input
     */
    public void playRounds(final int numberOfRounds, final Scanner scanner) {

        for (int round = 0; round < numberOfRounds; round++) {
            System.out.println("Round " + (round + 1) + " begins!");
            //to see if it is actually doing rounds

            for (Player currentplayer : players) {
                handlePlayerTurn(currentplayer, scanner);
            }

            if (players.size() == 1) {
                System.out.println("This is the end of the game." + "\n" + "There is only one player remaining");
                break;
                //exits the ronunds and stops the game
            }
        }
        declareWinner();
        //call declareWinner method
    }

    /**
     * Handles a single player's turn.
     * Checks for cycle race, bankruptcy, station teleport
     * Provides movement logic to rollAndMove() method
     * @param currentplayer the player whose turn it is
     * @param scanner the Scanner for user input
     */
    private void handlePlayerTurn(final Player currentplayer, final Scanner scanner) {
        //Check if the player is on a cycle race square at the start of their turn
        if (cycleRacePositions[currentplayer.getPosition()]) {
            currentplayer.setCycleRestriction(true);
        }
        // Check bankruptcy at start of turn
        if (currentplayer.getBalance() < 0) {
            handleBankruptcy(currentplayer);
        }
        //skip turn if player was removed
        if (!players.contains(currentplayer)) {
            return;  // ← CHANGED from continue to return
        }

        //ADD STATION CHECK HERE — before the dice roll
        if (board.getUndergroundStation(currentplayer.getPosition()) && !currentplayer.getJustTeleported()) {
            System.out.println("Station detected — teleporting!");
            currentplayer.setJustTeleported(true);
            playerOnSquare(currentplayer, scanner);
            System.out.println("Continue reached — skipping dice roll!");
            return;  // ← CHANGED from continue to return
        }
        // Roll dice and move
        rollAndMove(currentplayer, scanner);
    }

    /**
     * Rolls the dice, applies the cycle restrictions, moves the player and handles passing home.
     * @param currentplayer the player whose tuurn it is
     * @param scanner the scanner for user input
     */
    private void rollAndMove(final Player currentplayer, final Scanner scanner) {
        int steps = calculateSteps(currentplayer);
        currentplayer.move(steps, board.getSize());

        System.out.println("Player " + currentplayer + " rolled " + steps
                + " and moved to square " + currentplayer.getPosition()
                + " Balance: " + currentplayer.getBalance());

        int oldPosition = (currentplayer.getPosition() - steps + board.getSize()) % board.getSize();

        if (currentplayer.getPosition() < oldPosition) {
            addPassingHomeBonus(currentplayer, oldPosition);
            playerOnSquare(currentplayer, scanner);
        }
    }

    private int calculateSteps(final Player currentplayer) {
        int roll1 = dice.roll();
        int roll2 = dice.roll();
        int steps = roll1 + roll2;

        System.out.println("Steps before move: " + steps);

        if (currentplayer.getCycleRestriction()) {
            if (steps >= 4) {
                steps = 0;
                System.out.println("Player " + currentplayer + " rolled " + steps
                        + " — cycle restriction applied, moving 0");
            } else {
                System.out.println("Player " + currentplayer + " rolled " + steps
                        + " — restriction removed, moving normally");
            }
            currentplayer.setCycleRestriction(false);
        }
        return steps;
    }

    /**
     * Declares the winner of the game.
     * If only one player remains, they are the winner.
     * Otherwise, the player with the highest assets wins.
     */
    private void declareWinner() {
        if (players.size() == 1) {
            System.out.println("Congratulations " + players.get(0) + "\n"
                    + "with total assets of £" + calculateAssets(players.get(0)) + "!");
        } else {
            Player winner = getWinner();
            System.out.println("Congratulations " + winner + "\n"
                    + "with total assets of £" + calculateAssets(winner) + "!");
        }
    }


    /**
     * Calculate all the assets of the player.
     * Assests = balance + total purchase price of all owned properties.
     *
     * @param player the thayer whose assets are to be calculated.
     * @return the total asset value of the player.
     */
    public int calculateAssets(final Player player) {
        //Application.java needs to display the winner's assets.
        // Since calculateAssets() is a method that calculates player assets,
        // it makes sense to be public so other classes can use it.
        int totalPropertyValue = 0;

        for (int i = 0; i < board.getSize(); i++) {
            Object square = board.getSquare(i);

            if (square instanceof Property property && property.getPropertyOwner() == player) {
                //if the square is a property and the player owns it
                totalPropertyValue = totalPropertyValue + property.getPropertyPurchasePrice();
            }
        }
        return player.getBalance() + totalPropertyValue;
    }

    /**
     * Returns the player with the highest total assets.
     * Assets = cash balance + total purchase price of all owned properties.
     *
     * @return the player with the highest assets
     */
    public Player getWinner() {
        Player winner = players.get(0);
        int playerWithHighestAssets = calculateAssets(winner);

        for (Player consideredPlayer : players) {
            // Loop through each player to find the one with the highest asset
            // Calculate assets for the current player being checked

            int playerAssets = calculateAssets(consideredPlayer);

            if (playerAssets > playerWithHighestAssets) {
                playerWithHighestAssets = playerAssets;
                winner = consideredPlayer;
            }
        }
        return winner;
    }

    /**
     * What to do with the square the player is standing on.
     * @param currentplayer the player whose turn it currently is
     * @param scanner the scanner used for user input
     */
    public void playerOnSquare(final Player currentplayer, final Scanner scanner) {
        boolean movingForward = false;
        //declare movingForward variable
        //player's current pos to check for us for pass HOME check
        int currentPosition = currentplayer.getPosition();
        if (board.getUndergroundStation(currentPosition)) {
            //if statement calls method in Board.java to check square status
            int newPosition = board.getOtherStation(currentPosition);
            //return RANDOM pos for another station
            //store it as var newPosition so player will move
            currentplayer.setPosition(newPosition);
            //player moved to a different station imediately
            if (newPosition < currentPosition) {
                movingForward = true;
                //pass rules for Home
                //direction matters to see if player recieves money
                int reward = getReward(currentplayer);
                currentplayer.setBalance(currentplayer.getBalance() + reward);
            }
            return;
        }
        handleNonStationSquare(currentplayer, scanner);
        //only runs of the square is not a station
        //too long of a block for checkstyle so split into 2
    }

    /**
     * Handles actions when a player lands on a non station square.
     * Decides whether the square is aProperty or CycleRace square.
     * If it is a Property, delegates to either handlePropertyPurchase()
     * or handleRentPayment() based on ownership.
     * @param currentplayer the player who is curently playing
     * @param scanner the scanner used for the user input
     */
    private void handleNonStationSquare(final Player currentplayer, final Scanner scanner) {
        Object square = board.getSquare(currentplayer.getPosition());

        if (square instanceof Property property) {
            if (property.getPropertyOwner() == null && currentplayer.getBalance() >= property.getPropertyPurchasePrice()) {
                handlePropertyPurchase(currentplayer, property, scanner);
            } else if (property.getPropertyOwner() != null && property.getPropertyOwner() != currentplayer) {
                handleRentPayment(currentplayer, property, scanner);
            }
        } else if (cycleRacePositions[currentplayer.getPosition()]) {
            currentplayer.setCycleRestriction(true);
        }
    }

    /**
     * Handles the purchase of a property.
     * @param currentplayer the player buying the property
     * @param property the property to buy
     * @param scanner the Scanner for user input
     */
    private void handlePropertyPurchase(final Player currentplayer, final Property property, final Scanner scanner) {
        boolean wantsToBuy = askToBuyProperty(property, scanner);

        if (wantsToBuy) {
            currentplayer.setBalance(currentplayer.getBalance() - property.getPropertyPurchasePrice());
            property.setPropertyOwner(currentplayer);
            System.out.println(currentplayer + " bought " + property.getPropertyName());
        } else {
            System.out.println(currentplayer + " chose not to buy " + property.getPropertyName());
        }
    }

    /**
     * Asks the player if they want to buy property.
     * @param property the property to buy
     * @param scanner the scanner gor user input
     * @return true if the player wants to buy but false ootherwise
     */
    private boolean askToBuyProperty (final Property property, final Scanner scanner){
            while (true) {
                System.out.print("Would you like to buy this property for £"
                        + property.getPropertyPurchasePrice() + "? (yes/no): ");

                String input = scanner.nextLine().trim().toLowerCase();

                if (input.equals("yes")) {
                    return true;
                } else if (input.equals("no")) {
                    return false;
                } else {
                    System.out.println("Invalid input. Please enter 'yes' or 'no'.");
                }
            }
        }

    /**
     * Handles the payment of rent.
     * @param currentplayer the player paying rent
     * @param property the property they landed on
     * @param scanner the Scanner for user input
     */
    private void handleRentPayment(final Player currentplayer, final Property property, final Scanner scanner) {
        currentplayer.setBalance(currentplayer.getBalance() - property.getPropertyRentalPrice());
        handleBankruptcy(currentplayer);
        property.getPropertyOwner().setBalance(property.getPropertyOwner().getBalance() + property.getPropertyRentalPrice());
    }

    /**
     * Calculates the reward for passing Home and 1% of owned property.
     * @param currentplayer the player currently playing and passed Home
     * @return the total reward monopoly
     */
    private int getReward(final Player currentplayer) {
        int reward = 200;
        //money player gets when they pass Home like passing GO
        int totalRent = 0;
        //sets rent value to 0 initially
        for (int i = 0; i < board.getSize(); i++) {
            //check is the square a property and if currentplayer owns it
            //if yes add to totalRent
            Object square = board.getSquare(i);
            //counter starting at 0

            if (square instanceof Property property && property.getPropertyOwner() == currentplayer) {
                //checks if square is a property
                //if yes creates a property var that acts as Property
                //checks if current player owns the property
                // && means both conditions must be true
                totalRent += property.getPropertyRentalPrice();
            }
        }
        reward += totalRent / 100;
        return reward;
    }

    /**
     * Handles players who have neg balance by selling properties.
     *
     * @param currentplayer the player who could be bankrupt
     */
    private void handleBankruptcy(final Player currentplayer) {
        while (currentplayer.getBalance() < 0/* && currentplayer Owns atleast 1 property*/) {
            Property propertyForSale = findCheapestProperty(currentplayer);
            if (propertyForSale == null) {
                break;
            } else {
                int halfPriceOfProperty = propertyForSale.getPropertyPurchasePrice() / 2;
                int newBalance = currentplayer.getBalance() + halfPriceOfProperty;
                currentplayer.setBalance(newBalance);
                propertyForSale.setPropertyOwner(null);

                //print for when property is sold
                System.out.println(currentplayer + " sold " + propertyForSale.getPropertyName() + " for £" + halfPriceOfProperty);
            }
        }
        if (currentplayer.getBalance() < 0) {
            players.remove(currentplayer);
            //removes player if their balance becomes -ve
            //they are out of the game
            //Print  for when player looses
            System.out.println(currentplayer + " is bankrupt and out of the game!");
        }
    }

    /**
     * FOR TESTING ONLY — forces a bankruptcy check on a player.
     *
     * @param player the player to check
     */
    public void forceBankruptcyCheck(final Player player) {
        handleBankruptcy(player);
    }

    /**
     * Finds property with the lowest purchase value owned by the player.
     *
     * @param player the player whose properties are being sold
     * @return the cheapest property the player owns and null if they don't own any
     */
    private Property findCheapestProperty(final Player player) {
        Property cheapest = null;
        for (int index = 0; index < board.getSize(); index++) {
            Object square = board.getSquare(index);
            if (square instanceof Property property) {
                if (property.getPropertyOwner() == player) {
                    if (cheapest == null) {
                        cheapest = property;
                    } else if (property.getPropertyPurchasePrice() < cheapest.getPropertyPurchasePrice()) {
                        cheapest = property;
                    }
                }
            }
        }
        return cheapest;
    }

    /**
     * Adds the passing home plus 1% of the player's owned properties rental price.
     * @param player the player who moved
     * @param oldPosition the player's position before moving
     */
    private void addPassingHomeBonus(final Player player, final int oldPosition) {
        //checks if player has moved to a position less than previous position
        if (player.getPosition() < oldPosition) {
            int totalRent = 0;
            for (int i = 0; i < board.getSize(); i++) {
                //creates var for sum of rentalPrices and loops every square on board
                Object square = board.getSquare(i);
                if (square instanceof Property property && property.getPropertyOwner() == player) {
                    totalRent += property.getPropertyRentalPrice();
                    //checks if square has a property
                    //checks if owner of the property is the current player
                    //adds property rental price to total
                }
            }
            int bonus = totalRent / 100;
            player.setBalance(player.getBalance() + 200 + bonus);
        }
    }
}
