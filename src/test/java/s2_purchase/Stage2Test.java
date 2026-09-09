package s2_purchase;

import com.cm6123.monopoly.Board;
import com.cm6123.monopoly.Player;
import com.cm6123.monopoly.Property;
import com.cm6123.monopoly.Game;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class Stage2Test {
    @Test
    public void testPropertyPurchase() {
        List<Property> squares = new ArrayList<>();
        squares.add(new Property("Armagh Road", 20, 3));
        for (int i = 0; i < 15; i++) {
            squares.add(null);
        }
        Board board = new Board(squares);
        Player player = new Player();
        player.setBalance(1000);
        player.setPosition(0);

        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        Game game = new Game(board, playerList);

        Scanner testScanner = new Scanner("yes");
        game.playerOnSquare(player, testScanner);
        assertEquals(980, player.getBalance());
        assertSame(player, squares.get(0).getPropertyOwner());
    }
    @Test
    public void testCannotAffordProperty() {
        //creates a board with a property
        List<Property> squares = new ArrayList<>();
        squares.add(new Property("Test street", 2000, 100));
        for (int i = 0; i < 15; i++) {
            squares.add(null);
        }
        Board board = new Board(squares);
        //creates a new board objexct with squares list I already made

        //Player doesn't have enough money
        Player player = new Player();
        player.setBalance(1000);
        player.setPosition(0);

        List<Player> playerList = new ArrayList<>();
        //empty list that holds player objects
        playerList.add(player);
        Game game = new Game(board, playerList);
        //creates a new game object giving it a board and the list of players

        //Tries to buy property but saying yes bit should fail due to insufficient funds
        Scanner testScanner = new Scanner("yes");
        game.playerOnSquare(player, testScanner);

        // Balance should stay 1000 (unchanged) because couldn't afford it
        assertEquals(1000, player.getBalance());
        // Property should have no owner
        assertEquals(null, squares.get(0).getPropertyOwner());
    }

    @Test
    public void testAlreadyOwnedProperty() {
        // Create board with property
        List<Property> squares = new ArrayList<>();
        squares.add(new Property("Test Street", 50, 5));
        for (int i = 0; i < 15; i++) {
            squares.add(null);
        }
        Board board = new Board(squares);

        // Player 1 owns the property
        Player player1 = new Player();
        player1.setBalance(1000);
        squares.get(0).setPropertyOwner(player1);

        // Player 2 tries to buy it
        Player player2 = new Player();
        player2.setBalance(1000);
        player2.setPosition(0);

        List<Player> playerList = new ArrayList<>();
        playerList.add(player1);
        playerList.add(player2);
        Game game = new Game(board, playerList);

        // Player 2 tries to buy already-owned property
        Scanner testScanner = new Scanner("yes");
        game.playerOnSquare(player2, testScanner);

        // Player 2's balance should NOT change
        assertEquals(995, player2.getBalance());
        // Player 1 should still own it
        assertSame(player1, squares.get(0).getPropertyOwner());
    }

    @Test
    public void testDeclinePurchase() {
        // Create board with property
        List<Property> squares = new ArrayList<>();
        squares.add(new Property("Test Street", 50, 5));
        for (int i = 0; i < 15; i++) {
            squares.add(null);
        }
        Board board = new Board(squares);

        Player player = new Player();
        player.setBalance(1000);
        player.setPosition(0);

        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        Game game = new Game(board, playerList);

        // Player says "no" to buying
        Scanner testScanner = new Scanner("no");
        game.playerOnSquare(player, testScanner);

        // Balance should NOT change
        assertEquals(1000, player.getBalance());
        // Property should have no owner
        assertEquals(null, squares.get(0).getPropertyOwner());
    }
}
//previous attempt but modified