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
  private boolean accelerate, steerLeft, steerRight, brake;

  public GameController(GameModel gameModel, GameView gameView) {
    this.gameView = gameView;
    this.gameModel = gameModel;
    this.scene = gameView.getScene();
    setUpInputHandler();
  }

  /**
   * Updates all needed dependencies every frame. GAME LOOP
   *
   * @param timeDifferenceInSeconds the time passed since last frame
   */
  public void updateContinuously(double timeDifferenceInSeconds) {
    updateGameModel(timeDifferenceInSeconds);
    renderGameView();
  }


  private void renderGameView() {
    gameView.clear();
    gameView.renderTrack(gameModel.getTrack());
    gameView.renderCar(gameModel.getCarPosition(), gameModel.getCarAngle());

  }


  private void updateGameModel(double delta) {
    gameModel.updateCarControl(accelerate, steerLeft, steerRight, brake);
    gameModel.updateCar(delta);
  }


  private void reset() {
    gameModel.resetCar();
  }


  /**
   * Pause mode: TODO
   */
  private void pause() {
    //do something
  }

  /**
   * Keyhandler to control the race car and open menus.
   */
  private void setUpInputHandler() {

    scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        switch (event.getCode()) {
          case UP:
            accelerate = true;
            System.out.println("UP pressed");
            break;
          case LEFT:
            steerLeft = true;
            break;
          case RIGHT:
            steerRight = true;
            break;
          case DOWN:
            brake = true;
            break;
          case P:
            pause();
          case R:
            reset();
        }
      }
    });   //end keyPressed

    scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        switch (event.getCode()) {
          case UP:
            accelerate = false;
            System.out.println("UP released");
            break;
          case LEFT:
            steerLeft = false;
            break;
          case RIGHT:
            steerRight = false;
            break;
          case DOWN:
            brake = false;
            break;
        }
      }
    });   //end keyReleased


  }     //end event handler

}
