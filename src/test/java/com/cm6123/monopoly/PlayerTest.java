package com.cm6123.monopoly;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
//Like a file path
//Java organizes code into packages
//tells Java what @Test means and substitution

public class PlayerTest {
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
    public void testMoveWrapsCorrectly() {
        Player player = new Player();
        player.setPosition(14);
        int BeforeMoveBalance = player.getBalance();
        player.move(5, 16);
        assertEquals(BeforeMoveBalance, player.getBalance());
        assertEquals(3, player.getPosition());
        //Set position to 14 + 5 steps will move to square 3
        //wraps past Home
        // store balance before move
    }
}
