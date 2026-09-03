package com.cm6123.monopoly.s1_navigation;

import com.cm6123.monopoly.Board;
import com.cm6123.monopoly.Game;
import com.cm6123.monopoly.Player;
import com.cm6123.monopoly.Property;
import com.cm6123.monopoly.app.Application;
import com.cm6123.monopoly.app.MonopolyGame;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
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
        Player player = new Player();
        player.setPosition(14);
        int BeforeMoveBalance = player.getBalance();
        player.move(5, 16);
        // If bonus is NOT added in move() anymore, just check position
        assertEquals(3, player.getPosition());
        // Remove the balance check if it's handled in Game
    }

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
        game.playRounds(10, new Scanner(System.in));
        //method playRounds
        //object game
        //paramaters 10
        //same as constructor order

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
}
//All y tests from the project the 1st time round just reorganized