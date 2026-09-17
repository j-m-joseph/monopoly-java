package s5a_cycle;

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

public class Stage5aTest {
    @Test
    public void testCycle() {
        List<Square> squares = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            squares.add(new Road("Road"));
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
        game.playerOnSquare(player, null);
        //cyclerace square logic
        assertTrue(player.getCycleRestriction());

        game.setNextDiceRoll(2);
        game.setNextDiceRoll(3);
        //return a 5 this is for testing
        //I can use set or force they do the same thing
        game.playRounds(1, null);
        //next turn apply rule for the cycle square
        assertEquals(12, player.getPosition());
        //player's position is still 12 (unchamged)
    }

    @Test
    public void testCycleRoll() {
        List<Square> squares = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            squares.add(new Road("Road"));
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
        game.playerOnSquare(player, null);
        //cyclerace square logic
        assertTrue(player.getCycleRestriction());
        game.forceNextDiceRoll(1);
        //FORCES 1st die to 1
        game.forceNextDiceRoll(1);
        //FORCES 2nd die to 1
        //return a 2 this is for testing
        game.playRounds(1, null);
        //next turn apply rule for the cycle square
        assertEquals(14, player.getPosition());
        //player's position is 14 (updated)
        assertFalse(player.getCycleRestriction());
        //same thing but to test the low roll
    }

    @Test
    public void testCycleWithFour() {
        //when the dice rolls 4
        List<Square> squares = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            squares.add(new Road("Road"));
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
        game.setNextDiceRoll(2);
        // Force first die to 2
        game.setNextDiceRoll(2);
        // Force second die to 2
        //not random for test purpouse returns 4
        //is meant to miss turn
        game.playRounds(1, null);
        //one round and there is only one player for test
        assertEquals(2, player.getPosition());
        //checks that the player's pisition is 5
        assertFalse(player.getCycleRestriction());
        //after turn player doesn't have the cycle restriction
    }

    @Test
    public void testCycleRestrictionWithMultiplePlayers() {
        // Create board with 16 empty squares
        List<Square> squares = new ArrayList<>(16);
        for (int i = 0; i < 16; i++) {
            squares.add(new Road("Road"));
        }
        Board board = new Board(squares);
        // Create 2 players
        Player player1 = new Player();
        Player player2 = new Player();
        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        Game game = new Game(board, players);
        // Set position 3 as cycle square
        game.setCycleRaceSquare(3);
        // Both players land on cycle square
        player1.setPosition(3);
        player2.setPosition(3);
        // Force dice roll to 6 (high roll - should restrict movement)
        game.forceNextDiceRoll(3);
        game.forceNextDiceRoll(3);
        // Play 1 round
        game.playRounds(1, null);
        // Both players should NOT have moved
        assertEquals(3, player1.getPosition());
        assertEquals(3, player2.getPosition());
    }

    @Test
    public void testCycleRestrictionRemovedAfterTurun() {
        // Create board with 16 empty squares
        List<Square> squares = new ArrayList<>(16);
        for (int i = 0; i < 16; i++) {
            squares.add(new Road("Road"));
        }
        Board board = new Board(squares);
        Player player = new Player();
        List<Player> players = new ArrayList<>();
        players.add(player);
        Game game = new Game(board, players);
        // Set position 3 as cycle square
        game.setCycleRaceSquare(3);
        // Player lands on cycle square
        player.setPosition(3);
        //1st turn and restricyion applies
        game.forceNextDiceRoll(3);
        game.forceNextDiceRoll(3);
        game.playRounds(1, null);
        // Player should not have moved
        assertEquals(3, player.getPosition());
        // Player should have restriction flag set
        assertTrue(player.getCycleRestriction());
        //2nd turn and restriction removed
        game.forceNextDiceRoll(1);
        game.forceNextDiceRoll(2);
        game.playRounds(1, null);
        // Player should have moved
        assertNotEquals(3, player.getPosition());
    }

    @Test
    public void testNoCycleSquareOnPropertySquare() {
        // Create board with 16 squares
        List<Square> squares = new ArrayList<>(16);
        for (int i = 0; i < 16; i++) {
            squares.add(new Road("Road"));
        }
        // Add a property at position 3
        Property property = new Property("Test Street", 100, 10);
        squares.set(3, property);
        Board board = new Board(squares);
        Player player = new Player();
        List<Player> players = new ArrayList<>();
        players.add(player);
        Game game = new Game(board, players);
        game.setCycleRaceSquare(3);
        //try to set cycle square on property square
        assertFalse(game.getCycleRacePosition(3));
        //property priority
    }

    @Test
    public void testCycleSquareCannotBeSetHome() {
        // Create board with 16 empty squares
        List<Square> squares = new ArrayList<>(16);
        for (int i = 0; i < 16; i++) {
            squares.add(new Road("Road"));
        }
        Board board = new Board(squares);
        Player player = new Player();
        List<Player> players = new ArrayList<>();
        players.add(player);
        Game game = new Game(board, players);
        game.setCycleRaceSquare(0);
        //tries to set cycle square at home
        assertFalse(game.getCycleRacePosition(0));
        //home can't be cycle square
    }

    @Test
    public void testLowRollAllowsMovementFromCycle() {
        // Create board with 16 empty squares
        List<Square> squares = new ArrayList<>(16);
        for (int i = 0; i < 16; i++) {
            squares.add(new Road("Road"));
        }
        Board board = new Board(squares);
        Player player = new Player();
        List<Player> players = new ArrayList<>();
        players.add(player);
        Game game = new Game(board, players);
        // Set position 3 as cycle square
        game.setCycleRaceSquare(3);
        // Player lands on cycle square
        player.setPosition(3);
        // Force roll of 3 (should allow movement)
        game.forceNextDiceRoll(1);
        game.forceNextDiceRoll(2);
        game.playRounds(1, null);
        // Player should have moved (roll 3 or less allows movement)
        assertNotEquals(3, player.getPosition());
    }

    @Test
    void testCycleRestrictionRollExactly4() {
        List<Square> squares = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            squares.add(new Road("Road"));
        }
        Board board = new Board(squares);

        Player player = new Player();
        player.setCycleRestriction(true);
        player.setPosition(5);

        List<Player> players = List.of(player);
        Game game = new Game(board, players);

        game.forceNextDiceRoll(2);
        game.forceNextDiceRoll(2);
        game.playRounds(1, null);

        assertEquals(5, player.getPosition());
    }
}
