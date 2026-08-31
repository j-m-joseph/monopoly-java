package com.cm6123.monopoly.s1_navigation;
import com.cm6123.monopoly.Board;
import com.cm6123.monopoly.Game;
import com.cm6123.monopoly.Property;
import org.junit.jupiter.api.Test;
import com.cm6123.monopoly.Player;
import static org.junit.jupiter.api.Assertions.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Stage1Test {
    @Test
    public void testGameRuns() {
        List<Property> squares = new ArrayList<>(16);
        for (int i = 0; i < 16; i++) {
            squares.add(null);
        }
        Player player1 = new Player();
        Player player2 = new Player();
        //Creates 2 new players
        List<Player> playerList = new ArrayList<>();
        playerList.add(player1);
        playerList.add(player2);

        Board board = new Board(squares);
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
    public void testGetWinnerSameBalance() {
        //what happens when the game is over and 2 players have the samebalance
        List<Property> squares = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            squares.add(null);
        }
            Board board = new Board(squares);
            Player jack = new Player();
            jack.setBalance(100);
            //Jack has 100 monopoly
            Player jill = new Player();
            jill.setBalance(100);
            //Jill has 100 monopoly
            List<Player>players = List.of(jack,jill);
            //Put Jack and Jill in a list
            Game game = new Game(board, players);
            Player winner = game.getWinner();
            //call get winner
            assertNotNull(winner);
            //make sure winner is returned
        }
    }
