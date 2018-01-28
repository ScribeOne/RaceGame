package model;

import controller.Settings;
import java.util.LinkedList;
import java.util.Set;

/**
 * Class to represent the race track.
 */
public class Track {

  private double centerX, centerY, innerRadiusX, innerRadiusY, outerRadiusX, outerRadiusY;
  private LinkedList<Obstacle> obstacles;
  private Vector2D finishUp, finishDown, checkpointUp, checkPointDown;

  /**
   * Constructor with the Radius of the two Ellipses as Parameters.
   */
  public Track(double innerRadiusX, double innerRadiusY,
      double outerRadiusX, double outerRadiusY) {
    this.centerX = Settings.WIDTH / 2;
    this.centerY = Settings.HEIGHT / 2;
    this.innerRadiusX = innerRadiusX;
    this.innerRadiusY = innerRadiusY;
    this.outerRadiusX = outerRadiusX;
    this.outerRadiusY = outerRadiusY;
    this.obstacles = new LinkedList<>();

    finishUp = new Vector2D();
    finishDown = new Vector2D();
    finishUp.setX(centerX);
    finishUp.setY(centerY - outerRadiusY);
    finishDown.setX(centerX);
    finishDown.setY(centerY - innerRadiusY);

    checkpointUp = new Vector2D();
    checkPointDown = new Vector2D();

    checkpointUp.setX(centerX);
    checkpointUp.setY(centerY + outerRadiusY);
    checkPointDown.setX(centerX);
    checkPointDown.setY(centerY + innerRadiusY);

  }

  public boolean isOnTrack(Vector2D position) {
    return isOnOuterTrack(position) && !isOnInnerTrack(position);
  }

  /**
   * create a set amount of obstacles on the track.
   */
  public void createObstacles(int amount) {
    //clear the list and add first fitting obstacle to it
    obstacles.clear();
    obstacles.add(findFittingObstacle());

    while (obstacles.size() < amount) {
      boolean tooClose = false;
      Obstacle helper = findFittingObstacle();
      for (Obstacle obstacle : obstacles) {
        Vector2D distance = new Vector2D();
        double dist = distance.distance(obstacle.getPosition(), helper.getPosition());
        if (dist <= Settings.meterToPixel(Settings.CARHEIGHT * 2)) {
          tooClose = true;
        }
      }
      if (!tooClose) {
        obstacles.add(helper);
      } else {
        helper = null;
      }
    }
  }

  private Obstacle findFittingObstacle() {
    while (true) {
      Vector2D position = new Vector2D();
      position.setX(Math.random() * Settings.WIDTH);
      position.setY(Math.random() * Settings.HEIGHT);
      System.out.println(
          "Guess (" + position.getX() + " | " + position.getY() + " is " + isOnTrack(position));
      if (isOnTrack(position)) {
        System.out.println("found an obstacle");
        return new Obstacle(position, Settings.OBSTACLERADIUS);
      } else {
        position = null;
      }
    }
  }

  private boolean isOnOuterTrack(Vector2D position) {
    double part1 =
        ((position.getX() - centerX) * (position.getX() - centerX)) / (outerRadiusX * outerRadiusX);
    double part2 =
        ((position.getY() - centerY) * (position.getY() - centerY)) / (outerRadiusY * outerRadiusY);
    return (part1 + part2 <= 1);
  }

  private boolean isOnInnerTrack(Vector2D position) {
    double part1 =
        ((position.getX() - centerX) * (position.getX() - centerX)) / (innerRadiusX * innerRadiusX);
    double part2 =
        ((position.getY() - centerY) * (position.getY() - centerY)) / (innerRadiusY * innerRadiusY);
    return (part1 + part2 <= 1);
  }


  public LinkedList<Obstacle> getObstacles() {
    return obstacles;
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

  public Vector2D getFinishUp() {
    return finishUp;
  }

  public void setFinishUp(Vector2D finishUp) {
    this.finishUp = finishUp;
  }

  public Vector2D getFinishDown() {
    return finishDown;
  }

  public void setFinishDown(Vector2D finishDown) {
    this.finishDown = finishDown;
  }

  public Vector2D getCheckpointUp() {
    return checkpointUp;
  }

  public void setCheckpointUp(Vector2D checkpointUp) {
    this.checkpointUp = checkpointUp;
  }

  public Vector2D getCheckPointDown() {
    return checkPointDown;
  }

  public void setCheckPointDown(Vector2D checkPointDown) {
    this.checkPointDown = checkPointDown;
  }
}

