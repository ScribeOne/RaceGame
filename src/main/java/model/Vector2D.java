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
    double v0 = a.x - b.x;
    double v1 = a.y - b.y;
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

  public double getAngle(Vector2D vector1, Vector2D vector2) {
    double helper = Math.atan2(vector1.getX() * vector2.getY() - vector1.getY() * vector2.getX(),
        vector1.crossProduct(vector2));
    helper = (helper / Math.PI) * 180;
    helper = helper * -1;
    if (helper < 0) {
      helper = 360 - helper * -1;
    }
    return helper;
  }

  public double crossProduct(Vector2D other) {
    return x * other.getX() + y * other.getY();
  }


  public void rotate(double n) {
    x = (x * Math.cos(n)) - (y * Math.sin(n));
    y = (x * Math.sin(n)) + (y * Math.cos(n));
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
