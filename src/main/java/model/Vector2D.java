package model;


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

  public boolean equals(Vector2D other) {
    return (this.x == other.x && this.y == other.y);
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
