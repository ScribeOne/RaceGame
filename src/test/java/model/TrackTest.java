package model;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Observable;
import org.junit.Assert;
import org.junit.Test;

public class TrackTest {

  Track testTrack = new Track(450, 250, 550, 350);

  @Test
  public void isOnTrack() {
    boolean test = testTrack.isOnTrack(new Vector2D(650, 100));
    assertEquals(true, test);

    boolean test2 = testTrack.isOnTrack(new Vector2D(650, 250));
    assertEquals(false, test2);

    boolean test3 = testTrack.isOnTrack(new Vector2D(200, 550));
    assertEquals(true, test3);

  }

  @Test
  public void createObstacles() {
    testTrack.createObstacles(40);
    List<Obstacle> obstacleList = testTrack.getObstacles();
    for(Obstacle obstacle : obstacleList){
      boolean test = testTrack.isOnTrack(obstacle.getPosition());
      assertEquals(true,test);
    }
  }


}