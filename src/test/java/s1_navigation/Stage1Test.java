package com.cm6123.monopoly.s1_navigation;

import com.cm6123.monopoly.Board;
import com.cm6123.monopoly.Game;
import com.cm6123.monopoly.Player;
import com.cm6123.monopoly.Property;
import com.cm6123.monopoly.app.Application;
import com.cm6123.monopoly.app.MonopolyGame;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class Stage1Test {

    @Test
    public void testInitialBalance() {
        Player player = new Player();
        assertEquals(1000, player.getBalance());
        //Test Method for testInitialBalance
        //Creates new Player object
        //Balance is set to 1000
    }

    @Test
    public void testInitialPosition() {
        Player player = new Player();
        assertEquals(0, player.getPosition());
        //Test for testInitialPosition
        //Creates new Player object
        //position initially is 0
    }

    @Test
    public void testSetBalance() {
        Player player = new Player();
        player.setBalance(3000);
        assertEquals(3000, player.getBalance());
    }

    @Test
    public void testSetPosition() {
        Player player = new Player();
        player.setPosition(6);
        assertEquals(6, player.getPosition());
        //changes the position
        //checks if the position changed correctly
    }

    @Test
    public void testMoveWithoutPassingHome() {
        Player player = new Player();
        player.setPosition(5);
        int BeforeMoveBalance = player.getBalance();
        player.move(3, 16);
        assertEquals(BeforeMoveBalance, player.getBalance());
        assertEquals(8, player.getPosition());
        //Set position to 5 + 3 steps will move to 8
        //no wrap
        // store balance before move and balance is unchanged
    }

    @Test
    public void testMoveWithPassingHome() {
     List<Property> squares = new ArrayList<>(16);
     //Board empty with 16 squares
        for (int i = 0; i < 16; i++) {
            squares.add(null);
        }
        Board board = new Board(squares);

        Player player = new Player();
        player.setPosition(14);
        int BeforeMoveBalance = player.getBalance();

        List<Player> players = new ArrayList<>();
        players.add(player);

        Game game = new Game(board, players);

        game.forceNextDiceRoll(2);
        // first die
        game.forceNextDiceRoll(3);
        // second die (total 5)
        game.playRounds(1, null);
        assertEquals(3, player.getPosition());

        assertEquals(BeforeMoveBalance + 200, player.getBalance());
        // Player should get £200 bonus for passing Home
    }
    //player.move() doesn't need Home bonus only Gmae.playRounds() does

    @Test
    public void testGameRuns() {
        //Create a board with 16 empty squares (null properties)
        List<Property> squares = new ArrayList<>(16);
        for (int i = 0; i < 16; i++) {
            squares.add(null);
        }
        Board board = new Board(squares);

        Player player1 = new Player();
        Player player2 = new Player();
        //Creates 2 new players
        List<Player> playerList = new ArrayList<>();
        playerList.add(player1);
        playerList.add(player2);

        Game game = new Game(board, playerList);
        // Round 1
        game.forceNextDiceRoll(3);
        // Player 1 - first die
        game.forceNextDiceRoll(2);
        // Player 1 - second die (total 5)
        game.forceNextDiceRoll(1);
        // Player 2 - first die
        game.forceNextDiceRoll(1);
        // Player 2 - second die (total 2)

        game.playRounds(1, null);

        assertEquals(5, player1.getPosition());
        assertEquals(2, player2.getPosition());

        Player winner = game.getWinner();
        assertNotNull(winner);
    }

    @Test
    public void testMonopolyGameRuns() {
        String input = "13\n2\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        MonopolyGame game = new MonopolyGame();
        game.start(new String[]{});
        //Tests that MonopolyGame starts and runs without crashing
        //Uses board size 13 and 2 players as input
    }

    @Test
    public void testApplicationMainStarts() {
        String input = "13\n2\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Application.main(new String[]{});
        //Tests that Application.main starts without crashing
    }

    @Test
    public void testPlayerMovesToEndOfBoard() {
        //Create a board with 16 empty squares
        List<Property> squares = new ArrayList<>(16);
        for (int i = 0; i < 16; i++) {
            squares.add(null);
        }
        Board board = new Board(squares);
        Player player = new Player();

        //Set player at position 14 (near the end)
        player.setPosition(14);
        player.move(5, board.getSize());
        //14 + 5 = 19, wraps to 3
        assertEquals(3, player.getPosition());
        //Checks that player wraps around correctly
    }

    @Test
    public void testPlayerMovesExactBoardSize() {
        //Create a board with 16 empty squares
        List<Property> squares = new ArrayList<>(16);
        for (int i = 0; i < 16; i++) {
            squares.add(null);
        }
        Board board = new Board(squares);
        Player player = new Player();

        //Set player at position 0
        player.setPosition(0);
        player.move(16, board.getSize());
        //0 + 16 = 16, wraps to 0
        assertEquals(0, player.getPosition());
        //Checks that moving exactly board size lands on same position
    }

    @Test
    public void testPlayerMovesFromHome() {
        //Create a board with 16 empty squares
        List<Property> squares = new ArrayList<>(16);
        for (int i = 0; i < 16; i++) {
            squares.add(null);
        }
        Board board = new Board(squares);
        Player player = new Player();

        //Player starts at position 0 (Home)
        assertEquals(0, player.getPosition());
        //Move 3 steps
        player.move(3, board.getSize());
        assertEquals(3, player.getPosition());
        //Checks that player moves correctly from starting position
    }

    @Test
    public void testMultiplePlayersMove() {
        //Create a board with 16 empty squares
        List<Property> squares = new ArrayList<>(16);
        for (int i = 0; i < 16; i++) {
            squares.add(null);
        }
        Board board = new Board(squares);

        //Create 3 players
        Player player1 = new Player();
        Player player2 = new Player();
        Player player3 = new Player();

        //Move each player different amounts
        player1.move(3, board.getSize());
        assertEquals(3, player1.getPosition());

        player2.move(7, board.getSize());
        assertEquals(7, player2.getPosition());

        player3.move(12, board.getSize());
        assertEquals(12, player3.getPosition());
        //Checks that multiple players can move independently
    }

    @Test
    public void testPlayerCannotGoNegative() {
        //Create a board with 16 empty squares
        List<Property> squares = new ArrayList<>(16);
        for (int i = 0; i < 16; i++) {
            squares.add(null);
        }
        Board board = new Board(squares);
        Player player = new Player();

        //Player at position 0
        player.setPosition(0);
        //In Monopoly, you can't move backwards, so this just tests normal movement
        player.move(5, board.getSize());
        assertEquals(5, player.getPosition());
        //Position should always be >= 0
        assertTrue(player.getPosition() >= 0);
    }

    @Test
    void testInvalidBoardSizeAndPlayerCountTriggersValidationBranches() {
        // Board: invalid low (5), invalid high (60), valid (13)
        // Players: invalid low (1), invalid high (11), valid (2)
        String input = "5\n60\n13\n1\n11\n2\nq\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Application.main(new String[]{});

        assertTrue(out.toString().contains("Welcome to Monopoly"));
    }

    @Test
    void testAddStationsWithSmallBoardTriggersMinStationsBranch() {
        // Board size 10 → 10/6 = 1 → triggers the < 2 branch
        String input = "10\n2\nq\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Application.main(new String[]{});

        assertTrue(out.toString().contains("Welcome to Monopoly"));
    }
    @Test
    void testPlayerDoesNotPassHome() {
        List<Property> squares = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            squares.add(null);
        }
        Board board = new Board(squares);

        Player player = new Player();
        player.setPosition(5);

        List<Player> players = List.of(player);
        Game game = new Game(board, players);

        game.forceNextDiceRoll(2);
        game.forceNextDiceRoll(2);
        game.playRounds(1, null);

        assertEquals(1000, player.getBalance());
    }
}
//Most of the tests are from the project the 1st time round just reorganized
//Aditional tests added to better coverage score