package controller;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
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
    gameModel.update(timeDifferenceInSeconds);
    gameView.renderTrack(gameModel.track.getInnerRadiusX(),gameModel.track.getInnerRadiusY(),gameModel.track.getOuterRadiusX(),gameModel.track.getOuterRadiusY());
    gameView.renderCar(gameModel.getCarPosition());
  }

  private void setUpInputHandler() {
    scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        switch (event.getCode()){
          case UP: //do something
        }
      }
    });
  }

}
