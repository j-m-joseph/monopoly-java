package com.cm6123.monopoly;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
//random import for stations

public final class Board {

    /**
     * Field that can hold 16 items.
     * Squares is the name of the field.
     */
    private final List<Property> squares;

    /**
     * Field that holds underground stations.
     * undergroundStations is the name of the field.
     */
    private final List<UndergroundStation> undergroundStations;

    /**
     * Random number generator used to pick the station the current player moves to.
     */
    private final Random randomstation = new Random();

    /**
     * Constructs a new board with a given list of squares.
     * @param squareList list of 16 squares null for roads
     * Initialize undergroundStations list as new ArrayList<>.
     * Empty list for stations to be added.
     */
    public Board(final List<Property> squareList) {
        this.squares = squareList;
        undergroundStations = new ArrayList<>();
    }

    /**
     * Returns square at given position.
     * @param position the 0 index
     * @return the property position
     */
    public Object getSquare(final int position) {
        return squares.get(position);
    }

    /**
     * Returns the number of squares on the board.
     * @return the board sixe (16)
     */
    public int getSize() {
        return squares.size();
    }

    /**
     * Adds underground station to the board's list.
     * @param undergroundStation object to add
     */
    public void addUndergroundStation(final UndergroundStation undergroundStation) {
            undergroundStations.add(undergroundStation);
        }

    /**
     * Returns a random station different from the one the player is on.
     * @param currentPosition is excluded
     * @return a random station from the remaining ones
     */
    public int getOtherStation(final int currentPosition) {
        List<Integer> otherpositions = new ArrayList<>();
        for (UndergroundStation us : undergroundStations) {
            int station = us.getStationPosition();
            if (station != currentPosition) {
                otherpositions.add(station);
            }
        }
        int randomindex = randomstation.nextInt(otherpositions.size());
        return otherpositions.get(randomindex);
        //after loop pick random index from the list of remaining positions
    }

    /**
     * Checks if the square the current player landed on is a station.
     * @param position board index to check
     * @return true of the square is a station and false if it's not
     */
    public boolean getUndergroundStation(final int position) {
        for (UndergroundStation us : undergroundStations) {
            if (us.getStationPosition() == position) {
                return true;
            }
        }
        return false;
    }

    /**
     * Clears all underground stations from the board.
     * This method is primarily used for testing purposes to ensure
     * a clean state before adding specific stations for test scenarios.
     */
    public void clearUndergroundStations() {
        undergroundStations.clear();
    }
}
