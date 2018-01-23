package model;

/**
 * 2D Vector for easier calculations on position, direction... of game model objects like car and obstacles.
 */
public class Vector2D {


  //Variables
  private double x;
  private double y;

  //Constructors
  public Vector2D() {
    this.x = 0.0f;
    this.y = 0.0f;
  }

  public Vector2D(double x, double y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Check if 2 Vectors have the same values.
   */
  public boolean equals(Vector2D other) {
    return (this.x == other.x && this.y == other.y);
  }


  /**
   * Add 2 Vectors
   */
  public Vector2D add(Vector2D other) {
    Vector2D result = new Vector2D();
    result.setX(this.x + other.x);
    result.setY(this.y + other.y);
    return result;
  }


  /**
   * Calculate the distance of two Vectors with euclidean distance algorithm.
   */
  public double distance(Vector2D a, Vector2D b) {
    double v0 = b.x - a.x;
    double v1 = b.y - a.y;
    return Math.sqrt(v0 * v0 + v1 * v1);
  }

  /**
   * Multiply the vector with a given number.
   */
  public Vector2D multiply(double scalar) {
    return (new Vector2D(x * scalar, y * scalar));
  }

  /**
   * Multiply the vector with another vector.
   */
  public Vector2D multiply(Vector2D other) {
    return new Vector2D(x * other.x, y * other.y);
  }

  /**
   * get the length of a vector.
   */
  public double getLength() {
    return Math.sqrt(x * x + y * y);
  }

  /**
   * Normalize a Vector to 1, but keep the direction its pointing to.
   */
  public void normalize() {
    double length = Math.sqrt(x * x + y * y);

    if (length != 0.0) {
      double s = 1.0f / (double) length;
      x = x * s;
      y = y * s;
    }
  }


  //Getter and Setter
  public double getX() {
    return x;
  }

  public void setX(double x) {
    this.x = x;
  }

  public double getY() {
    return y;
  }

  public void setY(double y) {
    this.y = y;
  }


}
