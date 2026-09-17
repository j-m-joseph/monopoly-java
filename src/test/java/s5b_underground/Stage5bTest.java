package s5b_underground;

import com.cm6123.monopoly.Board;
import java.util.ArrayList;
import java.util.List;
import com.cm6123.monopoly.Property;
import com.cm6123.monopoly.Road;
import com.cm6123.monopoly.Square;
import com.cm6123.monopoly.Game;
import com.cm6123.monopoly.Player;
import com.cm6123.monopoly.UndergroundStation;
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
            List<Square> squares = new ArrayList<>();
            for (int i = 0; i < 16; i++) {
                squares.add(new Road("Road"));
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
            List<Square> squares = new ArrayList<>();
            //holds 16 items null for roads
            for (int i = 0; i < 16; i++) {
                //loop runs 16 times
                squares.add(new Road("Road"));
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
            game.playerOnSquare(player, null);
            //should move player to square 2 and add total balance
            assertEquals(2, player.getPosition());
            //checks player was moved to square 2
            assertEquals(1200, player.getBalance());
            //checks the alance increased by 200
            //no properties for this example
        }

        @Test
        public void testGetToOtherStationsWithThreeStations() {
            List<Square> squares = new ArrayList<>();
            for (int i = 0; i < 16; i++) {
                squares.add(new Road("Road"));
            }
            Board board = new Board(squares);
            board.addUndergroundStation(new UndergroundStation(4));
            //station at position 4
            board.addUndergroundStation(new UndergroundStation(9));
            //station at position 9
            board.addUndergroundStation(new UndergroundStation(13));
            //station at position 13
            for (int i = 0; i < 100; i++) {
                int other = board.getOtherStation(4);
                assertNotEquals(4, other);
                //must not resturn pos 4
                assertTrue(List.of(9, 13).contains(other));
                //Verifies TRUE other has to be 9 or 13
            }
        }

        @Test
        public void testStationTeleportNoHomeBonusWhenNotPassingHome() {
            // Create board with 16 squares
            List<Square> squares = new ArrayList<>(16);
            for (int i = 0; i < 16; i++) {
                squares.add(new Road("Road"));
            }
            Board board = new Board(squares);
            // Stations at 2 and 10 (2->10 does NOT pass Home)
            board.addUndergroundStation(new UndergroundStation(2));
            board.addUndergroundStation(new UndergroundStation(10));
            Player player = new Player();
            player.setPosition(2);
            int initialBalance = player.getBalance();
            List<Player> players = new ArrayList<>();
            players.add(player);
            Game game = new Game(board, players);
            // Player lands on station at position 2
            game.playerOnSquare(player, null);
            // Player should NOT have received Home bonus
            assertEquals(initialBalance, player.getBalance());
        }
    }
