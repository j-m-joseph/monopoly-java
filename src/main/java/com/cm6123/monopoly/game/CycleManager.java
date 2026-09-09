package com.cm6123.monopoly.game;
import com.cm6123.monopoly.Board;
import com.cm6123.monopoly.Property;
import com.cm6123.monopoly.UndergroundStation;

/**
 * Manages cycle race squares on the board.
 * Cycle race squares restrict player movement when a 4, 5, or 6 is rolled.
 */
public class CycleManager {

    /**
     * The board object.
     */
    private final Board board;

    /**
     * List of cycle race positions.
     */
    private final boolean[] cycleRacePositions;

    /**
     * Creates a new CycleManager.
     * @param pboard the game board
     */
    public CycleManager(final Board pboard) {
        this.board = pboard;
        this.cycleRacePositions = new boolean[board.getSize()];
    }

    /**
     * Marks the square as a CycleRace square.
     * @param position the index of the square on the board
     * if a 4, 5 or 6 is rolled the player doesn't move
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
     * @param position the index of the square on the board
     * @return true if the square is a cycleRaceSquare and false if not
     */
    public boolean getCycleRacePosition(final int position) {
        return cycleRacePositions[position];
    }

    /**
     * Gets the cycle race position's array.
     * @return the cycle race positions array
     */
    public boolean[] getCycleRacePositions() {
        return cycleRacePositions;
    }
}
