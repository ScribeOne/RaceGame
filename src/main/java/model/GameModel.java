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
    private static final Vector2D initalPosition = new Vector2D(420.0f, 230.0f);
    private static final Vector2D initalSpeed = new Vector2D(0.0f, 0.0f);

    // setup race track
    private Track track;
    private Vector2D trackCenter;
    private Vector2D trackInnerRadius;
    private Vector2D trackOuterRadius;

    private int centerX = 650;
    private int centerY = 400;
    private int innerX = 900;
    private int innerY = 500;
    private int outerX = 1100;
    private int outerY = 700;


    /**
     * Creates a gameModel, that handles most of the actions
     */
    public GameModel() {
        //initialize Car, default data in GameView
        car = initializeCar();
        System.out.println(
                "Initial position: [" + car.getPosition().getX() + "|" + car.getPosition().getY() + "]");
        track = createTrack();
    }

    public void update(double timeDifferenceInSeconds) {
        //car.moveCar(timeDifferenceInSeconds);
        System.out
                .println("new Position: [" + getCarPosition().getX() + "|" + getCarPosition().getY() + "]");
    }


    public Vector2D getCarPosition() {
        return car.getPosition();
    }

    /**
     * Initializes the track with the initial values
     *
     * @return the initialized track
     */
    private Track createTrack() {
        trackCenter = new Vector2D(centerX, centerY);
        trackInnerRadius = new Vector2D(innerX, innerY);
        trackOuterRadius = new Vector2D(outerX, outerY);
        track = new Track(trackCenter, trackInnerRadius, trackOuterRadius);
        return track;
    }

    public Vector2D getTrackCenter() {
        return track.getCenter();
    }

    public Vector2D getTrackInnerRadius() {
        return track.getInnerRadius();
    }

    public Vector2D getTrackOuterRadius() {
        return track.getOuterRadius();
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