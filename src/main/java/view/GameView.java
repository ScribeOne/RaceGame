package view;

import application.Main;
import controller.Settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import model.Obstacle;
import model.Track;
import model.Vector2D;

/**
 * Contains all GUI Elements
 */
public class GameView {

    private Canvas gameCanvas;
    private GraphicsContext gc;
    private Scene scene;
    private Stage stage;
    private StackPane rootPane;
    private Pane gamePane, infoPane, helpPane, menuPane;
    private Label fps, carPos, velo, dir, tracker;
    private boolean infoShown, helpShown, menuShown;
    private Button startButton, exitButton;
    private double time;
    private int sec, min;


    /**
     * GameView object for setting up the GUI
     *
     * @param stage the primary stage
     */
    public GameView(Stage stage) {
        // set stage
        this.stage = stage;
        stage.setTitle("Race Game v1.1");
        stage.setResizable(false);
        stage.sizeToScene();

        // create Stackpane and add to scene
        rootPane = new StackPane();
        scene = new Scene(rootPane, Settings.WIDTH, Settings.HEIGHT);

        // create all content panes that get stacked up on the root pane
        setUpGamePane();
        setUpInfoPane();
        setUpHelpPane();
        setUpMenuPane();


        // load custom font
        Font.loadFont(
                Main.class.getResource("/style/tagtype-freefont.ttf").toExternalForm(),
                10
        );

        //Set style for the different Panes
        menuPane.getStylesheets().add(Settings.MENUCSS);

        menuPane.getStyleClass().add("background");

        //set scene to stage
        stage.setScene(scene);
    }

    public void updateTime(double elapsed) {
        time += elapsed;
        sec = (int) time % 60;
        min = (int) ((time / (60)) % 60);
        System.out.println(String.format("%02d min, %02d sec", min, sec));
        gc.setFill(Color.BLACK);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.setFont(new Font(40));
        gc.fillText(String.format("%02d:%02d", min, sec),
                Math.round(Settings.WIDTH / 2),
                Math.round(Settings.HEIGHT / 2)
        );
    }

    public void resetTimer() {
        time = 0;
    }


    private void setUpMenuPane() {
        menuPane = new Pane();
        menuPane.setPrefSize(Settings.WIDTH, Settings.HEIGHT);
        VBox menuBox = new VBox(20);
        menuBox.setPrefSize(Settings.WIDTH, Settings.HEIGHT - 150);
        menuBox.setAlignment(Pos.CENTER);
        startButton = new Button();
        startButton.setPrefSize(250, 25);
        startButton.setText("START GAME");
        exitButton = new Button();
        exitButton.setPrefSize(250, 25);
        exitButton.setText("EXIT");
        startButton.getStyleClass().add("menu-button");
        exitButton.getStyleClass().add("menu-button");
        menuBox.getChildren().addAll(startButton, exitButton);
        menuPane.getChildren().addAll(menuBox);
    }


    private void setUpHelpPane() {
        helpPane = new Pane();
        Rectangle helpWindow = new Rectangle(500, 500);
        helpWindow.setLayoutX(200);
        helpWindow.setLayoutY(250);
        helpWindow.setFill(Color.BLACK);
    }


    /**
     * Sets up the main game window, create Canvas and its GraphicsContext.
     */
    private void setUpGamePane() {
        gamePane = new Pane();
        gameCanvas = new Canvas(Settings.WIDTH, Settings.HEIGHT);
        gamePane.getChildren().addAll(gameCanvas);
        gc = gameCanvas.getGraphicsContext2D();
        rootPane.getChildren().add(gamePane);
    }

    /**
     * create InfoBox with all labels and add it to the stackpane.
     */
    private void setUpInfoPane() {
        infoPane = new Pane();
        VBox infoBox = new VBox();
        infoBox.setPrefWidth(250);
        infoBox.setPrefHeight(250);
        fps = new Label();
        fps.setPrefSize(250, 10);
        fps.setText("FPS: ");
        fps.setTextFill(Settings.INFOCOLOR);
        carPos = new Label();
        carPos.setPrefSize(250, 10);
        carPos.setText("Position: ");
        carPos.setTextFill(Settings.INFOCOLOR);
        velo = new Label();
        velo.setPrefSize(250, 10);
        velo.setText("Velocity: ");
        velo.setTextFill(Settings.INFOCOLOR);
        dir = new Label();
        dir.setPrefSize(250, 10);
        dir.setText("Direction: ");
        dir.setTextFill(Settings.INFOCOLOR);
        tracker = new Label();
        tracker.setPrefSize(250, 10);
        tracker.setText("Car is on track: ");
        tracker.setTextFill(Settings.INFOCOLOR);
        infoBox.getChildren().addAll(fps, carPos, velo, dir, tracker);
        infoPane.getChildren().add(infoBox);
    }

    /**
     * update Infobox labels with information from the model.
     */
    public void updateInfo(long fps, Vector2D position, double velocity, double direction,
                           boolean carOnTrack) {
        this.fps.setText("FPS: " + fps);
        carPos.setText(
                "Position: [" + Math.round(position.getX()) + " | " + Math.round(position.getY()) + "]");
        velo.setText("Velocity: " + velocity + " pixel/sec");
        dir.setText("Direction: " + Math.round(direction) + "°");
        tracker.setText("Car is on track: " + carOnTrack);
    }


