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
  private Stopwatch stopwatch;

  private boolean accelerate, steerLeft, steerRight, brake, passedStart, passedCheckpoint;

  /**
   * Creates a gameModel, that handles most of the actions
   */
  public GameModel() {
    car = initializeCar();
    track = initializeTrack();
    track.createObstacles(Settings.OBSTACLEAMOUNT);
    oldVelocity = 0;
    stopwatch = new Stopwatch();
  }

  private void checkStartandFinish() {
   // System.out.println(Math.round(car.getPosition().getX())+ " | "+ Math.round(car.getPosition().getY()));
   // System.out.println(track.getFinish().getStartX() + " | " + track.getFinish().getStartY());
    //Check if start has been passed less than 1 sec ago
    if (stopwatch.getElapsedTime() < 1) {
      if (track.getFinish().contains(Math.round(car.getPosition().getX()),Math.round(car.getPosition().getY()))) {
        passedStart = true;
        stopwatch.start();
        System.out.println("passed start");
      }
    }
  }


  private void setNewVelocity(double delta) {
    double allForces = 0;

    if (accelerate) {
      allForces = Settings.engineForce - getForces() * delta;
    } else {
      allForces = -getForces() * delta;
    }
    double newVelocity = car.getVelocity() + allForces;
    //System.out.println("force: " + allForces);

    if (newVelocity > Settings.MAXSPEED) {
      car.setVelocity(Settings.MAXSPEED);
    } else if (Math.abs(newVelocity) < 1) {
      car.setVelocity(0);
    } else {
      car.setVelocity(car.getVelocity() + allForces);
    }

  }

  private double getForces() {
    return carFriction() + airResistance() / Settings.carWeigth;
  }

  private double carFriction() {
    if (track.isOnTrack(car.getPosition())) {
      return Settings.CONCRETERESISTANCE * Settings.normalForce;
    } else {
      return Settings.OFFROADRESISTANCE * Settings.normalForce;
    }
  }

  private double airResistance() {
    return Settings.AIRFACTOR * Settings.CARSURFACE * (Settings.AIRDENSITY * (1 / 2)) * car
        .getVelocity()
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
   * update attributes of the car according to the pressed keys in the current timeframe.
   *
   * @param delta elapsed time for calculations
   */
  public void updateCar(double delta) {
    setNewVelocity(delta);
    checkStartandFinish();
    if (brake) {
      car.brake(delta);
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
    car.setPosition(Settings.initialPosition);
    car.resetDirection();
    car.setVelocity(0);
    return car;
  }

  /**
   * Initialize the Track with default values in the Settings
   *
   * @return initialized track
   */
  private Track initializeTrack() {
    track = new Track(Settings.meterToPixel(Settings.INNERRADIUSX),
        Settings.meterToPixel(Settings.INNERRADIUSY),
        Settings.meterToPixel(Settings.OUTERRADIUSX),
        Settings.meterToPixel(Settings.OUTERRADIUSY));
    return track;
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