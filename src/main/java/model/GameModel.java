package model;


import controller.Settings;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * The GameModel saves data about the game, including the racecar.
 * It handles most of the calculations for the racegame.
 */
public class GameModel {

  private Car car;
  private Track track;
  private double oldVelocity;
  private boolean accelerate, steerLeft, steerRight, brake, passedStart, passedCheckpoint, won, alive;

  /**
   * Creates a gameModel, that handles most of the actions
   */
  public GameModel() {
    car = initializeCar();
    track = initializeTrack();
    track.createObstacles(Settings.OBSTACLEAMOUNT);
    oldVelocity = 0;
    won = false;
  }


  /**
   * check if the race car has passed the start/finish and checkpoint line.
   * Sets the flags accordingly.
   */
  private void checkStartAndFinish() {
    // finish
    if (track.getFinishRect()
        .contains(Math.round(car.getPosition().getX()), Math.round(car.getPosition().getY()))) {
      if (passedCheckpoint) {
        won = true;
      }
      passedStart = true;
    }

    // checkpoint
    if (track.getCheckPointRect()
        .contains(Math.round(car.getPosition().getX()), Math.round(car.getPosition().getY()))) {
      passedCheckpoint = true;
    }
  }

  /**
   * Set the new velocity depending on elapsed time since last timeframe
   * and forces calculated by the getforces() Method.
   *
   * @param delta elapsed time
   */
  private void setNewVelocity(double delta) {
    double allForces = 0;
    if (accelerate) {
      allForces = Settings.engineForce - getForces() * delta;
    } else {
      allForces = -getForces() * delta;
    }
    double newVelocity = car.getVelocity() + allForces;

    if (newVelocity > Settings.MAXSPEED) {
      car.setVelocity(Settings.MAXSPEED);
    } else if (Math.abs(newVelocity) < 1) {
      car.setVelocity(0);
    } else {
      car.setVelocity(car.getVelocity() + allForces);
    }

  }

  /**
   * calculate all forces on the race car.
   */
  private double getForces() {
    return carFriction() + airResistance() / Settings.carWeigth;
  }

  /**
   * get friction depending on the surface.
   */
  private double carFriction() {
    if (track.isOnTrack(car.getPosition())) {
      return Settings.CONCRETERESISTANCE * Settings.normalForce * 5;
    } else {
      return Settings.OFFROADRESISTANCE * Settings.normalForce * 10;
    }
  }

  /**
   * get the air resistance depending on the velocity of the car.
   */
  private double airResistance() {
    return Settings.AIRFACTOR * Settings.CARSURFACE * (Settings.AIRDENSITY * (1 / 2)) * car
        .getVelocity()
        * car.getVelocity();
  }

  /**
   * Check if the car Rectangle intersects with any obstacle on the track.
   *
   * @param carRect the racecar
   */
  public void collisionDetection(Rectangle carRect) {
    for (Obstacle obstacle : track.getObstacles()) {
      Circle circle = new Circle(obstacle.getPosition().getX()+obstacle.getRadius()/2,
          obstacle.getPosition().getY()+obstacle.getRadius()/2,
          obstacle.getRadius()/2);
      if (circle.getLayoutBounds().intersects(carRect.getLayoutBounds())) {
        if (car.getVelocity() >= Settings.CRASHTHRESHOLD) {
          alive = false;
        }
        {
          car.setVelocity(0);
        }
      }
    }
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
    checkStartAndFinish();
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
    passedStart = false;
    passedCheckpoint = false;
    won = false;
    alive = true;
  }

  public void toggleVelocity() {
    double buffer = car.getVelocity();
    car.setVelocity(oldVelocity);
    oldVelocity = buffer;
    if (car.getVelocity() != 0 && oldVelocity != 0) {
      oldVelocity = 0;
    }
  }


  // Getter and Setter
  public Track getTrack() {
    return track;
  }

  public Car getCar() {
    return car;
  }

  public boolean hasWon() {
    return won;
  }

  public boolean isAlive() {
    return alive;
  }

  public void setWon(boolean won) {
    this.won = won;
  }

  public boolean isPassedStart() {
    return passedStart;
  }

  public Vector2D getCarPosition() {
    return car.getPosition();
  }

  public double getCarAngle() {
    return car.getAngle();
  }

}