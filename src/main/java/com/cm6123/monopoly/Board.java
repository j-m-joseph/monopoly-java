package com.cm6123.monopoly;

import java.util.List;
public final class Board {

    /**
     * Field that can hold 16 items.
     * Squares is the name of the field.
     */
    private final List<Property> squares;

    /**
     *Constructs a new board with a given list of squares.
     * @param squareList list of 16 squares null for roads
     */
    public Board(final List<Property> squareList) {
        this.squares = squareList;
        }

    /**
     * Returns square at given position.
     * @param position the 0 index
     * @return the property position
     */
    public Property getSquare(final int position) {
        return squares.get(position);
    }

    /**
     * Returns the number of squares on the board.
     * @return the board sixe (16)
     */
    public int getSize() {
        return squares.size();
    }
}
