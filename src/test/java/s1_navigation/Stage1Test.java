package com.cm6123.monopoly.s1_navigation;
import com.cm6123.monopoly.Board;
import com.cm6123.monopoly.Game;
import org.junit.jupiter.api.Test;
import com.cm6123.monopoly.Player;
import static org.junit.jupiter.api.Assertions.*;


import java.util.ArrayList;
import java.util.List;


public class Stage1Test {
    @Test
    public void testGameRuns() {
        Board board = new Board(16);
        Player player1 = new Player();
        Player player2 = new Player();
        //Creates 2 new players
        List<Player> playerList = new ArrayList<>();
        playerList.add(player1);
        playerList.add(player2);

        Game game = new Game(board, playerList);
        game.playRounds(10);
        //method playRounds
        //object game
        //paramaters 10
        //same as constructor order

        Player winner = game.getWinner();
        assertNotNull(winner);
    }
}
