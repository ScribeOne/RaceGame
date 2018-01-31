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
  public static final Color BACKGROUND = Color.rgb(155, 118, 83);
  public static final Color OBSTACLECOLOR = Color.rgb(109, 9, 9);
  public static final Color PAUSECOLOR = Color.rgb(0, 0, 0, 0.5);
  public static final Color INFOCOLOR = Color.rgb(0, 0, 0);

  //2 Ellipses that define the race track, size in meters
  public static final double INNERRADIUSX = 45;
  public static final double INNERRADIUSY = 25;
  public static final double OUTERRADIUSX = 55;
  public static final double OUTERRADIUSY = 35;

  //size of the race car in meter
  public static final double CARWIDTH = 4.255;
  public static final double CARHEIGHT = 2.027;

  // initial position of the race car
  public static final Vector2D initialPosition = new Vector2D(
      650 - (meterToPixel(Settings.CARWIDTH / 2)) - 10, 100.0f);
  public static final Vector2D INITIALDIRECTION = new Vector2D(1.0f, 0.0f);

  /**
   * Needed to define where zero degree is. (1,0) represents the x-Axis. Degree is ascending counter-clockwise.
   */
  public static final Vector2D ZERODEGREES = new Vector2D(1, 0);

  //Path to the car image
  public static final String CARPATH = "/images/raceCarOrange.png";

  public static final String EXPLOSIONPATH = "/images/Explosion.png";

  //Path to style Files
  public static final String MENUCSS = "/style/menu.css";
  public static final String GAMECSS = "/style/game.css";
  /*
   * Sound under Creative Common Licence from
   * https://freesound.org/people/willybilly1984/sounds/345335/
   */
  public static final String CARENGINESOUND = "/sound/carEngine.mp3";
  public static final String ENGINERUNNING = "/sound/runningEngine.mp3";
  public static final String INTRO = "/sound/Intro.mp3";
  public static final String EXPOSIONSOUND = "/sound/Explosion.mp3";

  // acceleration and brake values
  //public static final double ACCELERATIONVALUE = 200;
  public static final double BRAKEVALUE = -80;

  public static final double CRASHTHRESHOLD = 100;

  // Physic values
  public static final double normalForce = 9.81;
  public static final double engineForce = 2;

  // maximum speed of the car
  public static final double MAXSPEED = 200;
  public static final double carWeigth = 12500;

  // Air Resistance Settings
  public static final double AIRDENSITY = 1.2041;
  public static final double CARSURFACE = 2.19;
  public static final double AIRFACTOR = 0.28;

  //Surface Resistance
  public static final double CONCRETERESISTANCE = 0.015;
  public static final double OFFROADRESISTANCE = 0.05;

  //Obstacle Settings
  public static final double OBSTACLERADIUS = 15;
  public static final int OBSTACLEAMOUNT = 5;

  //Methods to convert Pixel <-> Meter
  static public double meterToPixel(double input) {
    return input * 10;
  }

  static public double pixelToMeter(double input) {
    return input / 10;
  }

}
