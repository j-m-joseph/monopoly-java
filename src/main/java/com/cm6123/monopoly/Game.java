package com.cm6123.monopoly;

//A list is more flexible
import com.cm6123.monopoly.dice.Dice;
import java.util.List;


public final class Game {

    /**
     * What board players are using.
     * Board has a size that game uses to wrap positions when players move.
     */
    private final Board board;

    /**
     * Which players are playing in what order.
     * loops through them for each round.
     */
    private final List<Player> players;

    /**
     * The dice used for rolling one for now stage1.
     */
    private final Dice dice;

    /**
     * Contrustor line.
     *
     * @param gameboard  the game board
     * @param playerList the list of players
     */
    public Game(final Board gameboard, final List<Player> playerList) {
        this.board = gameboard;
        this.players = playerList;
        this.dice = new Dice(6);
        // 6 faces for a standard die
    }

    /**
     * Run the game for a specified number of rounds.
     * For each round every player takes one turn.
     * void is for when no values are returned.
     *
     * @param numberOfRounds the number of rounds to play
     */
    public void playRounds(final int numberOfRounds) {

        for (int round = 0; round < numberOfRounds; round++) {

            for (Player currentplayer : players) {
                //for each player in the list(players)
                //name the rolling player currentplayer
                //then that player will be having their turn
                int steps = dice.roll();
                currentplayer.move(steps, board.getSize());
                playerOnSquare(currentplayer);
            }
        }
    }

    /**
     * Returns the player with the highest balance.
     *
     * @return the richest player
     */
    public Player getWinner() {
        Player winner = players.get(0);

        for (Player consideredplayer : players) {
            //one player at a time from list players
            //that one player call is currentplayer
            if
            (consideredplayer.getBalance() > winner.getBalance()) {
                winner = consideredplayer;
            }
        }
        return winner;
    }

    /**
     * What to do with the square the player is standing on.
     *
     * @param player the player whose turn it currently is
     */
    public void playerOnSquare(final Player currentplayer) {

        Object square = board.getSquare(currentplayer.getPosition());
        if (square instanceof Property property) {
            if (property.getPropertyOwner() == null
                    && currentplayer.getBalance() >= property.getPropertyPurchasePrice()) {
                currentplayer.setBalance(currentplayer.getBalance() - property.getPropertyPurchasePrice());
                property.setPropertyOwner(currentplayer);
            } else if (property.getPropertyOwner() != null
                    && (property.getPropertyOwner() != currentplayer)) {

                currentplayer.setBalance(currentplayer.getBalance() - property.getPropertyRentalPrice());
                property.getPropertyOwner().setBalance(property.getPropertyOwner().getBalance() + property.getPropertyRentalPrice());
            }
        }
    }
}