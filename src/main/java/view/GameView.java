package view;

import controller.Settings;
import java.util.Set;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import model.Track;
import model.Vector2D;

/**
 * Contains all GUI Elements
 */
public class GameView {


  private Canvas gameCanvas;
  private Canvas carCanvas;
  private GraphicsContext gc;
  private GraphicsContext carGC;
  private double currentAngle;
  private Rotate rotate;

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
    carGC.clearRect(0, 0, Settings.WIDTH, Settings.HEIGHT);
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
    carGC = carCanvas.getGraphicsContext2D();
    rootPane.getChildren().add(gamePane);
    rotate = new Rotate();
    currentAngle = 180;
  }

  /*
  public double getNewAngle(double newAngle) {
    if (newAngle != currentAngle) {
      return newAngle - currentAngle;
    } else {
      return 0;
    }
  }
  */

  /**
   * draw the car at its current position.
   */
  public void renderCar(Vector2D position, double newAngle) {
    Image blueCar = new Image("../resources/raceCarBlue.png");
    double PivotX = (position.getX() + Settings.meterToPixel(Settings.CARWIDTH) / 2);
    double PivotY = (position.getY() + Settings.meterToPixel(Settings.CARHEIGHT) / 2);
    carGC.save();
    carGC.transform(new Affine(new Rotate(newAngle, PivotX, PivotY)));
    carGC.setFill(new ImagePattern(blueCar, 0, 0, 1, 1, true));
    carGC.fillRect(position.getX(), position.getY(), Settings.meterToPixel(Settings.CARWIDTH),
        Settings.meterToPixel(Settings.CARHEIGHT));
    carGC.restore();

  }

  public void renderTrack(Track track) {
    gc.setFill(Color.GREEN);
    gc.fillRect(0, 0, Settings.WIDTH, Settings.HEIGHT);

    //calculate numbers that javaFX needs to draw a ellipse
    double innerCornerX = (Settings.WIDTH - track.getInnerRadiusX() * 2) / 2;
    double innerCornerY = (Settings.HEIGHT - track.getInnerRadiusY() * 2) / 2;
    double outerCornerX = (Settings.WIDTH - track.getOuterRadiusX() * 2) / 2;
    double outerCornerY = (Settings.HEIGHT - track.getOuterRadiusY() * 2) / 2;

    gc.setFill(Color.GRAY);
    gc.fillOval(outerCornerX, outerCornerY, track.getOuterRadiusX() * 2,
        track.getOuterRadiusY() * 2);

    gc.setFill(Color.GREEN);
    gc.fillOval(innerCornerX, innerCornerY, track.getInnerRadiusX() * 2,
        track.getInnerRadiusY() * 2);

  }

}
