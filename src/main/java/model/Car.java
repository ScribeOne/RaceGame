package model;

import controller.Settings;

/**
 * Represents the race-car in the game. Has a postion and a direction represented by 2D Vectors.
 * Velocity is a double because forces only point into 2 opposite direction.
 * Therefore no need to treat velocity as a Vector.
 */
public class Car {

  private Vector2D position;
  private Vector2D direction;
  private double velocity;

  /**
   * Constructor with direction and position.
   */
  public Car(Vector2D position, Vector2D direction) {
    this.position = position;
    this.direction = direction;
    this.velocity = 0;
  }

  /**
   * Check if car is moving.
   */
  public boolean isMoving() {
    return velocity != 0;
  }

  /**
   * move the car into its direction according to elapsed time since last update.
   *
   * @param delta elapsed time
   */
  public void moveCar(double delta) {
    direction.normalize();
    position = position.add(direction.multiply(velocity).multiply(delta));
  }

  /**
   * steer racecar to the right.
   */
  public void steerRight() {
    if (isMoving()) {
      direction.rotate(0.03);
    }
  }

  /**
   * steer racecar to the left.
   */
  public void steerLeft() {
    if (isMoving()) {
      direction.rotate(-0.03);
    }
  }


  /**
   * slow down the car according to elpased time with brake value from Settings.
   *
   * @param delta elapsed time in seconds
   */
  public void brake(double delta) {
    if (velocity < Settings.MAXSPEED * -1) {
      velocity = Settings.MAXSPEED * -1;
    } else if (velocity > 0) {
      velocity += Settings.BRAKEVALUE * delta;
    } else {
      velocity += Settings.BRAKEVALUE * delta;
    }
  }

  /**
   * get the angle of the car direction relative to the zero degree direction of Settings file.
   *
   * @return angle in degrees
   */
  public double getAngle() {
    return direction.getAngle(direction, Settings.ZERODEGREES);
  }

  /**
   * reset the direction of the racecar to east.
   */
  public void resetDirection() {
    direction.setX(1);
    direction.setY(0);
  }


  /*
   *Getter and Setter
   */
  public void setVelocity(double velocity) {
    this.velocity = velocity;
  }

  public Vector2D getPosition() {
    return position;
  }

  public double getVelocity() {
    return velocity;
  }

  public Vector2D getDirection() {
    return direction;
  }

  public void setPosition(Vector2D position) {
    this.position = position;
  }
}
