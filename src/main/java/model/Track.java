package model;


public class Track {

  private int centerX;
  private int centerY;

  private int innerEllipseX;
  private int innerEllipseY;
  private int outerEllipseX;
  private int outerEllipseY;


  public Track(int centerX, int centerY, int innerEllipseX, int innerEllipseY, int outerEllipseX,
      int outerEllipseY) {
    this.centerX = centerX;
    this.centerY = centerY;
    this.innerEllipseX = innerEllipseX;
    this.innerEllipseY = innerEllipseY;
    this.outerEllipseX = outerEllipseX;
    this.outerEllipseY = outerEllipseY;
  }

  public int getCenterX() {
    return centerX;
  }

  public void setCenterX(int centerX) {
    this.centerX = centerX;
  }

  public int getCenterY() {
    return centerY;
  }

  public void setCenterY(int centerY) {
    this.centerY = centerY;
  }

  public int getInnerEllipseX() {
    return innerEllipseX;
  }

  public void setInnerEllipseX(int innerEllipseX) {
    this.innerEllipseX = innerEllipseX;
  }

  public int getInnerEllipseY() {
    return innerEllipseY;
  }

  public void setInnerEllipseY(int innerEllipseY) {
    this.innerEllipseY = innerEllipseY;
  }

  public int getOuterEllipseX() {
    return outerEllipseX;
  }

  public void setOuterEllipseX(int outerEllipseX) {
    this.outerEllipseX = outerEllipseX;
  }

  public int getOuterEllipseY() {
    return outerEllipseY;
  }

  public void setOuterEllipseY(int outerEllipseY) {
    this.outerEllipseY = outerEllipseY;
  }
}
