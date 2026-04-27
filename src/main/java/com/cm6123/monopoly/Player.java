package com.cm6123.monopoly;

public final class Player {

    /** The Player's current balance in pounds. */
    private int balance = 1000;
    /** The Player's current position on the board. */
    private int position = 0;
    /** The Cycle race field.
     * False is the default field. */
    private boolean cycleRestriction;

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

    /** Moves the player to a new position.
     * @param steps the number of spaces the player moves
     * @param boardSize the number of squares on the board
     */
    public void move(final int steps, final int boardSize) {
        int newPosition = (position + steps) % boardSize;
        if (newPosition < position) {
            balance = balance + 200;
        }
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
