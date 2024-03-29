package model;

import controller.Settings;

import java.util.LinkedList;
import javafx.geometry.Point2D;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

/**
 * Class to represent the race track. Has a Finish and Checkpoint line.
 * Obstacles are created by the track.
 * Has a method to check of a position with x and y is on it.
 */
public class Track {

  private double centerX, centerY, innerRadiusX, innerRadiusY, outerRadiusX, outerRadiusY;
  private LinkedList<Obstacle> obstacles;
  private Line finish, checkpoint;
  private Point2D finishUp, finishDown, checkpointUp, checkPointDown;
  private double tracksize;
  private Rectangle finishRect, checkPointRect;


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
    this.tracksize = outerRadiusX - innerRadiusX;

    /*
     *create finish and checkpoint line
     *at the top and bottom in the middle of the track
     */
    finishUp = new Point2D(centerX, centerY - outerRadiusY);
    finishDown = new Point2D(centerX, centerY - innerRadiusY);
    checkpointUp = new Point2D(centerX, centerY + outerRadiusY);
    checkPointDown = new Point2D(centerX, centerY + innerRadiusY);
    finish = new Line(finishUp.getX(), finishUp.getY(), finishDown.getX(), finishDown.getY());
    checkpoint = new Line(checkpointUp.getX(), checkpointUp.getY(), checkPointDown.getY(),
        checkPointDown.getY());
    finishRect = new Rectangle(centerX, centerY - outerRadiusY, 7, tracksize);
    checkPointRect = new Rectangle(centerX, centerY + innerRadiusY, 7, tracksize);
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
        if (dist <= Settings.meterToPixel(Settings.CARHEIGHT * 3)) {
          tooClose = true;
        }
      }
      if (!tooClose) {
        obstacles.add(helper);
      }
    }
  }

  /**
   * find location for an obstacle.
   *
   * @return obstacle with possible position
   */
  private Obstacle findFittingObstacle() {
    while (true) {
      Vector2D position = new Vector2D();
      position.setX((Math.random() * Settings.WIDTH));
      position.setY((Math.random() * Settings.HEIGHT));
      if (isOnTrack(position)) {
        if (!((position.getX() > 500 && position.getX() < 800) && position.getY() < 500)) {
          return new Obstacle(position, Settings.OBSTACLERADIUS);
        }
      }
    }
  }

  /**
   * position is on the track if it is in the outer but not in the inner ellipse.
   *
   * @param position to check
   */
  public boolean isOnTrack(Vector2D position) {
    return isOnOuterTrack(position) && !isOnInnerTrack(position);
  }

  /**
   * check if position is on the outer ellipse.
   */
  private boolean isOnOuterTrack(Vector2D position) {
    double part1 =
        ((position.getX() - centerX) * (position.getX() - centerX)) / (outerRadiusX * outerRadiusX);
    double part2 =
        ((position.getY() - centerY) * (position.getY() - centerY)) / (outerRadiusY * outerRadiusY);
    return (part1 + part2 <= 1);
  }

  /**
   * check if position is on the inner ellipse.
   */
  private boolean isOnInnerTrack(Vector2D position) {
    double part1 =
        ((position.getX() - centerX) * (position.getX() - centerX)) / (innerRadiusX * innerRadiusX);
    double part2 =
        ((position.getY() - centerY) * (position.getY() - centerY)) / (innerRadiusY * innerRadiusY);
    return (part1 + part2 <= 1);
  }


  /*
   * Getter and Setter
   */
  public LinkedList<Obstacle> getObstacles() {
    return obstacles;
  }

  public Rectangle getFinishRect() {
    return finishRect;
  }

  public double getInnerRadiusX() {
    return innerRadiusX;
  }

  public double getInnerRadiusY() {
    return innerRadiusY;
  }

  public double getOuterRadiusX() {
    return outerRadiusX;
  }

  public double getOuterRadiusY() {
    return outerRadiusY;
  }

  public Rectangle getCheckPointRect() {
    return checkPointRect;
  }

  public Line getFinish() {
    return finish;
  }

  public Line getCheckpoint() {
    return checkpoint;
  }
}

