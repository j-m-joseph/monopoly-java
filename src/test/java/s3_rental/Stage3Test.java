package s3_rental;

import com.cm6123.monopoly.Board;
import com.cm6123.monopoly.Game;
import com.cm6123.monopoly.Player;
import com.cm6123.monopoly.Property;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Stage3Test {
    @Test
    public void testPropertyRentalTransfers() {

        Property Coraline = new Property("Coraline Road", 100, 10);
        List<Property> squares = new ArrayList<>();
        squares.add(Coraline);
        for (int i = 0; i < 15; i++) {
            squares.add(null);
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
                game1.playerOnSquare(playerB);
                assertEquals(1010, playerA.getBalance());
                assertEquals(990, playerB.getBalance());
    }
    }
