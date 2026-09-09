package com.cm6123.monopoly.game;

//imports
import com.cm6123.monopoly.Board;
import com.cm6123.monopoly.Player;
import com.cm6123.monopoly.Property;
import com.cm6123.monopoly.dice.Dice;

import java.util.Scanner;

/**
 * Manages player movement on the board.
 * Handles dice rolls, cycle restrictions, and passing home bonuses.
 */
public class MovementManager {

    /**
     * The board object.
     */
    private final Board board;

    /**
     * The dice object.
     */
    private final Dice dice;

    /**
     * Creates a new MovementManager.
     * @param pboard the game board
     * @param pdice the dice object
     */
    public MovementManager(final Board pboard, final Dice pdice) {
        this.board = pboard;
        this.dice = pdice;
    }

    /**
     * Rolls the dice, applies the cycle restrictions, moves the player and handles passing home.
     * @param currentplayer the player whose turn it is
     * @param scanner the scanner for user input
     * @param cycleRacePositions the squares that are cycle race squares
     */
    public void rollAndMove(final Player currentplayer, final Scanner scanner, final boolean[] cycleRacePositions) {
        int steps = calculateSteps(currentplayer, cycleRacePositions);
        currentplayer.move(steps, board.getSize());

        System.out.println("Player " + currentplayer + " rolled " + steps
                + " and moved to square " + currentplayer.getPosition()
                + " Balance: " + currentplayer.getBalance());
        }

    /**
     * Calculates the number of steps a player will move.
     * Applies cycle race restriction if the player is on a cycle square.
     * @param currentplayer the player whose steps are being calculated
     * @param cycleRacePositions the squares that are cycle race squares
     * @return the number of steps the player will move
     */
    public int calculateSteps(final Player currentplayer, final boolean[] cycleRacePositions) {
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
     * Adds the passing home plus 1% of the player's owned properties rental price.
     * method called when player passes a Home square.
     * @param player the player who moved
     * @param oldPosition the player's position before moving
     */
    public void addPassingHomeBonus(final Player player, final int oldPosition) {
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

    /**
     * Calculates the reward for passing Home and 1% of owned property rental price.
     * Player receives £200 plus 1% of all owned properties' rental prices.
     * @param currentplayer the player currently playing who passed Home
     * @return the total reward monopoly/amount
     */
    public int getReward(final Player currentplayer) {
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
}
