package application;

import controller.SoundController;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import controller.GameController;
import model.GameModel;
import view.GameView;


/**
 * Main Class to start the JavaFX Application.
 */
public class Main extends Application {


  private long oldTime;

  /**
   * The main entry point. Starts the Animation Timer.
   */
  @Override
  public void start(Stage primaryStage) throws Exception {

    //Create instances for the game
    GameModel gameModel = new GameModel();
    GameView gameView = new GameView(primaryStage);
    SoundController soundController = new SoundController();
    GameController gameController = new GameController(gameModel, gameView, soundController);

    oldTime = System.currentTimeMillis();

    /*
     * Start the gameloop.
     * It is executed every frame, the long now is the current timestamp
     */
    new AnimationTimer() {
      @Override
      public void handle(long now) {

                /*
                  timeDifferenceInSeconds calculates the time between 2 frames.
                  It compares the last time with the current time (now) and
                  is divided by 1000000000.0 to get the time in seconds
                 */
        double timeDifferenceInSeconds = (now - oldTime) / 1000000000.0;

                /*
                  Sets the oldTime to now, so the next loop can take the difference
                 */
        oldTime = now;

                /*
                  Use the controller to update all dependencies
                 */
        gameController.updateContinuously(timeDifferenceInSeconds);

      }
    }.start();

    primaryStage.show();

  }


  /**
   * Main to call the launch method.
   */
  public static void main(String[] args) {
    launch(args);
  }

}
