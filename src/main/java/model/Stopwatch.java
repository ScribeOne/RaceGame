package model;

/**
 * Class to stop the time. Used for trackTimeDisplay by the game controller.
 */
public class Stopwatch {

  private long startTime;
  private long stopTime;
  private long pauseTime;
  private boolean running;
  private boolean paused;
  private boolean elapsedTime;

  /**
   * Default constructor
   */
  public Stopwatch() {
    this.startTime = 0;
    this.stopTime = 0;
    this.pauseTime = 0;
    this.running = false;
    this.paused = false;
  }

  public void start() {
    if (!running) {
      startTime = System.currentTimeMillis();
      paused = false;
      running = true;
      pauseTime = -1;
    }
  }

  /**
   * Stops the stopwatch and returns the time elapsed
   *
   * @return time elapsed
   */
  public long stop() {
    if (!running) {
      return -1;
    } else if (paused) {
      running = false;
      paused = false;

      return pauseTime - startTime;
    } else {
      stopTime = System.currentTimeMillis();
      running = false;
      return stopTime - startTime;
    }
  }

  /**
   * Pauses the Stopwatch
   *
   * @return The time elapsed so far
   */
  public long pause() {
    if (!running) {
      return -1;
    } else if (paused) {
      return (pauseTime - startTime);
    } else {
      pauseTime = System.currentTimeMillis();
      paused = true;
      return (pauseTime - startTime);
    }
  }


  public void reset() {
    stop();
    startTime = 0;
    stopTime = 0;
  }

  /**
   * Resumes the StopWatch from it's paused state
   */
  public void resume() {
    if (paused && running) {
      startTime = System.currentTimeMillis() - (pauseTime - startTime);
      paused = false;
    }
  }


  /**
   * get the time elapsed so far.
   *
   * @return time elapsed in milliseconds
   */
  public long getElapsedTime() {
    if (running) {
      if (paused) {
        return ((pauseTime - startTime) / 1000);
      }
      return (System.currentTimeMillis() - startTime) / 1000;
    } else {
      return (startTime - startTime) / 1000;
    }
  }

}
