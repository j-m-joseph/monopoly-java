package com.cm6123.monopoly;

public final class Player {

    /**
     * The Player's current balance in pounds.
     * The Player's current balance in pounds.
     * The money they are meant to start with
     */
    private int balance = 1000;

    /** The Player's current position on the board.
     * it is set to start at "Home"
     */
    private int position = 0;

    /**
     *  The Cycle race field.
     * False is the default field.
     */
    private boolean cycleRestriction;

    /**
     * The teleport fiels.
     * False is the default field.
     */
    private boolean justTeleported;
    //player's new teleport status
    //these are all attributes of the player
    //all private because only the player class shuld access them


    /**
     *  Returns the Player's current balance.
     *  @return the player's current balance in pounds
     */
    public int getBalance() {
        return balance;
    }
    /**
     * Sets the player's balance.
     * @param newBalance the new balance to set
     */
    public void setBalance(final int newBalance) {
        this.balance = newBalance;
    }
    /** Returns the player's current position.
     * @return the player's position
     */
    public int getPosition() {
        return position;
    }
    /** Sets the player's position.
     * @param newPosition the new position on the board
     */
    public void setPosition(final int newPosition) {
        this.position = newPosition;
    }

    /**
     * Sets the justTeleported flag.
     * @param value true if player just teleported
     */
    public void setJustTeleported(final boolean value) {
        this.justTeleported = value;
    }
    //The order doesn't matter
    // grouping by elevance and action and state methods
    //good for code organization and readability

    /**
     * Gets the justTeleported flag.
     * @return true if player just teleported
     */
    public boolean getJustTeleported() {
        return justTeleported;
    }

    /** Moves the player to a new position.
     * @param steps the number of spaces the player moves
     * @param boardSize the number of squares on the board
     */
    public void move(final int steps, final int boardSize) {
        int newPosition = (position + steps) % boardSize;
        position = newPosition;
        }

    /** Cycle restriction rule.
     * @return getter method
     * TRUE if the player has a cycle race restriction
     */
    public boolean getCycleRestriction() {
        return cycleRestriction;
        }

    /** to restrict the next move.
     * @param newCycleRestriction TRUE to restrict the next move
     *  False to remove the restriction
     */
    public void setCycleRestriction(final boolean newCycleRestriction) {
        this.cycleRestriction = newCycleRestriction;
        }
    }
