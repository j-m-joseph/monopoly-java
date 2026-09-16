package com.cm6123.monopoly;

import com.cm6123.monopoly.dice.Dice;
import com.cm6123.monopoly.game.BankruptcyManager;
import com.cm6123.monopoly.game.MovementManager;
import com.cm6123.monopoly.game.PropertyManager;
import com.cm6123.monopoly.game.CycleManager;

import java.util.List;
import java.util.Scanner;

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
     * Manages bankruptcy logic for the game.
     * Handles selling properties and removing bankrupt players.
     */
    private final BankruptcyManager bankruptcyManager;

    /**
     * Manages property related logic for the game.
     * Handles property purchase, rent and ownership.
     */
    private final PropertyManager propertyManager;

    /**
     * Manages movement logic for the game.
     * Handles rolling dice, moving players and passing Home bonus.
     */
    private final MovementManager movementManager;

    /**
     * Manages cycle race logic for the game.
     * Handles cycle race squares and restrictions.
     */
    private final CycleManager cycleManager;

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
        this.bankruptcyManager = new BankruptcyManager(gameboard, playerList);
        this.propertyManager = new PropertyManager(gameboard, bankruptcyManager);
        this.movementManager = new MovementManager(gameboard, dice);
        this.cycleManager = new CycleManager(gameboard);

    }

    //To fix build error

    /**
     * Marks the square as a CycleRace square.
     * Delegates to CycleManager.
     *
     * @param position the index of the square on the board
     */
    public void setCycleRaceSquare(final int position) {
        cycleManager.setCycleRaceSquare(position);
    }

    /**
     * Checks if a given square is a CycleRaceSquare.
     * Delegates to CycleManager.
     *
     * @param position the index of the square on the board
     * @return true if the square is a cycleRaceSquare
     */
    public boolean getCycleRacePosition(final int position) {
        return cycleManager.getCycleRacePosition(position);
    }

    /**
     * Forces the next dice roll to be a specific value to test.
     * This method sets the next roll value without forcing it.
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
     */
    public void forceNextDiceRoll(final int roll) {
        this.dice.forceNextRoll(roll);
    }

    /**
     * Run the game for a specified number of rounds.
     * For each round every player takes one turn.
     * The game stops early if only one player remains.
     * After the game ends, the winner is declared.
     *
     * @param numberOfRounds the number of rounds to play
     * @param scanner        the Scanner for user input
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
     *
     * @param currentplayer the player whose turn it is
     * @param scanner       the Scanner for user input
     */
    private void handlePlayerTurn(final Player currentplayer, final Scanner scanner) {
        if (cycleManager.getCycleRacePosition(currentplayer.getPosition())) {
            //Check if the player is on a cycle race square at the start of their turn
            currentplayer.setCycleRestriction(true);
        }
        if (currentplayer.getBalance() < 0) {
            // Check bankruptcy at start of turn
            bankruptcyManager.handleBankruptcy(currentplayer);
        }
        if (!players.contains(currentplayer)) {
            //skip turn if player was removed
            return;
            //CHANGED from continue to return
        }

        //STATION CHECK — before the dice roll
        if (board.getUndergroundStation(currentplayer.getPosition()) && !currentplayer.getJustTeleported()) {
            System.out.println("Station detected — teleporting!");
            currentplayer.setJustTeleported(true);
            playerOnSquare(currentplayer, scanner);
            System.out.println("Continue reached — skipping dice roll!");
            return;
            //CHANGED from continue to return
        }
        rollAndMove(currentplayer, scanner);
        // Roll dice and move
    }

    /**
     * Rolls the dice, applies the cycle restrictions, moves the player and handles passing home.
     *
     * @param currentplayer the player whose tuurn it is
     * @param scanner       the scanner for user input
     */
    private void rollAndMove(final Player currentplayer, final Scanner scanner) {
        int oldPosition = currentplayer.getPosition();
        int steps = movementManager.calculateSteps(currentplayer, cycleManager.getCycleRacePositions());
        currentplayer.move(steps, board.getSize());

        System.out.println("Player " + currentplayer + " rolled " + steps
                + " and moved to square " + currentplayer.getPosition()
                + " Balance: " + currentplayer.getBalance());

        if (currentplayer.getPosition() < oldPosition) {
            movementManager.addPassingHomeBonus(currentplayer, oldPosition);
        }
        playerOnSquare(currentplayer, scanner);
    }

    /**
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
     * Delegates to the square's whenLand method.
     * @param currentplayer the player whose turn it currently is
     * @param scanner the scanner used for user input
     */
    public void playerOnSquare(final Player currentplayer, final Scanner scanner) {
        int currentPosition = currentplayer.getPosition();

        if (cycleManager.getCycleRacePosition(currentPosition)) {
            currentplayer.setCycleRestriction(true);
            return;
        }

        Square square = board.getSquare(currentPosition);
        square.whenLand(this, currentplayer, scanner);
    }

    /**
     * FOR TESTING ONLY — forces a bankruptcy check on a player.
     * @param player the player to check
     */
    public void forceBankruptcyCheck(final Player player) {
        bankruptcyManager.forceBankruptcyCheck(player);
    }

    /**
     * Handles what happens when a player lands on a property.
     * Gives o propertyManager to handle.
     * @param player the player who landed
     * @param property the property they landed on
     * @param scanner the scanner used for user input
     */
    public void handlePropertyLanding(final Player player, final Property property, final Scanner scanner) {
        propertyManager.handleNonStationSquare(player, scanner);
    }

    /**
     * Handles what happens when a plauer lands on an underground station.
     * Moves the player to another station
     * @param player the player who landed
     */
    public void handleStationLanding(final Player player) {
        int currentPosition = player.getPosition();
        int newPosition = board.getOtherStation(currentPosition);
        player.setPosition(newPosition);

        if (newPosition < currentPosition) {
            int reward = movementManager.getReward(player);
            player.setBalance(player.getBalance() + reward);
        }
    }
}
