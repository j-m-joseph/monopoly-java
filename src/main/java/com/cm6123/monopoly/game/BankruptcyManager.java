package com.cm6123.monopoly.game;

import com.cm6123.monopoly.Board;
import com.cm6123.monopoly.Player;
import com.cm6123.monopoly.Property;
import java.util.List;

public final class BankruptcyManager {

    /**
     * The board object containing all squares.
     */
    private final Board board;

    /**
     * List of active players in the game.
     */
    private final List<Player> players;

    /**
     * Creates a new BankruptcyManager.
     * @param pboard the game board
     * @param pPlayers the list of players
     */
    public BankruptcyManager(final Board pboard, final List<Player> pPlayers) {
        //to aoid confusion param should have different names to fields
        this.board = pboard;
        this.players = pPlayers;
    }

    /**
     * Handles players who have neg balance by selling properties.
     * @param currentplayer the player who could be bankrupt
     */
    public void handleBankruptcy(final Player currentplayer) {
        while (currentplayer.getBalance() < 0) {
            Property propertyForSale = findCheapestProperty(currentplayer);
            if (propertyForSale == null) {
                //no properties break out of the loop
                break;
            } else {
                int halfPriceOfProperty = propertyForSale.getPropertyPurchasePrice() / 2;
                int newBalance = currentplayer.getBalance() + halfPriceOfProperty;
                currentplayer.setBalance(newBalance);
                propertyForSale.setPropertyOwner(null);

                //print for when property is sold
                System.out.println(currentplayer + " sold " + propertyForSale.getPropertyName() + " for £" + halfPriceOfProperty);
            }
        }
        if (currentplayer.getBalance() < 0) {
            players.remove(currentplayer);
            //removes player if their balance becomes -ve
            //they are out of the game
            //Print  for when player looses
            System.out.println(currentplayer + " is bankrupt and out of the game!");
        }
    }

    /**
     * FOR TESTING ONLY — forces a bankruptcy check on a player.
     * @param player the player to check
     */
    public void forceBankruptcyCheck(final Player player) {
        handleBankruptcy(player);
    }

    /**
     * Finds property with the lowest purchase value owned by the player.
     * @param player the player whose properties are being sold
     * @return the cheapest property the player owns and null if they don't own any
     */
    private Property findCheapestProperty(final Player player) {
        Property cheapest = null;
        for (int index = 0; index < board.getSize(); index++) {
            Object square = board.getSquare(index);
            if (square instanceof Property property) {
                if (property.getPropertyOwner() == player) {
                    if (cheapest == null) {
                        cheapest = property;
                    } else if (property.getPropertyPurchasePrice() < cheapest.getPropertyPurchasePrice()) {
                        cheapest = property;
                    }
                }
            }
        }
        return cheapest;
    }
}
