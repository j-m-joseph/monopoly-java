package com.cm6123.monopoly.game;

//imports
import com.cm6123.monopoly.Board;
import com.cm6123.monopoly.Player;
import com.cm6123.monopoly.Property;
import java.util.Scanner;

/**
 * Manages property-related actions in the game.
 * Handles property purchase, rent collection, and ownership transfers.
 */
public class PropertyManager {

    /**
     * The board object.
     */
    private final Board board;

    /**
     * The bankruptcy manager object.
     */
    private final BankruptcyManager bankruptcyManager;

    /**
     * Creates the new PropertyManager.
     * @param pboard the game board
     * @param pbankruptcyManager the bankruptcy manager
     */
    public PropertyManager(final Board pboard, final BankruptcyManager pbankruptcyManager) {
        this.board = pboard;
        this.bankruptcyManager = pbankruptcyManager;
    }

    /**
     * Handles actions when a player lands on a non station square.
     * Decides whether the square is aProperty or CycleRace square.
     * If it is a Property, delegates to either handlePropertyPurchase()
     * or handleRentPayment() based on ownership.
     * @param currentplayer the player who is curently playing
     * @param scanner the scanner used for the user input
     */
    public void handleNonStationSquare(final Player currentplayer, final Scanner scanner) {
        Object square = board.getSquare(currentplayer.getPosition());

        if (square instanceof Property property) {
            if (property.getPropertyOwner() == null && currentplayer.getBalance() >= property.getPropertyPurchasePrice()) {
                handlePropertyPurchase(currentplayer, property, scanner);
            } else if (property.getPropertyOwner() != null && property.getPropertyOwner() != currentplayer) {
                handleRentPayment(currentplayer, property);
            }
        }
    }

    /**
     * Handles the purchase of a property.
     * @param currentplayer the player buying the property
     * @param property the property to buy
     * @param scanner the Scanner for user input
     */
    private void handlePropertyPurchase(final Player currentplayer, final Property property, final Scanner scanner) {
        boolean wantsToBuy = askToBuyProperty(property, scanner);

        if (wantsToBuy) {
            currentplayer.setBalance(currentplayer.getBalance() - property.getPropertyPurchasePrice());
            property.setPropertyOwner(currentplayer);
            System.out.println(currentplayer + " bought " + property.getPropertyName());
        } else {
            System.out.println(currentplayer + " chose not to buy " + property.getPropertyName());
        }
    }

    /**
     * Asks the player if they want to buy property.
     * @param property the property to buy
     * @param scanner  the scanner gor user input
     * @return true if the player wants to buy but false ootherwise
     */
    private boolean askToBuyProperty(final Property property, final Scanner scanner) {
        while (true) {
            System.out.print("Would you like to buy this property for £"
                    + property.getPropertyPurchasePrice() + "? (yes/no): ");

            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("yes")) {
                return true;
            } else if (input.equals("no")) {
                return false;
            } else {
                System.out.println("Invalid input. Please enter 'yes' or 'no'.");
            }
        }
    }

    /**
     * Handles the payment of rent.
     * @param currentplayer the player paying rent
     * @param property the property they landed on
     */
    private void handleRentPayment(final Player currentplayer, final Property property) {
        currentplayer.setBalance(currentplayer.getBalance() - property.getPropertyRentalPrice());
        bankruptcyManager.handleBankruptcy(currentplayer);
        property.getPropertyOwner().setBalance(property.getPropertyOwner().getBalance() + property.getPropertyRentalPrice());
    }
}
