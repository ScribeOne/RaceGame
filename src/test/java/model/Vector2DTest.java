package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class Vector2DTest {

  @Test
  public void equals() {
  }

  @Test
  public void getX() {
    Vector2D vector = new Vector2D(2.0f, 5.0f);
    assertEquals(2.0f,vector.getX(),0);
  }

  @Test
  public void setX() {
  }

  @Test
  public void getY() {
    Vector2D vector = new Vector2D(2.0f, 5.0f);
    assertEquals(5.0f,vector.getY(),0);
  }

  @Test
  public void setY() {
  }
}