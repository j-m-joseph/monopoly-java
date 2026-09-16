package com.cm6123.monopoly;
import java.util.Scanner;

public final class UndergroundStation extends Square {

    /**
     * Position of a particular station.
     */
    private final int stationPosition;

    /**
     * Constructor so that every underground station is created witha set position.
     * @param stationPos to store station position
     * Final because position is stationary
     */
    public UndergroundStation(final int stationPos) {
      super("Station " + stationPos);
        this.stationPosition = stationPos;
        //renamed for checkstyle
    }

    /**
     * Returns the number of underground stations.
     * @return posiitions of the stations
     */
    public int getStationPosition() {
        return stationPosition;
    }

    @Override
    public void whenLand(final Game game, final Player player, final Scanner scanner) {
        game.handleStationLanding(player);
    }
}
