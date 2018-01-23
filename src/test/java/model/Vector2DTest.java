package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class Vector2DTest {

  @Test
  public void normalize() {
    Vector2D vector = new Vector2D(42.0f, 0.0f);
    vector.normalize();
    assertEquals(1.0f, vector.getX(), 0);
    assertEquals(0.0f, vector.getY(), 0);
  }

  @Test
  public void getLength() {
    Vector2D vector = new Vector2D(42.0f, 23.0f);
    assertEquals(47.8852f, vector.getLength(), 0.001f);
  }

  @Test
  public void add() {
    Vector2D vector1 = new Vector2D(-30,60);
    Vector2D vector2 = new Vector2D(50,15);
    Vector2D result = vector1.add(vector2);
    assertEquals(20.0f,result.getX(),0.5f);
  }

  @Test
  public void equals() {
  }

  @Test
  public void getX() {
    Vector2D vector = new Vector2D(2.0f, 5.0f);
    assertEquals(2.0f, vector.getX(), 0);
  }

  @Test
  public void setX() {
  }

  @Test
  public void getY() {
    Vector2D vector = new Vector2D(2.0f, 5.0f);
    assertEquals(5.0f, vector.getY(), 0);
  }

  @Test
  public void setY() {
  }
}