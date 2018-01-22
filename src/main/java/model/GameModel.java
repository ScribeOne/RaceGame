package model;

import controller.Settings;

/**
 * The GameModel saves data about the game, including the racecar.
 * It handles most of the calculations for the racegame.
 */
public class GameModel {

  /**
   * The car that is driven on the racetrack
   */
  private Car car;

  public Track track;


  /**
   * Creates a gameModel, that handles most of the actions
   */
  public GameModel() {
    //initialize Car, default data in GameView
    car = initializeCar();
    System.out.println(
        "Initial position: [" + car.getPosition().getX() + "|" + car.getPosition().getY() + "]");
    track = initializeTrack();
  }

  /**
   * Initialize the Track with default values in the Settings
   *
   * @return initialized track
   */
  private Track initializeTrack() {
    track = new Track(meterToPixel(Settings.INNERRADIUSX), meterToPixel(Settings.INNERRADIUSY),
        meterToPixel(Settings.OUTERRADIUSX), meterToPixel(Settings.OUTERRADIUSY));
    System.out.println(meterToPixel(Settings.INNERRADIUSX) + " | " + meterToPixel(Settings.INNERRADIUSY)+ " | " +
        meterToPixel(Settings.OUTERRADIUSX)+ " | " + meterToPixel(Settings.OUTERRADIUSY));
    return track;
  }


  public void update(double timeDifferenceInSeconds) {
    //car.moveCar(timeDifferenceInSeconds);
    //System.out.println("new Position: [" + getCarPosition().getX() + "|" + getCarPosition().getY() + "]");
  }


  public Vector2D getCarPosition() {
    return car.getPosition();
  }


  /**
   * Initializes a car with the initial values
   *
   * @return the initialized car
   */
  private Car initializeCar() {
    //initialize a new car and give it the init values set in the static variables
    car = new Car(Settings.initialPosition, Settings.initialSpeed);
    return car;
  }

  //Methods to convert Pixel <-> Meter
  private double meterToPixel(double input) {
    return input * 10;
  }

  private double pixelToMeter(double input) {
    return input / 10;
  }

}