package model;

/**
 * 2D Vector for easier calculations on position, direction... of game model objects like car and obstacles.
 */
public class Vector2D {


  //Variables
  private float x;
  private float y;

  //Constructors
  public Vector2D() {
    this.x = 0.0f;
    this.y = 0.0f;
  }

  public Vector2D(float x, float y) {
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
   * Calculate the distance of two Vectors with euclidean distance algorithm.
   */
  public double distance(Vector2D a, Vector2D b) {
    float v0 = b.x - a.x;
    float v1 = b.y - a.y;
    return Math.sqrt(v0 * v0 + v1 * v1);
  }

  /**
   * get the length of a vector.
   */
  public double getLength(){
    return Math.sqrt(x*x+y*y);
  }

  /**
   * Normalize a Vector to 1, but keep the direction its pointing to.
   */
  public void normalize() {
    double length = Math.sqrt(x * x + y * y);

    if (length != 0.0) {
      float s = 1.0f / (float) length;
      x = x * s;
      y = y * s;
    }
  }


  //Getter and Setter
  public float getX() {
    return x;
  }

  public void setX(float x) {
    this.x = x;
  }

  public float getY() {
    return y;
  }

  public void setY(float y) {
    this.y = y;
  }


}
