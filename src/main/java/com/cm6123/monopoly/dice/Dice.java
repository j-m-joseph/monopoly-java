package com.cm6123.monopoly.dice;

import java.util.LinkedList;
import java.util.Queue;
//I am going to use queue to attempt to fix the dice rll order problem
//Queue is an interface
//LinkedList is a class that imlpements the behaviour
public class Dice {

  /**
   * The number of faces on this dice.
   */
  private Integer faces;

  /**
   * Construct a die with the given number of faces.
   * @param numberOfFaces the number of faces that the dice will have.  Dice rolls 1 to this number.
   */
  public Dice(final Integer numberOfFaces) {
    this.faces = numberOfFaces;
  }

  /**
   * This ensures the order of the die is considered.
   * Prevents overwritting instead storing multiple rolls and put them in order.
   */
  private Queue<Integer> forcedRolls = new LinkedList<>();
  //only diece can see and use the var

  /**
   * Roll to dice to get a value.
   * If a forced roll is set through setNextRoll(),the value is returned and cleared.
   * @return an integer between 1 and the number of faces inclusive.
   */
  public Integer roll() {
    //if the queue has no numbers
    System.out.println("roll() called | Queue size: " + forcedRolls.size());
    if (!forcedRolls.isEmpty()) {
      int result = forcedRolls.poll();
      //removes 1st num and returns it
      System.out.println("Forced roll used: " + result);
      //To confirm forced roll is being applied
      return result;
    }
    Double tempRoll = Math.ceil(Math.random() * faces.doubleValue());
    //if the queue is empty the code runs
    //generates random number between 1 and 6(faces)
    return tempRoll.intValue();
  }

  /**
   * Clears the forced roll value.
   * Allows the dice to return to ramdom rolls
   */
  public void clearForcedRoll() {
    forcedRolls.clear();
    //removes all values from the queue
  }

  /**
   * forced roll to return a wanted number (test).
   * @param roll the value to return on the next roll
   */
  public void setNextRoll(final Integer roll) {
    forcedRolls.offer(roll);
  }
  /**
   * Forces the next dice roll to be a specific value for testing.
   * Only works if test mode is enabled.
   * @param total the value to force
   */
  public void forceNextRoll(final int total) {
    forcedRolls.offer(total);
    //adds number to end of queue
    //empty queue becomes total
    System.out.println("Added forced roll: " + total + " | Queue now: " + forcedRolls);
  }
}
