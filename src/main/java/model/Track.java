package model;

import controller.Settings;

/**
 * Class to represent the race track.
 */
public class Track {
  private double centerX;
  private double centerY;
  private double innerRadiusX;
  private double innerRadiusY;
  private double outerRadiusX;
  private double outerRadiusY;

  public Track(double innerRadiusX, double innerRadiusY,
      double outerRadiusX, double outerRadiusY) {
    this.centerX = 0;
    this.centerY = 0;
    this.innerRadiusX = innerRadiusX;
    this.innerRadiusY = innerRadiusY;
    this.outerRadiusX = outerRadiusX;
    this.outerRadiusY = outerRadiusY;
  }

  public Track() {
  }

  public double getCenterX() {
    return centerX;
  }

  public void setCenterX(double centerX) {
    this.centerX = centerX;
  }

  public double getCenterY() {
    return centerY;
  }

  public void setCenterY(double centerY) {
    this.centerY = centerY;
  }

  public double getInnerRadiusX() {
    return innerRadiusX;
  }

  public void setInnerRadiusX(double innerRadiusX) {
    this.innerRadiusX = innerRadiusX;
  }

  public double getInnerRadiusY() {
    return innerRadiusY;
  }

  public void setInnerRadiusY(double innerRadiusY) {
    this.innerRadiusY = innerRadiusY;
  }

  public double getOuterRadiusX() {
    return outerRadiusX;
  }

  public void setOuterRadiusX(double outerRadiusX) {
    this.outerRadiusX = outerRadiusX;
  }

  public double getOuterRadiusY() {
    return outerRadiusY;
  }

  public void setOuterRadiusY(double outerRadiusY) {
    this.outerRadiusY = outerRadiusY;
  }
}
