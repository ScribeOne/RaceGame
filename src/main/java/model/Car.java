package model;

import controller.Settings;

/**
 * Represents the race-car in the game
 */
public class Car {

  private Vector2D position;
  private Vector2D direction;
  private double velocity;

  /*
   *Constructors
   */
  public Car() {
    this.position = new Vector2D();
    this.direction = new Vector2D();
    this.velocity = 0;
  }

  public Car(Vector2D position, Vector2D direction) {
    this.position = position;
    this.direction = direction;
    this.velocity = 0;
  }

  public void steerRight() {
    direction.rotate(0.03);
  }

  public void steerLeft() {
    direction.rotate(-0.03);
  }

  public void accelerateCar(double delta) {
    if (velocity >= Settings.MAXSPEED) {
      velocity = Settings.MAXSPEED;
    } else {
      velocity += Settings.ACCELERATIONVALUE * delta;
    }
  }

  public void brake(double delta) {
    if (velocity < Settings.MAXSPEED * -1) {
      velocity = Settings.MAXSPEED * -1;
    } else if (velocity > 0) {
      velocity += Settings.BRAKEVALUE * delta;
    } else {
      velocity -= Settings.ACCELERATIONVALUE * delta;
    }
  }

  public boolean isMoving() {
    return velocity != 0;
  }

  public void moveCar(double delta) {
    direction.normalize();
    position = position.add(direction.multiply(velocity).multiply(delta));
  }

  public double getAngle() {
    return direction.getAngle(direction, Settings.ZERODEGREES);
  }

  public void resetDirection() {
    direction.setX(-1);
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

  public void setPosition(Vector2D position) {
    this.position = position;
  }

  public Vector2D getDirection() {
    return direction;
  }

  public void setDirection(Vector2D direction) {
    this.direction = direction;
  }

}
