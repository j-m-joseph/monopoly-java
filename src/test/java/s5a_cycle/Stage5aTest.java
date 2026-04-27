package s5a_cycle;

import com.cm6123.monopoly.Board;
import com.cm6123.monopoly.Game;
import com.cm6123.monopoly.Player;
import com.cm6123.monopoly.Property;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class Stage5aTest {
    @Test
    public void testCycle() {
        List<Property> squares = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            squares.add(null);
        }
        Board board = new Board(squares);
        //board should be created once after the loop is done
        Player player = new Player();
        //player obkect and store it in a variable
        List<Player> players = new ArrayList<>();
        //ArrayList of Player
        players.add(player);
        //add player to list
        Game game = new Game(board, players);
        //new game object
        player.setPosition(0);
        //set player pos to 0
        game.setCycleRaceSquare(12);
        //marks position 12 as a cycle race square
        player.setPosition(12);
        //set player's position to 12
        game.playerOnSquare(player);
        //cyclerace square logic
        assertTrue(player.getCycleRestriction());
        game.setNextDiceRoll(5);
        //return a 5 this is for testing
        game.playRounds(1);
        //next turn apply rule for the cycle square
        assertEquals(12, player.getPosition());
        //player's position is still 12 (unchamged)
    }

    @Test
    public void testCycleRoll() {
        List<Property> squares = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            squares.add(null);
        }
        Board board = new Board(squares);
        //board should be created once after the loop is done
        Player player = new Player();
        //player obkect and store it in a variable
        List<Player> players = new ArrayList<>();
        //ArrayList of Player
        players.add(player);
        //add player to list
        Game game = new Game(board, players);
        //new game object
        player.setPosition(0);
        //set player pos to 0
        game.setCycleRaceSquare(12);
        //marks position 12 as a cycle race square
        player.setPosition(12);
        //set player's position to 12
        game.playerOnSquare(player);
        //cyclerace square logic
        assertTrue(player.getCycleRestriction());
        game.setNextDiceRoll(2);
        //return a 2 this is for testing
        game.playRounds(1);
        //next turn apply rule for the cycle square
        assertEquals(14, player.getPosition());
        //player's position is 14 (updated)
        assertFalse(player.getCycleRestriction());
        //same thing but to test the low roll
    }
}
