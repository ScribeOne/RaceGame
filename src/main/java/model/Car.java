package model;

/**
 * Represents the race-car in the game
 */
public class Car {

  private Vector2D position;
  private Vector2D direction;


  public Car() {
    this.position = new Vector2D();
    this.direction = new Vector2D();
  }

  public Car(Vector2D position, Vector2D direction) {
    this.position = position;
    this.direction = direction;
  }

  public Vector2D getPosition() {
    return position;
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
