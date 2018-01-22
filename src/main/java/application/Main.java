package application;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import controller.GameController;
import model.GameModel;
import view.GameView;


public class Main extends Application {

  private long oldTime;

  /**
   * The main entry point
   */
  @Override
  public void start(Stage primaryStage) throws Exception {

    //Create instances for the game
    GameModel gameModel = new GameModel();
    GameView gameView = new GameView(primaryStage);
    GameController gameController = new GameController(gameModel,gameView);


    oldTime = System.currentTimeMillis();
    System.out.println("current time: " + oldTime);
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


  public static void main(String[] args) {
    launch(args);
  }

}
