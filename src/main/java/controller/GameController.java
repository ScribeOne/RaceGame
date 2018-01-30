package controller;

import java.util.Optional;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
import model.Stopwatch;
import view.GameView;

/**
 * Class to connect Model with View and handle user input.
 * The GameController gets called every time frame and updates game model with delta time.
 * View gets then updated with the model data.
 */
public class GameController {

  private GameView gameView;
  private GameModel gameModel;
  private Scene scene;
  private Stage stage;
  private boolean accelerate, steerLeft, steerRight, brake, pause, infoMode, menu, sound;
  private Stopwatch stopwatch;
  private MediaPlayer effectPlayer, engineSoundPlayer, musicPlayer;

  /**
   * constructor
   *
   * @param gameModel data
   * @param gameView view
   */
  public GameController(GameModel gameModel, GameView gameView) {
    this.gameView = gameView;
    this.gameModel = gameModel;
    this.scene = gameView.getScene();
    this.stage = gameView.getStage();

    this.stopwatch = new Stopwatch();

    setupEngineSound();
    setupMenuMusic();
    setupCarSound();
    setUpInputHandler();

    menu = true;
    infoMode = true;
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
   * plays all sounds if sound is turned on.
   */
  private void playSound() {
    if (sound) {
      playCarSound();
      playIntroMusic();
      playEngineRunningSound();
    }
  }

  /**
   * Setup the media player with the car engine sound.
   * Plays an infinite loop on calling play().
   * Does nothing when calling play() while already playing.
   * Has to be stopped with stop().
   */
  private void setupCarSound() {
    String musicFile = Settings.CARENGINESOUND;
    Media sound = new Media(this.getClass().getResource(musicFile).toExternalForm());
    effectPlayer = new MediaPlayer(sound);
    effectPlayer.setOnEndOfMedia(new Runnable() {
      public void run() {
        effectPlayer.seek(Duration.ZERO);
      }
    });
  }

  /**
   * setup Mediaplayer for sound of running engine.
   */
  private void setupEngineSound() {
    String mediafile = Settings.ENGINERUNNING;
    Media sound = new Media(this.getClass().getResource(mediafile).toExternalForm());
    engineSoundPlayer = new MediaPlayer(sound);
    engineSoundPlayer.setOnEndOfMedia(new Runnable() {
      public void run() {
        engineSoundPlayer.seek(Duration.ZERO);
      }
    });
  }

  /**
   * setup of Mediaplayer for menu music.
   */
  private void setupMenuMusic() {
    String musicFile = Settings.INTRO;
    Media sound = new Media(this.getClass().getResource(musicFile).toExternalForm());
    musicPlayer = new MediaPlayer(sound);
    musicPlayer.setOnEndOfMedia(new Runnable() {
      public void run() {
        musicPlayer.seek(Duration.ZERO);
      }
    });
  }

  /**
   * play the Intro Music if menu is shown.
   */
  private void playIntroMusic() {
    if (menu) {
      musicPlayer.play();
    } else {
      musicPlayer.stop();
    }
  }

  /**
   * play the engine running sound if car is moving.
   */
  private void playEngineRunningSound() {
    if (gameModel.getCar().isMoving()) {
      engineSoundPlayer.play();
    } else {
      engineSoundPlayer.stop();
    }
  }


  /**
   * play the engine sound if car is accelerating.
   */
  private void playCarSound() {
    if (accelerate) {
      effectPlayer.play();
    } else {
      effectPlayer.stop();
    }
  }


  /**
   * Render the content to the screen. Calls methods in the game view.
   */
  private void renderGameView() {
    gameView.setView(menu, infoMode);
        /*
        counter++;
        timeBuffer += delta;
        if (counter == 60) {
            if (infoMode) {
                fpsTimer = Math.round(timeBuffer * 60);
            }
            counter = 0;
            timeBuffer = 0;
        }
        */

    gameView.clear();
    gameView.renderTrack(gameModel.getTrack());
    gameView.printTrackTime(stopwatch.getElapsedTime());
    gameView.renderCar(gameModel.getCarPosition(), gameModel.getCarAngle());
    gameView.setPause(pause);
    if (infoMode) {
      gameView.updateInfo(60, gameModel.getCarPosition(), gameModel.getCar().getVelocity(),
          gameModel.getCar().getAngle(),
          gameModel.getTrack().isOnTrack(gameModel.getCarPosition()));
    }
  }

  /**
   * Update the game model with user input for car control and update the car with new delta time.
   *
   * @param delta elapsed time
   */
  private void updateGameModel(double delta) {
    if (!pause) {
      gameModel.updateCarControl(accelerate, steerLeft, steerRight, brake);
      gameModel.updateCar(delta);
    }
  }

  /**
   * turn sound on and off, stops all sound when turning off.
   */
  private void toggleSound() {
    if(sound){
      musicPlayer.stop();
      engineSoundPlayer.stop();
      effectPlayer.stop();
    }
    sound = !sound;
  }

  /**
   * turn info box on and off
   */
  private void toggleInfo() {
    infoMode = !infoMode;
    System.out.println("info mode: " + infoMode);
  }


  /**
   * reset car and stopwatch
   */
  private void reset() {
    gameModel.resetCar();
    stopwatch.start();
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
     * Event Handler for the Start Game Button. Disables the menu, resets the car.
     */
    start.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent mouseEvent) {
        System.out.println("mouse click detected! " + mouseEvent.getSource());
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
