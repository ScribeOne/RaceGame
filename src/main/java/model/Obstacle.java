package model;

/**
 * round obstacles that can be spawned by the track class.
 */
public class Obstacle {

  private Vector2D position;
  private double radius;

  /**
   * constructor
   * @param position desired position as Vector
   * @param radius desired radius of the obstacle
   */
  public Obstacle(Vector2D position, double radius) {
    this.position = position;
    this.radius = radius;
  }


  // Getter for position and radius
  public Vector2D getPosition() {
    return position;
  }

  public double getRadius() {
    return radius;
  }

}
