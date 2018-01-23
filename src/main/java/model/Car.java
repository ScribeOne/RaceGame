package model;

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
    this.direction = new Vector2D(1,0);
    this.velocity = 0;
  }

  public void steerRight(){
    direction = direction.add(new Vector2D(-0.1,0.01));
  }

  public void steerLeft(){
    direction = direction.add(new Vector2D(0.01,-0.1));
  }

  public void accelerateCar(double value) {
    velocity += value;
    System.out.println("new velocity: "+ velocity);
  }

  public void brake(double brakeValue){
    velocity += brakeValue;
  }

  public void moveCar(double delta) {
    direction.normalize();
    position = position.add(direction.multiply(velocity).multiply(delta));
  }


  /*
   *Getter and Setter
   */
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
