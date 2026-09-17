package s3_rental;

import com.cm6123.monopoly.Board;
import com.cm6123.monopoly.Game;
import com.cm6123.monopoly.Player;
import com.cm6123.monopoly.Property;
import com.cm6123.monopoly.Square;
import com.cm6123.monopoly.Road;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class Stage3Test {
    @Test
    public void testPropertyRentalTransfers() {

        Property Coraline = new Property("Coraline Road", 100, 10);
        List<Square> squares = new ArrayList<>();
        squares.add(Coraline);
        for (int i = 0; i < 15; i++) {
            squares.add(new Road("Road"));
        }
        Board board = new Board(squares);
        Player playerA = new Player();
        playerA.setBalance(1000);
        Coraline.setPropertyOwner(playerA);

        Player playerB = new Player();
        playerB.setBalance(1000);
        playerB.setPosition(0);
        playerB.setPosition(0);
        Game game1 = new Game(board, List.of(playerA, playerB));
        game1.playerOnSquare(playerB, null);
        assertEquals(1010, playerA.getBalance());
        assertEquals(990, playerB.getBalance());
    }

    @Test
    public void testLandOnOwnProperty() {
        // Create property
        Property property = new Property("My Street", 100, 10);
        List<Square> squares = new ArrayList<>();
        squares.add(property);
        for (int i = 0; i < 15; i++) {
            squares.add(new Road("Road"));
        }
        Board board = new Board(squares);

        // Player owns the property
        Player player = new Player();
        player.setBalance(1000);
        player.setPosition(0);
        property.setPropertyOwner(player);

        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        Game game = new Game(board, playerList);

        // Player lands on their own property
        game.playerOnSquare(player, null);

        // Balance should not change (player doesn't pay rent to themselves)
        assertEquals(1000, player.getBalance());
    }

    @Test
    public void testNotEnoughMoneyForRent() {
        // Create expensive property
        Property property = new Property("Test Street", 100, 500);
        List<Square> squares = new ArrayList<>();
        squares.add(property);
        for (int i = 0; i < 15; i++) {
            squares.add(new Road("Road"));
        }
        Board board = new Board(squares);

        // Player A owns the property
        Player playerA = new Player();
        playerA.setBalance(1000);
        property.setPropertyOwner(playerA);

        // Player B doesn't have enough money for rent
        Player playerB = new Player();
        playerB.setBalance(100);
        playerB.setPosition(0);

        List<Player> playerList = new ArrayList<>();
        playerList.add(playerA);
        playerList.add(playerB);
        Game game = new Game(board, playerList);

        // Player B lands on property, can't afford rent
        game.playerOnSquare(playerB, null);

        // Player B should go bankrupt (balance becomes negative or 0)
        assertTrue(playerB.getBalance() < 100);
    }
    @Test
    public void testAccurateRentalAmount() {
        //creates property with details
        Property property = new Property("Test Street", 100, 25);
        List<Square> squares = new ArrayList<>();
        squares.add(property);
        for (int i = 0; i < 15; i++) {
            squares.add(new Road("Road"));
        }
        Board board = new Board(squares);
        // Player A owns the property
        Player playerA = new Player();
        playerA.setBalance(1000);
        property.setPropertyOwner(playerA);

        // Player B lands on it
        Player playerB = new Player();
        playerB.setBalance(1000);
        playerB.setPosition(0);

        List<Player> playerList = new ArrayList<>();
        playerList.add(playerA);
        playerList.add(playerB);
        Game game = new Game(board, playerList);

        int beforeA = playerA.getBalance();
        int beforeB = playerB.getBalance();

        game.playerOnSquare(playerB, null);

        //playerA should get 25 (rental amount)
        assertEquals(beforeA + 25, playerA.getBalance());

        //playerB should lose 25 (gives to playerA)
        assertEquals(beforeB - 25, playerB.getBalance());

    }
}
