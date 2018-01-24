package model;

public class Obstacle {

  private Vector2D position;
  private double radius;

  public Obstacle(Vector2D position, double radius) {
    this.position = position;
    this.radius = radius;
  }

  public Vector2D getPosition() {
    return position;
  }

  public void setPosition(Vector2D position) {
    this.position = position;
  }

  public double getRadius() {
    return radius;
  }

  public void setRadius(double radius) {
    this.radius = radius;
  }
}
