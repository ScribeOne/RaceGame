package controller;

import javafx.scene.Scene;
import model.GameModel;
import view.GameView;

public class GameController {

  private GameView gameView;
  private GameModel gameModel;
  private Scene scene;

  public GameController(GameModel gameModel, GameView gameView) {
    this.gameView = gameView;
    this.gameModel = gameModel;
    this.scene = gameView.getScene();

    setUpInputHandler();

  }

  /**
   * Updates all needed dependencies every frame
   *
   * @param timeDifferenceInSeconds the time passed since last frame
   */
  public void updateContinuously(double timeDifferenceInSeconds) {

  }

  private void setUpInputHandler() {
    /*
     * Useful actions:
     * setOnKeyPressed, setOnKeyReleased
     */
  }

}
