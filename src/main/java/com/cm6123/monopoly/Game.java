package com.cm6123.monopoly;

//A list is more flexible
import java.util.List;
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
    private final boolean[] cycleRacePositions = new boolean[16];
    // each element is TRUE/FALSE
    //creates an array of 16 elements all False at the start

    /**
     * Marks the square as a CycleRace square.
     *
     * @param position the index of the square on the board
     *                 if a 4, 5 or 6 is rolled the player doesn't move
     */
    public void setCycleRaceSquare(final int position) {
        cycleRacePositions[position] = true;
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
     * Run the game for a specified number of rounds.
     * For each round every player takes one turn.
     * void is for when no values are returned.
     *
     * @param numberOfRounds the number of rounds to play
     */
    public void playRounds(final int numberOfRounds) {

        for (int round = 0; round < numberOfRounds; round++) {

            for (Player currentplayer : players) {
                //for each player in the list(players)
                //name the rolling player currentplayer
                //then that player will be having their turn
                int steps = dice.roll();
                //player has cyclerace restriction from previous turn
                if (currentplayer.getCycleRestriction()) {
                    if (steps >= 4) {
                        steps = 0;
                    }
                    currentplayer.setCycleRestriction(false);
                }
                //move player if needed
                int oldPosition = (currentplayer.getPosition() - steps + board.getSize()) % board.getSize();
                //current pos before moving
                currentplayer.move(steps, board.getSize());
                //moves player by steps
                if (currentplayer.getPosition() < oldPosition) {
                    //checks if new pos is < than old pos
                    addPassingHomeBonus(currentplayer, oldPosition);
                    playerOnSquare(currentplayer);
                }
            }
        }
    }

    /**
     * Returns the player with the highest balance.
     *
     * @return the richest player
     */
    public Player getWinner() {
        Player winner = players.get(0);

        for (Player consideredplayer : players) {
            //one player at a time from list players
            //that one player call is currentplayer
            if
            (consideredplayer.getBalance() > winner.getBalance()) {
                winner = consideredplayer;
            }
        }
        return winner;
    }

    /**
     * What to do with the square the player is standing on.
     *
     * @param currentplayer the player whose turn it currently is
     */
    public void playerOnSquare(final Player currentplayer) {
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
                //16 squares 0 square is HOME
                //pass rules for Home
                int reward = getReward(currentplayer);
                currentplayer.setBalance(currentplayer.getBalance() + reward);
            }
            return;
        }
        handleNonStationSquare(currentplayer);
        //only runs of the square is not a station
        //too long of a block for checkstyle so split into 2
    }

    /**
     * Handles everythig when player is not on station square.
     *
     * @param currentplayer the player curently playing
     */
    private void handleNonStationSquare(final Player currentplayer) {
        Object square = board.getSquare(currentplayer.getPosition());
        if (square instanceof Property property) {
            if (property.getPropertyOwner() == null && currentplayer.getBalance() >= property.getPropertyPurchasePrice()) {
                currentplayer.setBalance(currentplayer.getBalance() - property.getPropertyPurchasePrice());
                property.setPropertyOwner(currentplayer);
            } else if (property.getPropertyOwner() != null && (property.getPropertyOwner() != currentplayer)) {
                currentplayer.setBalance(currentplayer.getBalance() - property.getPropertyRentalPrice());
                handleBankruptcy(currentplayer);
                property.getPropertyOwner().setBalance(property.getPropertyOwner().getBalance() + property.getPropertyRentalPrice());
            }
        } else if (cycleRacePositions[currentplayer.getPosition()]) {
            currentplayer.setCycleRestriction(true);
        }
    }

    /**
     * Calculates the reward for passing Home and 1% of owned property.
     *
     * @param currentplayer the player currently playing and passed Home
     * @return the total reward monopoly
     */
    private int getReward(final Player currentplayer) {
        int reward = 200;
        //money player gets when theu pass Home like passing GO
        int totalRent = 0;
        //sets rent value to 0 initially
        for (int i = 0; i < board.getSize(); i++) {
            //check is the quare a property and if currentplayer owns it
            //if yes add to totalRent
            Object square = board.getSquare(i);
            //counter starting at 0

            if (square instanceof Property property && property.getPropertyOwner() == currentplayer) {
                //checks if square is a property
                //if yes creates a property var that acts as Property
                //chacks if current player owns the property
                // && means both conditions must be true
                totalRent += property.getPropertyRentalPrice();
            }
        }
        reward += totalRent / 100;
        return reward;
    }

    /**
     * Handles players who have neg balance by selling properties.
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
            }
            if (currentplayer.getBalance() < 0) {
                players.remove(currentplayer);
                //removes player if their balance becomes -ve
                //they are out of the game
            }
        }
    }

    /**
     * Finds property with the lowest purchase value owned by the player.
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
        //chacks if player has moved to a pos less than previos pos
        if (player.getPosition() < oldPosition) {
            int totalRent = 0;
            for (int i = 0; i < board.getSize(); i++) {
                //crates var for sum of rentalPrices and loops every sqaure on board
                Object square = board.getSquare(i);
                if (square instanceof Property property && property.getPropertyOwner() == player) {
                    totalRent += property.getPropertyRentalPrice();
                    //checks if square has a property
                    //checks if owner of the propery is the current player
                    //adds property rental price to total
                }
            }
            int bonus = totalRent / 100;
            player.setBalance(player.getBalance() + 200 + bonus);
        }
    }
}
