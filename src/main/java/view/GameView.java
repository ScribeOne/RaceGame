package view;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.GameModel;
import model.Vector2D;

/**
 * Contains all GUI Elements
 */
public class GameView {

  //Dimensions of the application window
  private final int WIDTH = 1300;
  private final int HEIGHT = 800;


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
    scene = new Scene(rootPane, WIDTH, HEIGHT);

    setUpGameWindow();

    stage.setScene(scene);
  }

  /**
   * Sets up the main game window with the course as panebackground,
   * the car in the initial Position
   */
  private void setUpGameWindow() {
    gamePane = new Pane();
    gameCanvas = new Canvas(WIDTH,HEIGHT);
    gamePane.getChildren().add(gameCanvas);
    gc = gameCanvas.getGraphicsContext2D();

    Image blueCar = new Image("../resources/raceCarBlue.png");
    Rectangle car = new Rectangle();
    car.setWidth(150);
    car.setHeight(100);
    car.setLayoutX(650);
    car.setLayoutY(400);
    car.setFill(Color.BLACK);
    //car.setFill();
    gc.setFill(new ImagePattern(blueCar,0,0,1,1,true));
    gc.fillRect(650,400,75,45);


    rootPane.getChildren().add(gamePane);
    System.out.println("setup done!");
  }

  public void renderCar(Vector2D position){

  }

}
