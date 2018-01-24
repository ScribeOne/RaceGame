package model;

import controller.Settings;

/**
 * The GameModel saves data about the game, including the racecar.
 * It handles most of the calculations for the racegame.
 */
public class GameModel {

  private Car car;
  private Track track;
  private double oldVelocity;

  private boolean accelerate, steerLeft, steerRight, brake;

  /**
   * Creates a gameModel, that handles most of the actions
   */
  public GameModel() {
    car = initializeCar();
    track = initializeTrack();
    track.createObstacles(Settings.OBSTACLEAMOUNT);
    oldVelocity = 0;
  }


  private void calculateResistance() {

  }

  private double calculateAirResistance() {
    return Settings.AIRFACTOR * Settings.CARSURFACE * (Settings.AIRDENSITY / 2) * car.getVelocity()
        * car.getVelocity();
  }


  /**
   * Update the car control flags according to the key events for the current timeframe. Game controller should handle key events.
   */
  public void updateCarControl(boolean accelerate, boolean steerLeft, boolean steerRight,
      boolean brake) {
    this.accelerate = accelerate;
    this.steerLeft = steerLeft;
    this.steerRight = steerRight;
    this.brake = brake;
  }

  /**
   * Initialize the Track with default values in the Settings
   *
   * @return initialized track
   */
  private Track initializeTrack() {
    track = new Track(Settings.meterToPixel(Settings.INNERRADIUSX),
        Settings.meterToPixel(Settings.INNERRADIUSY),
        Settings.meterToPixel(Settings.OUTERRADIUSX), Settings.meterToPixel(Settings.OUTERRADIUSY));
    return track;
  }

  /**
   * update vectors of the car accodring to the pressed keys in the current timeframe.
   *
   * @param delta elapsed time for calculations
   */
  public void updateCar(double delta) {
    if (accelerate) {
      car.accelerateCar(Settings.ACCELERATIONVALUE);
    }
    if (brake) {
      if (car.isMoving()) {
        car.brake(Settings.BRAKEVALUE);
      } else {
        car.accelerateCar(Settings.BRAKEVALUE);
      }
    }
    if (steerRight) {
      car.steerRight();
    }
    if (steerLeft) {
      car.steerLeft();
    }
    car.moveCar(delta);
  }


  /**
   * Initializes a car with the initial values
   *
   * @return the initialized car
   */
  private Car initializeCar() {
    //initialize a new car and give it the init values set in the static variables
    car = new Car(Settings.initialPosition, Settings.INITIALDIRECTION);
    return car;
  }

  /**
   * Set the car back to starting position with direction west(180°) and velocity to zero.
   */
  public void resetCar() {
    car.setPosition(Settings.initialPosition);
    car.resetDirection();
    car.setVelocity(0);
    track.createObstacles(Settings.OBSTACLEAMOUNT);
  }

  public void toggleVelocity() {
    double buffer = car.getVelocity();
    car.setVelocity(oldVelocity);
    oldVelocity = buffer;
    if (car.getVelocity() != 0 && oldVelocity != 0) {
      oldVelocity = 0;
    }
  }

  public Track getTrack() {
    return track;
  }

  public Car getCar() {
    return car;
  }

  public Vector2D getCarPosition() {
    return car.getPosition();
  }

  public double getCarAngle() {
    return car.getAngle();
  }

}