package com.cm6123.monopoly;

public class Board {

    /**
     * The number of squares on the board.
     */
    private final int boardsize;

    /**
     * Build a board with a given size.
     * @param  size the number of squares
     */
    public Board(final int size) {
        this.boardsize = size;
    }

    /**
     * Returns the board size.
     *
     * @return the number of squares on the board
     */
    public int getBoardsize() {
        return boardsize;
    }

}
