package s5b_station;

import com.cm6123.monopoly.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Stage5bTest {
    @Test
    public void testUndergroundStation() {
        UndergroundStation station = new UndergroundStation(7);
        //underground pos object position 7
        assertEquals(7, station.getStationPosition());
        //confirms position
    }

    @Test
    public void testBoardUndergroundStation() {
        List<Property> squares = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            squares.add(null);
            //create a board with 16 empty squares
            //2 stations that can be added to the board
            //return objects to be at 2 positions retur true
            //the other positions return false
        }

        Board board = new Board(squares);
        //new board object
        UndergroundStation stationA = new UndergroundStation(7);
        UndergroundStation stationB = new UndergroundStation(14);
        //2 underground station positions
        board.addUndergroundStation(stationA);
        //station at square 7
        board.addUndergroundStation(stationB);
        //station at square 14
        assertTrue(board.getUndergroundStation(7));
        assertTrue(board.getUndergroundStation(14));
        //there are stations at these squares so the board should say true
        assertFalse(board.getUndergroundStation(3));
        //there is no station at square 3 so the soard should say false
    }

    @Test
    public void testPassHomeAndTotal() {
        List<Property> squares = new ArrayList<>();
        //holds 16 items null for roads
        for (int i = 0; i < 16; i++) {
            //loop runs 16 times
            squares.add(null);
        }
        Board board = new Board(squares);
        UndergroundStation stationA = new UndergroundStation(14);
        UndergroundStation stationB = new UndergroundStation(2);
        board.addUndergroundStation(stationA);
        board.addUndergroundStation(stationB);
        Player player = new Player();
        player.setBalance(1000);
        player.setPosition(14);
        List<Player> players = new ArrayList<>();
        //empty list to hold player objects
        players.add(player);
        //puts player into list
        Game game = new Game(board, players);
        //creates a new game
        game.playerOnSquare(player);
        //should move player to square 2 and add total balance
        assertEquals(2, player.getPosition());
        //checks player was moved to square 2
        assertEquals(1200, player.getBalance());
        //checks the alance increased by 200
        //no properties for this example
    }


}
