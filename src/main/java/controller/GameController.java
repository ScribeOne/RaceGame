package controller;

import java.util.Optional;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import model.GameModel;
import model.Obstacle;
import model.Stopwatch;
import view.GameView;

/**
 * Class to connect Model with View and handle user input.
 * The GameController gets called every time frame and updates game model with delta time.
 * View gets then updated with the model data.
 * Handles the Tracktime with a Stopwatch and Game Sound with Mediaplayers.
 */
public class GameController {

  private GameView gameView;
  private GameModel gameModel;
  private SoundController soundController;
  private Scene scene;
  private Stage stage;
  private boolean accelerate, steerLeft, steerRight, brake, pause, infoMode, menu, sound;
  private Stopwatch stopwatch;

  /**
   * constructor
   *
   * @param gameModel all game data stored in model
   * @param gameView view renders all GUI elements
   * @param soundController handles all sound effects
   */
  public GameController(GameModel gameModel, GameView gameView, SoundController soundController) {
    this.gameView = gameView;
    this.gameModel = gameModel;
    this.soundController = soundController;
    this.scene = gameView.getScene();
    this.stage = gameView.getStage();

    //create a stopwatch for track time
    this.stopwatch = new Stopwatch();

    // Setup Eventhandler for user input
    setUpInputHandler();

    //Turn default views on and off with flags
    menu = true;
    infoMode = false;
    sound = true;
  }

  /**
   * Updates all needed dependencies every frame. GAME LOOP
   *
   * @param timeDifferenceInSeconds the time passed since last frame
   */
  public void updateContinuously(double timeDifferenceInSeconds) {
    updateGameModel(timeDifferenceInSeconds);
    renderGameView();
    playSound();
  }


  /**
   * Update SoundController flags with info from the game model and play the sounds.
   */
  private void playSound() {
    soundController.update(menu, gameModel.isAlive(), accelerate, gameModel.getCar().isMoving());
    soundController.playSound(sound);
  }


  /**
   * Render the content to the screen.
   * Calls methods of the game view with data from the game model.
   */
  private void renderGameView() {
    // set flags
    gameView.setView(menu, infoMode);

    // clear the view first
    gameView.clear();

    //render all game objects, track first
    gameView.renderTrack(gameModel.getTrack());
    gameView.printTrackTime(stopwatch.getElapsedTime());
    gameView.renderCar(gameModel.getCarPosition(), gameModel.getCarAngle(), gameModel.isAlive());
    gameView.setPause(pause);

    // show info box when flag is true
    if (infoMode) {
      gameView.updateInfo(60, gameModel.getCarPosition(), gameModel.getCar().getVelocity(),
          gameModel.getCar().getAngle(),
          gameModel.getTrack().isOnTrack(gameModel.getCarPosition()));
    }

    // check if the game has been won and show winning screen
    if (gameModel.hasWon()) {
      gameView.showWon(gameModel.hasWon());
      gameView.setWinText(stopwatch.getElapsedTime());
      stopwatch.pause();
      gameModel.getCar().setVelocity(0);
    }

    // Show the game over screen when lost and not in the menu
    if (!gameModel.isAlive() && !menu) {
      gameView.gameOver(gameModel.isAlive());
      gameModel.getCar().setVelocity(0);
      stopwatch.pause();
    }
  }

  /**
   * Update the game model with user input for car control and update the car with new delta time.
   *
   * @param delta elapsed time
   */
  private void updateGameModel(double delta) {
    // update if not paused
    if (!pause) {
      gameModel.updateCarControl(accelerate, steerLeft, steerRight, brake);
      gameModel.updateCar(delta);
      gameModel.collisionDetection(gameView.getCarRect());
    }

    // start the stopwatch of car has passed start line
    if (gameModel.isPassedStart()) {
      stopwatch.start();
    }
  }


  /**
   * turn sound on and off, stops all sound when turning off.
   */
  private void toggleSound() {
    sound = !sound;
  }

  /**
   * turn info box on and off
   */
  private void toggleInfo() {
    infoMode = !infoMode;
  }


  /**
   * reset car and stopwatch
   */
  private void reset() {
    gameModel.resetCar();
    gameModel.setWon(false);
    gameView.gameOver(true);
    gameView.showWon(false);
    stopwatch.reset();
    stopwatch.stop();
  }


  /**
   * Pause mode
   */
  private void pause() {
    if (!pause) {
      gameModel.toggleVelocity();
      pause = true;
      stopwatch.pause();
    } else {
      gameModel.toggleVelocity();
      pause = false;
      stopwatch.resume();
    }
  }


  /**
   * Start the Input handler to control the race car and open menus with mouse and key events.
   */
  private void setUpInputHandler() {

    //get buttons from the gameView
    Button start = gameView.getStartButton();
    Button exit = gameView.getExitButton();
    Button retry = gameView.getTryAgainButton();
    Button menuButton = gameView.getMenuButton();
    Button retry1 = gameView.getTryAgainButton1();
    Button menuButton1 = gameView.getMenuButton1();

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
          case S:
            toggleSound();
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
     * Event Handler for the Restart Button.
     */
    retry.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent mouseEvent) {
        reset();
      }
    });

    /**
     * Event Handler for the Menu Button.
     */
    menuButton.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent mouseEvent) {
        menu = true;
        reset();
      }
    });

    /**
     * Event Handler for the Restart Button.
     */
    retry1.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent mouseEvent) {
        reset();
      }
    });

    /**
     * Event Handler for the Menu Button.
     */
    menuButton1.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent mouseEvent) {
        menu = true;
        reset();
      }
    });

    /**
     * Event Handler for the Start Game Button. Disables the menu, resets the car.
     */
    start.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent mouseEvent) {
        menu = false;
        reset();
      }
    });

    /**
     * Event Handler for the Exit Button. Calls the confirmation alert.
     */
    exit.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent mouseEvent) {
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

      // call pause if not in the menu
      if (!menu) {
        pause();
      }

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