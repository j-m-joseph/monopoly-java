package com.cm6123.monopoly.dice;

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
   * I want it to hold a specific dice roll value to test both outcomes.
   */
  private Integer forcedRoll = null;
  //only dice class will see the var
  // initially no forced roll is set
  //can't be final it needs to be changeable

  /**
   * Roll to dice to get a value.
   * If a forced roll is set through setNextRoll(),the value is returned and cleared.
   * @return an integer between 1 and the number of faces inclusive.
   */
  public Integer roll() {
    if (this.forcedRoll != null) {
      int result = forcedRoll;
      //this.forcedRoll = null;
      System.out.println("roll() called. forcedRoll = " + this.forcedRoll);
      //don't clear forced roll to attempt to correct logic
      System.out.println("Forced roll used: " + result);
      //To confirm forced roll is being applied
      return result;
    }
    Double tempRoll = Math.ceil(Math.random() * faces.doubleValue());
    return tempRoll.intValue();
  }

  /**
   * Clears the forced roll value.
   * Allows the dice to return to ramdom rolls
   */
  public void clearForcedRoll() {
    this.forcedRoll = null;
  }

  /**
   * forced roll to return a wanted number (test).
   * @param roll the value to return on the next roll
   */
  public void setNextRoll(final Integer roll) {
    this.forcedRoll = roll;
  }
  /**
   * Forces the next dice roll to be a specific value for testing.
   * Only works if test mode is enabled.
   * @param total the value to force
   */
  public void forceNextRoll(final int total) {
    this.forcedRoll = total;
  }


}
