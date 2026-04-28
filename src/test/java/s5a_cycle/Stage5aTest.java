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
    @Test
    public void testCycleWithFour() {
        //when the dice rolls 4
        List<Property> squares = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            squares.add(null);
        }
            Board board = new Board(squares);
            Player player = new Player();
            //new player with default balance and pos at 0
            player.setPosition(2);
            //moves the player to square 5
            player.setCycleRestriction(true);
            //the player lansed on a cycle square on their previous turn
            List<Player> players = List.of(player);
            //List with just the player
            Game game = new Game(board, players);
            game.setCycleRaceSquare(10);
            //the game knows that square 5 is cycle square and if the player alands on it execute the function
            game.setNextDiceRoll(4);
            //not random for test purpouse returns 4
            game.playRounds(1);
            //one round and there is only one player for test
            assertEquals(2, player.getPosition());
            //checks that the player's pisition is 5
            assertFalse(player.getCycleRestriction());
            //after turn player doesn't have the cycle restriction
        }
    }
