package s4_bankruptcy;

import com.cm6123.monopoly.Board;
import com.cm6123.monopoly.Game;
import com.cm6123.monopoly.Player;
import com.cm6123.monopoly.Property;
import com.cm6123.monopoly.Square;
import com.cm6123.monopoly.Road;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class Stage4Test {
    @Test
    public void testplayerBankruptcyRecovery() {
        List<Square> squares = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            squares.add(new Road("Road"));
        }
        Property player1Property = new Property("Hobbit road", 200, 10);
        Property player2Property = new Property("Waverly road", 500, 50);
        squares.set(10, player1Property);
        squares.set(14, player2Property);
        Board board = new Board(squares);
        Player player1 = new Player();
        player1.setBalance(40);
        player1Property.setPropertyOwner(player1);

        Player player2 = new Player();
        player2.setBalance(1000);
        player2Property.setPropertyOwner(player2);
        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        Game game = new Game(board, players);
        // add the players and set the respective balances
        player1.setPosition(10);
        player1.move(4, board.getSize());
        //player 1 moves to player 2's property
        game.playerOnSquare(player1, null);
        //rent and bankruptcy
        assertEquals(90, player1.getBalance());
        assertEquals(1050, player2.getBalance());
        assertNull(player1Property.getPropertyOwner());
    }

    @Test
    public void testBankruptcyWithZeroBalance() {
        // Create board with property
        List<Square> squares = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            squares.add(new Road("Road"));
        }
        Property property = new Property("Test road", 200, 10);
        squares.set(3, property);

        Board board = new Board(squares);

        // Player A owns the property
        Player playerA = new Player();
        playerA.setBalance(1000);
        property.setPropertyOwner(playerA);

        // Player B has £0 balance
        Player playerB = new Player();
        playerB.setBalance(0);
        playerB.setPosition(3);

        List<Player> players = new ArrayList<>();
        players.add(playerA);
        players.add(playerB);
        Game game = new Game(board, players);

        // Player B lands on property, must pay rent but has £0
        game.playerOnSquare(playerB, null);
        // Player B should go bankrupt (balance should be 0 or negative)
        //player B should lose their property to cover the missing rent
        assertEquals(playerA, property.getPropertyOwner());
        //check if player A srtill owns the property
    }

    @Test
    public void testBankruptcyRemovesPlayer() {
        // Create board with property
        List<Square> squares = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            squares.add(new Road("Road"));
        }
        Property property = new Property("Test Street", 100, 500);
        squares.set(3, property);
        Board board = new Board(squares);

        // Player A owns the property
        Player playerA = new Player();
        playerA.setBalance(1000);
        property.setPropertyOwner(playerA);

        // Player B has not enough money
        Player playerB = new Player();
        playerB.setBalance(100);
        playerB.setPosition(3);

        //Adding a playerC
        Player playerC = new Player();
        playerC.setBalance(1000);

        List<Player> players = new ArrayList<>();
        players.add(playerA);
        players.add(playerB);
        List<Player> player = new ArrayList<>();
        player.add(playerA);
        player.add(playerB);
        player.add(playerC);
        Game game = new Game(board, player);

        // Player B goes bankrupt
        game.playerOnSquare(playerB, null);

        // Player B should have negative or zero balance
        assertTrue(playerB.getBalance() <= 0);

        // Player B should not be the winner
        Player winner = game.getWinner();
        assertNotEquals(playerB, winner);
    }

    @Test
    public void testBankruptcySellsProperties() {
        // Create board with properties
        List<Square> squares = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            squares.add(new Road("Road"));
        }
        Property property1 = new Property("Property 1", 200, 10);
        Property property2 = new Property("Property 2", 300, 20);
        squares.set(2, property1);
        squares.set(5, property2);

        Board board = new Board(squares);

        // Player owns both properties
        Player player = new Player();
        player.setBalance(50);
        property1.setPropertyOwner(player);
        property2.setPropertyOwner(player);

        // Player A owns expensive property (rent is £100)
        Property testStreet = new Property("Test Street", 100, 100);
        squares.set(8, testStreet);
        Player playerA = new Player();
        playerA.setBalance(1000);
        testStreet.setPropertyOwner(playerA);

        player.setPosition(8);

        List<Player> players = new ArrayList<>();
        players.add(player);
        players.add(playerA);
        Game game = new Game(board, players);

        // Player lands on Test Street, can't afford rent
        // Should sell properties to pay rent
        game.playerOnSquare(player, null);

        // Check property1 was sold
        assertNull(property1.getPropertyOwner());

        // Check property2 is still owned by player
        assertEquals(player, property2.getPropertyOwner());

        // Check player has correct balance
        assertEquals(50, player.getBalance());
        // -50 + 100 = 50
    }

    @Test
    void testPassHomeWithPropertiesAndBonus() {
        List<Square> squares = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            squares.add(new Road("Road"));
            // Board with only one property at position 5, rest null

        }
        Property prop = new Property("Square 5", 150, 15);
        squares.set(5, prop);
        Board board = new Board(squares);

        Player player = new Player();
        player.setPosition(8);
        // Near the end

        prop.setPropertyOwner(player);
        // Player owns the property at position 5

        int initialBalance = player.getBalance();

        List<Player> players = List.of(player);
        Game game = new Game(board, players);

        game.forceNextDiceRoll(2);
        game.forceNextDiceRoll(2);
        // Force dice roll so player passes Home
        // 8 + 4 = 12 so wraps to 2

        game.playRounds(1, null);

        assertEquals(initialBalance + 200, player.getBalance());
        // Player should have received £200 bonus (rent/100 = 0 because rent is 15)
    }
}