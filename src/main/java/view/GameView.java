package view;

import application.Main;
import controller.Settings;
import java.util.Set;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
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
import model.Obstacle;
import model.Track;
import model.Vector2D;

/**
 * Contains all GUI Elements
 */
public class GameView {

  private Canvas gameCanvas;
  private GraphicsContext gc;
  private Scene scene;
  private Stage stage;
  private StackPane rootPane;
  private Pane gamePane, infoPane, menuPane, pausePane, gameOverPane, winPane;
  private Label fps, carPos, velo, dir, tracker, timeLabel;
  private boolean infoShown, menuShown, pauseShown, showGameOver, showWon;
  private Button startButton, exitButton, tryAgainButton, menuButton, tryAgainButton1, menuButton1;
  private Rectangle carRect;
  private Image raceCar, explosion;


  /**
   * GameView object for setting up the GUI
   *
   * @param stage the primary stage
   */
  public GameView(Stage stage) {
    // set stage
    this.stage = stage;
    stage.setTitle("Race Game v1.4");
    stage.setResizable(false);
    stage.sizeToScene();

    // create Stackpane and add to scene
    rootPane = new StackPane();
    scene = new Scene(rootPane, Settings.WIDTH, Settings.HEIGHT);

    raceCar = new Image(Settings.CARPATH);
    explosion = new Image(Settings.EXPLOSIONPATH);

    // create all content panes that get stacked up on the root pane
    setUpGamePane();
    setUpInfoPane();
    setUpMenuPane();
    setupPausePane();
    setupGameOver();
    setupWinning();

    //Set style for the different Panes
    menuPane.getStylesheets().add(Settings.MENUCSS);
    pausePane.getStylesheets().add(Settings.GAMECSS);
    gameOverPane.getStylesheets().add(Settings.GAMECSS);
    winPane.getStylesheets().add(Settings.GAMECSS);

    showGameOver = false;
    showWon = false;

    // load custom font
    Font.loadFont(
        Main.class.getResource("/style/tagtype-freefont.ttf").toExternalForm(),
        10
    );

    carRect = new Rectangle();

    //set scene to stage
    stage.setScene(scene);
  }


  public void setupWinning() {
    winPane = new Pane();
    winPane.setPrefSize(1300, 800);
    winPane.getStyleClass().add("gameOver");

    //Setup VBox that holds the buttons
    VBox menuBox = new VBox(20);
    menuBox.setPrefSize(Settings.WIDTH, Settings.HEIGHT);
    menuBox.setAlignment(Pos.CENTER);

    Label wonLabel = new Label();
    wonLabel.setText("WON");
    wonLabel.getStyleClass().add("gameOverText");

    Label wonText = new Label();
    wonText.setText("Your time:");
    wonText.getStyleClass().add("winningText");

    timeLabel = new Label();
    timeLabel.setText("");
    timeLabel.getStyleClass().add("winningText");

    //Create Buttons
    tryAgainButton1 = new Button();
    tryAgainButton1.setPrefSize(250, 25);
    tryAgainButton1.setText("TRY AGAIN");
    menuButton1 = new Button();
    menuButton1.setPrefSize(250, 25);
    menuButton1.setText("BACK TO MENU");

    menuButton1.getStyleClass().add("menu-button");
    tryAgainButton1.getStyleClass().add("menu-button");

    menuBox.getChildren().addAll(wonLabel, wonText, timeLabel, tryAgainButton1, menuButton1);
    winPane.getChildren().add(menuBox);
  }

  /**
   * Adds the game Over Pane to the Stackpane to make it visible.
   */
  public void showWon(boolean won) {
    if (won && !showWon) {
      rootPane.getChildren().add(winPane);
      showWon = true;
    }
    if (!won && showWon) {
      rootPane.getChildren().remove(winPane);
      showWon = false;
    }
  }


  public void setupGameOver() {
    gameOverPane = new Pane();
    gameOverPane.setPrefSize(1300, 800);
    gameOverPane.getStyleClass().add("gameOver");

    //Setup VBox that holds the buttons
    VBox menuBox = new VBox(20);
    menuBox.setPrefSize(Settings.WIDTH, Settings.HEIGHT);
    menuBox.setAlignment(Pos.CENTER);

    Label gameOverText = new Label();
    gameOverText.setText("Game Over");
    gameOverText.getStyleClass().add("gameOverText");

    //Create Buttons
    tryAgainButton = new Button();
    tryAgainButton.setPrefSize(250, 25);
    tryAgainButton.setText("TRY AGAIN");
    menuButton = new Button();
    menuButton.setPrefSize(250, 25);
    menuButton.setText("BACK TO MENU");

    menuButton.getStyleClass().add("menu-button");
    tryAgainButton.getStyleClass().add("menu-button");

    menuBox.getChildren().addAll(gameOverText, tryAgainButton, menuButton);
    gameOverPane.getChildren().add(menuBox);
  }

