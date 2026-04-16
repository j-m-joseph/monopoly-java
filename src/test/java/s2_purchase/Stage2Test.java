package s2_purchase;

import com.cm6123.monopoly.Board;
import com.cm6123.monopoly.Player;
import com.cm6123.monopoly.Property;
import com.cm6123.monopoly.Game;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
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

        game.playerOnSquare(player);
        assertEquals(980, player.getBalance());
        assertSame(player, squares.get(0).getPropertyOwner());
    }
}
