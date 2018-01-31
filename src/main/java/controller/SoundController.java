package controller;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 * Controller Class for all Sounds.
 * Sounds can be turned on and off with flags by the game controller.
 */
public class SoundController {


  private MediaPlayer effectPlayer, engineSoundPlayer, musicPlayer, explosionPlayer;
  private boolean menu, alive, accelerate, isMoving;

  /**
   * Constructor
   */
  public SoundController(){
    setupEngineSound();
    setupMenuMusic();
    setupCarSound();
    setupExplosionSound();
  }

  /**
   * update flags
   */
  public void update(boolean menu, boolean alive, boolean accelerate, boolean isMoving){
    this.menu = menu;
    this.alive = alive;
    this.accelerate = accelerate;
    this.isMoving = isMoving;
  }

  /**
   * plays all sounds if sound is turned on.
   */
  public void playSound(boolean sound) {
    if (sound) {
      playCarSound();
      playIntroMusic();
      playEngineRunningSound();
      playExplosionSound();
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


  private void setupExplosionSound() {
    String mediafile = Settings.EXPOSIONSOUND;
    Media sound = new Media(this.getClass().getResource(mediafile).toExternalForm());
    explosionPlayer = new MediaPlayer(sound);
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

  private void playExplosionSound() {
    if (!alive && !menu) {
      explosionPlayer.play();
    } else {
      explosionPlayer.stop();
    }
  }

  /**
   * play the engine running sound if car is moving.
   */
  private void playEngineRunningSound() {
    if (isMoving) {
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

  public void setMenu(boolean menu) {
    this.menu = menu;
  }

  public void setAlive(boolean alive) {
    this.alive = alive;
  }

  public void setAccelerate(boolean accelerate) {
    this.accelerate = accelerate;
  }

  public void setMoving(boolean moving) {
    isMoving = moving;
  }
}
