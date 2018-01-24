package view;

import controller.Settings;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import model.Car;
import model.Obstacle;
import model.Track;
import model.Vector2D;

/**
 * Contains all GUI Elements
 */
public class GameView {

  //Canvas and its GraphicsContext where all objects are drawn.
  private Canvas gameCanvas;
  private GraphicsContext gc;

  private Scene scene;

  //Stackpane where all dialogues are stacked up.
  private StackPane rootPane;

  private Pane gamePane, infoPane, helpPane;

  private Label fps, carPos, velo, dir, stuff;
  private boolean infoShown, helpShown;

  public Scene getScene() {
    return scene;
  }

  /**
   * GameView object for setting up the GUI
   *
   * @param stage the primary stage
   */
  public GameView(Stage stage) {
    stage.setTitle("Race Game v1.0");
    stage.setResizable(false);
    stage.sizeToScene();
    rootPane = new StackPane();
    scene = new Scene(rootPane, Settings.WIDTH, Settings.HEIGHT);
    setUpGameWindow();
    initializeInfoPane();
    initializeHelpPane();
    stage.setScene(scene);
  }

  public void setView(boolean showGame, boolean showInfo) {
    if (showInfo && !infoShown) {
      rootPane.getChildren().add(infoPane);
      infoShown = true;
    } else if (!showInfo && infoShown) {
      rootPane.getChildren().remove(infoPane);
      infoShown = false;
    }
  }

  public void clear() {
    gc.clearRect(0, 0, Settings.WIDTH, Settings.HEIGHT);
  }

  public void showHelp() {
    if (!helpShown) {
      rootPane.getChildren().add(helpPane);
      helpShown = true;
    }
  }

  public void removeHelp() {
    if (helpShown) {
      rootPane.getChildren().remove(helpPane);
      helpShown = false;
    }
  }

  private void initializeHelpPane() {
    helpPane = new Pane();
    Rectangle helpWindow = new Rectangle(500, 500);
    helpWindow.setLayoutX(200);
    helpWindow.setLayoutY(250);
    helpWindow.setFill(Color.BLACK);
  }


  /**
   * Sets up the main game window with the course as panebackground,
   * the car in the initial Position
   */
  private void setUpGameWindow() {
    gamePane = new Pane();
    gameCanvas = new Canvas(Settings.WIDTH, Settings.HEIGHT);
    gamePane.getChildren().addAll(gameCanvas);
    gc = gameCanvas.getGraphicsContext2D();
    rootPane.getChildren().add(gamePane);
  }

  /**
   * create InfoBox with all labels and add it to the stackpane.
   */
  private void initializeInfoPane() {
    infoPane = new Pane();
    VBox infoBox = new VBox();
    infoBox.setPrefWidth(250);
    infoBox.setPrefHeight(250);
    fps = new Label();
    fps.setPrefSize(250, 10);
    fps.setText("FPS: ");
    fps.setTextFill(Settings.INFOCOLOR);
    carPos = new Label();
    carPos.setPrefSize(250, 10);
    carPos.setText("Position: ");
    carPos.setTextFill(Settings.INFOCOLOR);
    velo = new Label();
    velo.setPrefSize(250, 10);
    velo.setText("Velocity: ");
    velo.setTextFill(Settings.INFOCOLOR);
    dir = new Label();
    dir.setPrefSize(250, 10);
    dir.setText("Direction: ");
    dir.setTextFill(Settings.INFOCOLOR);
    stuff = new Label();
    stuff.setPrefSize(250, 10);
    stuff.setText("Stuff: ");
    stuff.setTextFill(Settings.INFOCOLOR);
    infoBox.getChildren().addAll(fps, carPos, velo, dir, stuff);
    infoPane.getChildren().add(infoBox);
  }

  /**
   * update Infobox labels with information from the model.
   */
  public void updateInfo(long fps, Vector2D position, double velocity, double direction) {
    this.fps.setText("FPS: " + fps);
    carPos.setText(
        "Position: [" + Math.round(position.getX()) + " | " + Math.round(position.getY()) + "]");
    velo.setText("Velocity: " + velocity + " pixel/sec");
    dir.setText("Direction: " + Math.round(direction) + "°");
  }


  /**
   * draw the pause overlay on the canvas with text and transparent box.
   */
  public void showPause() {
    gc.setFill(Settings.PAUSECOLOR);
    gc.fillRect(0, 0, Settings.WIDTH, Settings.HEIGHT);
    gc.setFill(Color.BLACK);
    gc.setTextAlign(TextAlignment.CENTER);
    gc.setTextBaseline(VPos.CENTER);
    gc.setFont(new Font(40));
    gc.fillText(
        "Paused\n Press P to continue",
        Math.round(gameCanvas.getWidth() / 2),
        Math.round(gameCanvas.getHeight() / 2)
    );
  }


  /**
   * draw the car at its current position.
   */
  public void renderCar(Vector2D position, double newAngle) {

    Image blueCar = new Image(Settings.CARPATH);
    double pivotX = (position.getX() + Settings.meterToPixel(Settings.CARWIDTH) / 2);
    double pivotY = (position.getY() + Settings.meterToPixel(Settings.CARHEIGHT) / 2);
    gc.save();
    gc.transform(new Affine(new Rotate(newAngle, pivotX, pivotY)));
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

    //border of the track
    gc.setStroke(Color.WHITE);
    gc.strokeOval(outerCornerX, outerCornerY, track.getOuterRadiusX() * 2,
        track.getOuterRadiusY() * 2);
    gc.strokeOval(innerCornerX, innerCornerY, track.getInnerRadiusX() * 2,
        track.getInnerRadiusY() * 2);

    //Obstacles
    gc.setFill(Settings.OBSTACLECOLOR);
    for (Obstacle obstacle : track.getObstacles()) {
      gc.fillOval(obstacle.getPosition().getX(), obstacle.getPosition().getY(),
          obstacle.getRadius(), obstacle.getRadius());
    }
  }

  public void showInfo(Car car, Track track, double delta) {

  }
}
