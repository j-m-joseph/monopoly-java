package com.cm6123.monopoly;

public class UndergroundStation {

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
}
