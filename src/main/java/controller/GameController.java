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
  private boolean accelerate, steerLeft, steerRight, brake, pause, infoMode, help, menu;
  private int counter;
  private double timeBuffer;
  private long output;

  public GameController(GameModel gameModel, GameView gameView) {
    this.gameView = gameView;
    this.gameModel = gameModel;
    this.scene = gameView.getScene();
    setUpInputHandler();
    menu = true;
  }

  /**
   * Updates all needed dependencies every frame. GAME LOOP
   *
   * @param timeDifferenceInSeconds the time passed since last frame
   */
  public void updateContinuously(double timeDifferenceInSeconds) {
    updateGameModel(timeDifferenceInSeconds);
    renderGameView(timeDifferenceInSeconds);
  }


  private void renderGameView(double delta) {
    gameView.setView(menu, infoMode);
    counter++;
    timeBuffer += delta;
    if (counter == 60) {
      if (infoMode) {
        output = Math.round(timeBuffer*60);
      }
      counter = 0;
      timeBuffer = 0;
    }
    gameView.clear();
    gameView.renderTrack(gameModel.getTrack());
    gameView.renderCar(gameModel.getCarPosition(), gameModel.getCarAngle());
    if (pause) {
      gameView.showPause();
    }
    if (infoMode) {
      gameView.updateInfo(output, gameModel.getCarPosition(), gameModel.getCar().getVelocity(),
          gameModel.getCar().getAngle(),gameModel.getTrack().isOnTrack(gameModel.getCarPosition()));
    }
    if (help) {
      gameView.showHelp();
      pause = true;
    }
  }


  private void updateGameModel(double delta) {
    if (!pause) {
      gameModel.updateCarControl(accelerate, steerLeft, steerRight, brake);
      gameModel.updateCar(delta);
    }
  }

  private void toggleInfo() {
    infoMode = !infoMode;
    System.out.println("info mode: " + infoMode);
  }


  private void reset() {
    gameModel.resetCar();
  }


  /**
   * Pause mode
   */
  private void pause() {
    if (!pause) {
      gameModel.toggleVelocity();
      pause = true;
    } else {
      gameModel.toggleVelocity();
      pause = false;
    }
  }

  private void helpMenu(){
    if(help){
      gameView.showHelp();
      help = false;
    } else {
      gameView.removeHelp();
      help = true;
    }

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
          case SPACE:
            brake = true;
            break;
          case P:
            pause();
            break;
          case R:
            reset();
            break;
          case I:
            toggleInfo();
            break;
          case H:
            break;
        }
      }
    });   //end keyPressed

    scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        switch (event.getCode()) {
          case UP:
            accelerate = false;
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
          case SPACE:
            brake = false;
            break;
        }
      }
    });   //end keyReleased
  }     //end event handler
}