  /**
   * Adds the game Over Pane to the Stackpane to make it visible.
   */
  public void gameOver(boolean alive) {
    if (!alive && !showGameOver) {
      rootPane.getChildren().add(gameOverPane);
      showGameOver = true;
    }
    if (alive && showGameOver) {
      rootPane.getChildren().remove(gameOverPane);
      showGameOver = false;
    }
  }


  /**
   * display the current track time from the stopwatch.
   *
   * @param elapsedTime current track time
   */
  public void printTrackTime(long elapsedTime) {
    gc.setFill(Color.BLACK);
    gc.setFont(new Font(35));
    gc.setTextAlign(TextAlignment.CENTER);
    gc.setTextBaseline(VPos.CENTER);
    gc.fillText(secondsToString(elapsedTime),
        1190,
        75
    );
  }

  /**
   * Format seconds into mm:ss
   *
   * @return formated time mm:ss
   */
  private String secondsToString(long sec) {
    return String.format("%02d:%02d", sec / 60, sec % 60);
  }


  /**
   * Set up the menu pane with background and buttons.
   */
  private void setUpMenuPane() {

    //Setup pane
    menuPane = new Pane();
    menuPane.setPrefSize(Settings.WIDTH, Settings.HEIGHT);
    menuPane.getStyleClass().add("background");

    //Setup VBox that holds the buttons
    VBox menuBox = new VBox(20);
    menuBox.setPrefSize(Settings.WIDTH, Settings.HEIGHT);
    menuBox.setLayoutX(0);
    menuBox.setLayoutY(50);
    menuBox.setAlignment(Pos.CENTER);

    //Create Buttons
    startButton = new Button();
    startButton.setPrefSize(250, 25);
    startButton.setText("START GAME");
    exitButton = new Button();
    exitButton.setPrefSize(250, 25);
    exitButton.setText("EXIT");

    //add css
    startButton.getStyleClass().add("menu-button");
    exitButton.getStyleClass().add("menu-button");

    //Add buttons to VBox and VBox to pane
    menuBox.getChildren().addAll(startButton, exitButton);
    menuPane.getChildren().addAll(menuBox);
  }


  /**
   * Sets up the main game window, create Canvas and its GraphicsContext.
   */
  private void setUpGamePane() {
    gamePane = new Pane();
    gameCanvas = new Canvas(Settings.WIDTH, Settings.HEIGHT);
    gamePane.getChildren().addAll(gameCanvas);
    gc = gameCanvas.getGraphicsContext2D();
    rootPane.getChildren().add(gamePane);
  }


  /**
   * create InfoBox with all labels and add it to the stackpane.
   */
  private void setUpInfoPane() {
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
    tracker = new Label();
    tracker.setPrefSize(250, 10);
    tracker.setText("Car is on track: ");
    tracker.setTextFill(Settings.INFOCOLOR);
    infoBox.getChildren().addAll(fps, carPos, velo, dir, tracker);
    infoPane.getChildren().add(infoBox);
  }


  /**
   * update Infobox labels with information from the model.
   */
  public void updateInfo(long fps, Vector2D position, double velocity, double direction,
      boolean carOnTrack) {
    this.fps.setText("FPS: " + fps);
    carPos.setText(
        "Position: [" + Math.round(position.getX()) + " | " + Math.round(position.getY()) + "]");
    velo.setText("Velocity: " + velocity + " pixel/sec");
    dir.setText("Direction: " + Math.round(direction) + "°");
    tracker.setText("Car is on track: " + carOnTrack);
  }

  public void showWon() {
    gc.setFill(Color.BLACK);
    gc.fillRect(0, 0, 1300, 800);
  }


  /**
   * draw the pause overlay on the canvas with text and transparent box.
   */
  public void setupPausePane() {
    pausePane = new Pane();
    pausePane.setPrefSize(Settings.WIDTH, Settings.HEIGHT);
    pausePane.getStyleClass().add("background");
  }

  public void setPause(boolean showPause) {
    if (showPause) {
      showPause();
    } else if (!showPause && pauseShown) {
      removePause();
    }
  }

  /**
   * Adds the Menu Pane to the Stackpane to make it visible.
   */
  private void showPause() {
    if (!pauseShown) {
      rootPane.getChildren().add(pausePane);
      pauseShown = true;
      System.out.println("pause added. Variable is: " + pauseShown);
    }
  }

  /**
   * remove menu from the stack pane.
   */
  private void removePause() {
    if (pauseShown) {
      rootPane.getChildren().remove(pausePane);
      pauseShown = false;
      System.out.println("pause removed. variable: " + pauseShown);
    }
  }


  public Rectangle getCarRect() {
    return carRect;
  }


