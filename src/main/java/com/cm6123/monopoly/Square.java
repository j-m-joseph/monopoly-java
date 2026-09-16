package com.cm6123.monopoly;
import java.util.Scanner;

/**
 * Abstract addition and satisfies inheritance requirement.
 */
public abstract class Square {

    //each square knows what to do when a player lands on it

    /**
     * The name of this square.
     */
    private final String name;
    //every square has a name
    //a field called name

    /**
     * Creates a new square with the provided name.
     * @param squareName the name of the square
     */
    protected Square(final String squareName) {
        //only subclasses can call this constructer
        //a name must be given to create a square
        this.name = squareName;
        //stores the name in the field
    }

    /**
     * Returns the name of this square.
     * @return the name of the square
     */
    public final String getName() {
        //returns the name of the square
        return name;
    }

    /**
     * Called when a player lands on this square.
     * Each subclass must define what happens.
     * @param game the current game
     * @param player the player who landed on this square
     * @param scanner the scanner used for user input
     */
    public abstract void whenLand(Game game, Player player, Scanner scanner);
}
