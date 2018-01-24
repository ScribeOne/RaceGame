package controller;

import javafx.scene.paint.Color;
import model.Vector2D;

/**
 * Class to hold all the game settings with final variables.
 */
public class Settings {

  //Main window, size in Pixel
  static public final int HEIGHT = 800;
  static public final int WIDTH = 1300;

  //Colors
  public static final Color STREETCOLOR = Color.rgb(25, 25, 25);
  public static final Color BACKGROUND = Color.rgb(101, 67, 33);
  public static final Color OBSTACLECOLOR = Color.rgb(109, 9, 9);
  public static final Color PAUSECOLOR = Color.rgb(0, 0, 0, 0.5);

  //2 Ellipses that define the race track, size in meters
  static public final double INNERRADIUSX = 45;
  static public final double INNERRADIUSY = 25;
  static public final double OUTERRADIUSX = 55;
  static public final double OUTERRADIUSY = 35;

  //size of the race car in meter
  static public final double CARWIDTH = 4.255;
  static public final double CARHEIGHT = 2.027;

  // initial position of the race car
  public static final Vector2D initialPosition = new Vector2D(660.0f, 90.0f);
  public static final Vector2D INITIALDIRECTION = new Vector2D(-1.0f, 0.0f);

  /**
   * Needed to define where zero degree is. (1,0) represents the x-Axis. Degree is ascending counter-clockwise.
   */
  public static final Vector2D ZERODEGREES = new Vector2D(1, 0);

  //Path to the car image
  public static final String CARPATH = "../resources/raceCarBlue.png";

  // acceleration and brake values
  public static final double ACCELERATIONVALUE = 2;
  public static final double BRAKEVALUE = -2;

  // maximum speed of the car
  public static final double MAXSPEED = 200;

  // Air Resistance Settings
  public static final double AIRDENSITY = 1.2041;
  public static final double CARSURFACE = 2.19;
  public static final double AIRFACTOR = 0.28;

  //Obstacle Settings
  public static final double OBSTACLERADIUS = 10;
  public static final int OBSTACLEAMOUNT = 20;

  //Methods to convert Pixel <-> Meter
  static public double meterToPixel(double input) {
    return input * 10;
  }

  static public double pixelToMeter(double input) {
    return input / 10;
  }

}
