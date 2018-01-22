package view;

import controller.Settings;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.Track;
import model.Vector2D;

/**
 * Contains all GUI Elements
 */
public class GameView {


  private Canvas gameCanvas;
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

    stage.setTitle("Rennspiel");
    stage.setResizable(false);
    stage.sizeToScene();

    rootPane = new StackPane();
    scene = new Scene(rootPane, Settings.WIDTH, Settings.HEIGHT);

    setUpGameWindow();

    stage.setScene(scene);
  }

  /**
   * Sets up the main game window with the course as panebackground,
   * the car in the initial Position
   */
  private void setUpGameWindow() {
    gamePane = new Pane();
    gameCanvas = new Canvas(Settings.WIDTH, Settings.HEIGHT);
    gamePane.getChildren().add(gameCanvas);
    gc = gameCanvas.getGraphicsContext2D();
    rootPane.getChildren().add(gamePane);
    gc.setFill(Color.GREEN);
    gc.fillRect(0, 0, Settings.WIDTH, Settings.HEIGHT);
  }

  /**
   * draw the car at its current position.
   */
  public void renderCar(Vector2D position) {
    Image blueCar = new Image("../resources/raceCarBlue.png");
    Rectangle car = new Rectangle();
    car.setWidth(150);
    car.setHeight(100);
    car.setLayoutX(position.getX());
    car.setLayoutY(position.getY());
    car.setFill(Color.BLACK);
    gc.setFill(new ImagePattern(blueCar, 0, 0, 1, 1, true));
    gc.fillRect(position.getX(), position.getY(), 75, 45);
  }

  public void renderTrack(double innerX, double innerY, double outerX, double outerY) {

    //calculate numbers that javaFX needs to draw a ellipse
    double innerCornerX = (Settings.WIDTH - innerX * 2) / 2;
    double innerCornerY = (Settings.HEIGHT - innerY * 2) / 2;
    double outerCornerX = (Settings.WIDTH - outerX * 2) / 2;
    double outerCornerY = (Settings.HEIGHT - outerY * 2) / 2;

    gc.setFill(Color.GRAY);
    gc.fillOval(outerCornerX,outerCornerY,outerX*2,outerY*2);

    gc.setFill(Color.GREEN);
    gc.fillOval(innerCornerX,innerCornerY,innerX*2,innerY*2);

  }

}