  /**
   * Draw the car at its current position. Explosion image shown when alive flag is false.
   */
  public void renderCar(Vector2D position, double newAngle, boolean alive) {
    double pivotX = (position.getX());
    double pivotY = (position.getY());
    carRect = new Rectangle(position.getX() - Settings.meterToPixel(Settings.CARWIDTH) / 2,
        position.getY() - Settings.meterToPixel(Settings.CARHEIGHT) / 2,
        Settings.meterToPixel(Settings.CARWIDTH),
        Settings.meterToPixel(Settings.CARHEIGHT));
    carRect.getTransforms().add(new Affine(new Rotate(newAngle, pivotX, pivotY)));
    gc.save();
    gc.transform(new Affine(new Rotate(newAngle, pivotX, pivotY)));
    gc.setFill(new ImagePattern(raceCar, 0, 0, 1, 1, true));
    gc.fillRect(position.getX() - Settings.meterToPixel(Settings.CARWIDTH) / 2,
        position.getY() - Settings.meterToPixel(Settings.CARHEIGHT) / 2,
        Settings.meterToPixel(Settings.CARWIDTH),
        Settings.meterToPixel(Settings.CARHEIGHT));
    gc.restore();

    // Explosion
    if (!alive) {
      gc.setFill(new ImagePattern(explosion, 0, 0, 1, 1, true));
      gc.fillRect(position.getX() - 20,
          position.getY() - 20, 40, 40);
    }

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

    // Finish and Checkpoint
    gc.setLineDashes(8);
    gc.setLineWidth(6);
    gc.setStroke(Color.WHITE);
    gc.strokeLine(track.getFinish().getStartX(), track.getFinish().getStartY(),
        track.getFinish().getEndX(), track.getFinish().getEndY());
    gc.strokeLine(track.getCheckpoint().getStartX(), track.getCheckpoint().getStartY(),
        track.getCheckpoint().getEndX(), track.getCheckpoint().getEndY());

    //border of the track
    gc.setStroke(Color.RED);
    gc.setLineWidth(2);
    gc.setLineDashes(0);
    gc.strokeOval(outerCornerX, outerCornerY, track.getOuterRadiusX() * 2,
        track.getOuterRadiusY() * 2);
    gc.strokeOval(innerCornerX, innerCornerY, track.getInnerRadiusX() * 2,
        track.getInnerRadiusY() * 2);

    gc.setStroke(Color.WHITE);
    gc.setLineWidth(2);
    gc.setLineDashes(20);
    gc.strokeOval(outerCornerX, outerCornerY, track.getOuterRadiusX() * 2,
        track.getOuterRadiusY() * 2);
    gc.strokeOval(innerCornerX, innerCornerY, track.getInnerRadiusX() * 2,
        track.getInnerRadiusY() * 2);

    //Obstacles
    gc.setFill(Settings.OBSTACLECOLOR);
    for (Obstacle obstacle : track.getObstacles()) {
      gc.fillOval(obstacle.getPosition().getX() - Settings.OBSTACLERADIUS /2,
          obstacle.getPosition().getY() - Settings.OBSTACLERADIUS/2,
          obstacle.getRadius() *2, obstacle.getRadius() *2 );
    }
  }

  /**
   * Adds the Menu Pane to the Stackpane to make it visible.
   */
  private void showMenu() {
    if (!menuShown) {
      rootPane.getChildren().add(menuPane);
      menuShown = true;
    }
  }

  /**
   * remove menu from the stack pane.
   */
  private void removeMenu() {
    if (menuShown) {
      rootPane.getChildren().remove(menuPane);
      menuShown = false;
    }
  }

  /**
   * sets up view for inital start.
   *
   * @param showMenu toggle menu at start on and off
   * @param showInfo show menu for debugging
   */
  public void setView(boolean showMenu, boolean showInfo) {
    if (showInfo && !infoShown) {
      rootPane.getChildren().add(infoPane);
      infoShown = true;
    } else if (!showInfo && infoShown) {
      rootPane.getChildren().remove(infoPane);
      infoShown = false;
    }
    if (showMenu) {
      showMenu();
    } else if (!showMenu && menuShown) {
      removeMenu();
    }
  }

  /**
   * clear the whole Canvas' GraphicsContext.
   */
  public void clear() {
    gc.clearRect(0, 0, Settings.WIDTH, Settings.HEIGHT);
  }


  // Getter and Setter
  public Button getStartButton() {
    return startButton;
  }

  public void setWinText(long wintime) {
    timeLabel.setText(secondsToString(wintime));
  }

  public Button getExitButton() {
    return exitButton;
  }

  public Button getTryAgainButton() {
    return tryAgainButton;
  }

  public Button getMenuButton() {
    return menuButton;
  }

  public Button getTryAgainButton1() {
    return tryAgainButton1;
  }

  public Button getMenuButton1() {
    return menuButton1;
  }

  public Stage getStage() {
    return stage;
  }

  public Scene getScene() {
    return scene;
  }
}