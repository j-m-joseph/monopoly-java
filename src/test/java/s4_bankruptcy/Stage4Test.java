package s4_bankruptcy;

import com.cm6123.monopoly.Board;
import com.cm6123.monopoly.Game;
import com.cm6123.monopoly.Player;
import com.cm6123.monopoly.Property;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertNull;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Stage4Test {
    @Test
    public void testplayerBankruptcyRecovery() {
        List<Property> squares = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            squares.add(null);
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
        game.playerOnSquare(player1);
        //rent and bankruptcy
        assertEquals(90, player1.getBalance());
        assertEquals(1050, player2.getBalance());
        assertNull(player1Property.getPropertyOwner());
    }

    }

