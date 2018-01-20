package model;

/**
 * The GameModel saves data about the game, including the racecar.
 * It handles most of the calculations for the racegame.
 */
public class GameModel {

  /**
   * The car that is driven on the racetrack
   */
  private Car car;

  /**
   * initial position of the race car.
   */
  private static final Vector2D initalPosition = new Vector2D(42.0f, 23.0f);
  private static final Vector2D initalSpeed = new Vector2D(0.0f, 0.0f);


  private Track track;




  /**
   * Creates a gameModel, that handles most of the actions
   */
  public GameModel() {
    //initialize Car, default data in GameView
    car = initializeCar();
    System.out.println("Current position: [" + car.getPosition().getX() + "|" + car.getPosition().getY() +"]");

    track = createTrack();

  }

  public Vector2D getCarPosition() {
    return car.getPosition();
  }


  /**
   * Initializes the track with the initial values
   *
   * @return the initialized car
   */
  private Track createTrack() {

    return track;
  }



  /**
   * Initializes a car with the initial values
   *
   * @return the initialized car
   */
  private Car initializeCar() {
    //initialize a new car and give it the init values set in the static variables
    car = new Car(initalPosition, initalSpeed);
    return car;
  }
}