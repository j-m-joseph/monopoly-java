package com.cm6123.monopoly;

import java.util.Scanner;

/**
 * A road square so nothing happens when a player lands on it.
 */
public final class Road extends Square {

    /**
     * Creates a new road square.
     * Gives the road a "name" just "Road".
     * @param name the mane of the road
     */
    public Road(final String name) {
        super(name);
    }

    /**
     * Called when a player lands on a road.
     * @param game the current game
     * @param player the player who landed on the road
     * @param scanner the scanner used for the user input
     */
    @Override
    public void whenLand(final Game game, final Player player, final Scanner scanner){
        //nothing happens on a road
    }
}
