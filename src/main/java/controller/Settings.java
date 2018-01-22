package controller;

import model.Vector2D;

/**
 * Class to hold all the game settings with final variables.
 */
public class Settings {

  //Main window, size in Pixel
  static public final int HEIGHT = 800;
  static public final int WIDTH = 1300;

  //2 Ellipses that define the race track, size in meters
  static public final double INNERRADIUSX = 45;
  static public final double INNERRADIUSY = 25;
  static public final double OUTERRADIUSX = 55;
  static public final double OUTERRADIUSY = 35;

  //size of the race car in meter
  static public final double CARWIDTH = 4.255;
  static public final double CARHEIGHT = 2.027;

  /**
   * initial position of the race car.
   */
  public static final Vector2D initialPosition = new Vector2D(420.0f, 230.0f);
  public static final Vector2D initialSpeed = new Vector2D(0.0f, 0.0f);

  //Methods to convert Pixel <-> Meter
  static public double meterToPixel(double input) {
    return input * 10;
  }

  static public double pixelToMeter(double input) {
    return input / 10;
  }

}
