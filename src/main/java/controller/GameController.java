package controller;

import java.util.Optional;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.GameModel;
import view.GameView;

public class GameController {

  private GameView gameView;
  private GameModel gameModel;
  private Scene scene;
  private Stage stage;
  private boolean accelerate, steerLeft, steerRight, brake, pause, infoMode, help, menu;
  private int counter;
  private double timeBuffer;
  private long fpsTimer;
  private double min;
  private double sec;

  public GameController(GameModel gameModel, GameView gameView) {
    this.gameView = gameView;
    this.gameModel = gameModel;
    this.scene = gameView.getScene();
    this.stage = gameView.getStage();

    setUpInputHandler();
    menu = true;
    infoMode = true;
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
        gameView.updateTime(timeBuffer);
      if (infoMode) {
        fpsTimer = Math.round(timeBuffer * 60);
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
      gameView.updateInfo(fpsTimer, gameModel.getCarPosition(), gameModel.getCar().getVelocity(),
          gameModel.getCar().getAngle(),
          gameModel.getTrack().isOnTrack(gameModel.getCarPosition()));
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
      gameView.resetTimer();
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

  private void helpMenu() {
    if (help) {
      gameView.showHelp();
      help = false;
    } else {
      gameView.removeHelp();
      help = true;
    }

  }


  /**
   * Start the Input handler to control the race car and open menus with mouse and key events.
   */
  private void setUpInputHandler() {

    //get buttons from the gameView
    Button start = gameView.getStartButton();
    Button exit = gameView.getExitButton();

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
          case ESCAPE:
            stage.close();
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

    /**
     * Event Handler for the Start Game Button. Disables the menu, resets the car.
     */
    start.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent mouseEvent) {
        System.out.println("mouse click detected! " + mouseEvent.getSource());
        menu = false;
        reset();
        gameView.resetTimer();
      }
    });


    /**
     * Event Handler for the Exit Button. Calls the confirmation alert.
     */
    exit.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent mouseEvent) {
        System.out.println("mouse click detected! " + mouseEvent.getSource());
        exit.setOnAction(event ->
            stage.fireEvent(
                new WindowEvent(
                    stage,
                    WindowEvent.WINDOW_CLOSE_REQUEST
                )
            )
        );
      }
    });

    /**
     * Event Handler for Exit Confirmation dialogue with a Alert Window from JavaFX.
     * Opens up whenever the main windows "X" is clicked.
     * Also gets called when the exit button in main menu is clicked.
     * The game will be paused when the Exit Confirmation is shown and resume if canceled.
     */
    EventHandler<WindowEvent> confirmCloseEventHandler = event -> {
      Alert closeConfirmation = new Alert(
          Alert.AlertType.CONFIRMATION,
          "Are you sure you want to exit?"
      );
      // call pause
      pause();

      // set up dialogue
      Button exitButton = (Button) closeConfirmation.getDialogPane().lookupButton(
          ButtonType.OK
      );
      exitButton.setText("Exit");
      closeConfirmation.setHeaderText("Confirm Exit");
      closeConfirmation.initModality(Modality.APPLICATION_MODAL);
      closeConfirmation.initOwner(stage);

      // wait until the user clicks something
      Optional<ButtonType> closeResponse = closeConfirmation.showAndWait();
      if (!ButtonType.OK.equals(closeResponse.get())) {
        event.consume();
      }

      // unpause if dialogue got canceled
      pause();
    };

    stage.setOnCloseRequest(confirmCloseEventHandler);


  }     //end event handler
}
