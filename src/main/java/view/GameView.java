package view;

import controller.Settings;
import java.io.File;
import java.net.MalformedURLException;
import java.util.Set;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import model.Obstacle;
import model.Track;
import model.Vector2D;

/**
 * Contains all GUI Elements
 */
public class GameView {


  private Canvas gameCanvas;
  private Canvas carCanvas;
  private GraphicsContext gc;

  private Scene scene;

  //Stackpane where all dialogues are stacked up.
  private StackPane rootPane;

  private Pane gamePane;

  public Scene getScene() {
    return scene;
  }

  /**
   * GameView object for setting up the GUI
   *
   * @param stage the primary stage
   */
  public GameView(Stage stage) {
    stage.setTitle("Race Game Beta");
    stage.setResizable(false);
    stage.sizeToScene();
    rootPane = new StackPane();
    scene = new Scene(rootPane, Settings.WIDTH, Settings.HEIGHT);
    setUpGameWindow();
    stage.setScene(scene);
  }

  public void clear() {
    gc.clearRect(0, 0, Settings.WIDTH, Settings.HEIGHT);
  }


  /**
   * Sets up the main game window with the course as panebackground,
   * the car in the initial Position
   */
  private void setUpGameWindow() {
    gamePane = new Pane();
    gameCanvas = new Canvas(Settings.WIDTH, Settings.HEIGHT);
    carCanvas = new Canvas(Settings.WIDTH, Settings.HEIGHT);
    gamePane.getChildren().addAll(gameCanvas, carCanvas);
    gc = gameCanvas.getGraphicsContext2D();
    rootPane.getChildren().add(gamePane);
  }

  public void showPause() {
    gc.setFill(Settings.PAUSECOLOR);
    gc.fillRect(0, 0, Settings.WIDTH, Settings.HEIGHT);
    gc.setTextAlign(TextAlignment.CENTER);
    gc.setTextBaseline(VPos.CENTER);
    gc.setFont(new Font(40));
    gc.fillText(
        "Paused - Press P to continue",
        Math.round(gameCanvas.getWidth() / 2),
        Math.round(gameCanvas.getHeight() / 2)
    );
  }


  /**
   * draw the car at its current position.
   */
  public void renderCar(Vector2D position, double newAngle) {
    Image blueCar = new Image(Settings.CARPATH);
    double PivotX = (position.getX() + Settings.meterToPixel(Settings.CARWIDTH) / 2);
    double PivotY = (position.getY() + Settings.meterToPixel(Settings.CARHEIGHT) / 2);
    gc.save();
    gc.transform(new Affine(new Rotate(newAngle, PivotX, PivotY)));
    gc.setFill(new ImagePattern(blueCar, 0, 0, 1, 1, true));
    gc.fillRect(position.getX(), position.getY(), Settings.meterToPixel(Settings.CARWIDTH),
        Settings.meterToPixel(Settings.CARHEIGHT));
    gc.restore();

  }

  /**
   * Render Track including background. Color of background and track can be changed in "Setting.java".
   */
  public void renderTrack(Track track) {

    //Background
    gc.setFill(Settings.BACKGROUND);
    gc.fillRect(0, 0, Settings.WIDTH, Settings.HEIGHT);

    //calculate numbers that javaFX needs to draw a ellipse
    double innerCornerX = (Settings.WIDTH - track.getInnerRadiusX() * 2) / 2;
    double innerCornerY = (Settings.HEIGHT - track.getInnerRadiusY() * 2) / 2;
    double outerCornerX = (Settings.WIDTH - track.getOuterRadiusX() * 2) / 2;
    double outerCornerY = (Settings.HEIGHT - track.getOuterRadiusY() * 2) / 2;

    //Outer Ellipse
    gc.setFill(Settings.STREETCOLOR);
    gc.fillOval(outerCornerX, outerCornerY, track.getOuterRadiusX() * 2,
        track.getOuterRadiusY() * 2);

    //Inner Ellipse
    gc.setFill(Settings.BACKGROUND);
    gc.fillOval(innerCornerX, innerCornerY, track.getInnerRadiusX() * 2,
        track.getInnerRadiusY() * 2);

    //Obstacles
    gc.setFill(Settings.OBSTACLECOLOR);
    for (Obstacle obstacle : track.getObstacles()) {
      gc.fillOval(obstacle.getPosition().getX(), obstacle.getPosition().getY(),
          obstacle.getRadius(), obstacle.getRadius());
    }
  }

}