    /**
     * draw the pause overlay on the canvas with text and transparent box.
     */
    public void showPause() {
        gc.setFill(Settings.PAUSECOLOR);
        gc.fillRect(0, 0, Settings.WIDTH, Settings.HEIGHT);
        gc.setFill(Color.BLACK);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.setFont(new Font(40));
        gc.fillText(
                "Paused\n Press P to continue",
                Math.round(gameCanvas.getWidth() / 2),
                Math.round(gameCanvas.getHeight() / 2)
        );
    }


    /**
     * draw the car at its current position.
     */
    public void renderCar(Vector2D position, double newAngle) {
        Image blueCar = new Image(Settings.CARPATH);
        double pivotX = (position.getX() + Settings.meterToPixel(Settings.CARWIDTH) / 2);
        double pivotY = (position.getY() + Settings.meterToPixel(Settings.CARHEIGHT) / 2);
        gc.save();
        gc.transform(new Affine(new Rotate(newAngle, pivotX, pivotY)));
        gc.setFill(new ImagePattern(blueCar, 0, 0, 1, 1, true));
        gc.fillRect(position.getX(), position.getY(), Settings.meterToPixel(Settings.CARWIDTH),
                Settings.meterToPixel(Settings.CARHEIGHT));
        gc.restore();

    }

    /**
     * Render Track including background. Color of background and track can be changed in "Setting.java".
     */
    public void renderTrack(Track track) {

        //Background

        gc.setFill(Settings.BACKGROUND);
        gc.fillRect(0, 0, Settings.WIDTH, Settings.HEIGHT);

        //calculate numbers that javaFX needs to draw a ellipse
        double innerCornerX = (Settings.WIDTH - track.getInnerRadiusX() * 2) / 2;
        double innerCornerY = (Settings.HEIGHT - track.getInnerRadiusY() * 2) / 2;
        double outerCornerX = (Settings.WIDTH - track.getOuterRadiusX() * 2) / 2;
        double outerCornerY = (Settings.HEIGHT - track.getOuterRadiusY() * 2) / 2;

        //Outer Ellipse
        gc.setFill(Settings.STREETCOLOR);
        gc.fillOval(outerCornerX, outerCornerY, track.getOuterRadiusX() * 2,
                track.getOuterRadiusY() * 2);

        //Inner Ellipse
        gc.setFill(Settings.BACKGROUND);
        gc.fillOval(innerCornerX, innerCornerY, track.getInnerRadiusX() * 2,
                track.getInnerRadiusY() * 2);

        // Finish and Checkpoint
        gc.setLineDashes(8);
        gc.setLineWidth(6);
        gc.setStroke(Color.WHITE);
        gc.strokeLine(track.getFinishUp().getX(), track.getFinishUp().getY(),
                track.getFinishDown().getX(), track.getFinishDown().getY());
        gc.strokeLine(track.getCheckpointUp().getX(), track.getCheckpointUp().getY(),
                track.getCheckPointDown().getX(), track.getCheckPointDown().getY());

        //border of the track
        gc.setStroke(Color.RED);
        gc.setLineWidth(2);
        gc.setLineDashes(0);
        gc.strokeOval(outerCornerX, outerCornerY, track.getOuterRadiusX() * 2,
                track.getOuterRadiusY() * 2);
        gc.strokeOval(innerCornerX, innerCornerY, track.getInnerRadiusX() * 2,
                track.getInnerRadiusY() * 2);

        gc.setStroke(Color.WHITE);
        gc.setLineWidth(2);
        gc.setLineDashes(20);
        gc.strokeOval(outerCornerX, outerCornerY, track.getOuterRadiusX() * 2,
                track.getOuterRadiusY() * 2);
        gc.strokeOval(innerCornerX, innerCornerY, track.getInnerRadiusX() * 2,
                track.getInnerRadiusY() * 2);

        //Obstacles
        gc.setFill(Settings.OBSTACLECOLOR);
        for (Obstacle obstacle : track.getObstacles()) {
            gc.fillOval(obstacle.getPosition().getX(), obstacle.getPosition().getY(),
                    obstacle.getRadius(), obstacle.getRadius());
        }


    }

    private void showMenu() {
        if (!menuShown) {
            rootPane.getChildren().add(menuPane);
            menuShown = true;
        }
    }

    private void removeMenu() {
        if (menuShown) {
            rootPane.getChildren().remove(menuPane);
            menuShown = false;
        }
    }

    public void setView(boolean showMenu, boolean showInfo) {
        if (showInfo && !infoShown) {
            rootPane.getChildren().add(infoPane);
            infoShown = true;
        } else if (!showInfo && infoShown) {
            rootPane.getChildren().remove(infoPane);
            infoShown = false;
        }
        if (showMenu) {
            showMenu();
        } else if (!showMenu && menuShown) {
            removeMenu();
        }
    }

    public void clear() {
        gc.clearRect(0, 0, Settings.WIDTH, Settings.HEIGHT);
    }

    public void showHelp() {
        if (!helpShown) {
            rootPane.getChildren().add(helpPane);
            helpShown = true;
        }
    }

    public void removeHelp() {
        if (helpShown) {
            rootPane.getChildren().remove(helpPane);
            helpShown = false;
        }
    }


    // Getter and Setter
    public Button getStartButton() {
        return startButton;
    }

    public Button getExitButton() {
        return exitButton;
    }

    public Stage getStage() {
        return stage;
    }

    public Scene getScene() {
        return scene;
    }
}