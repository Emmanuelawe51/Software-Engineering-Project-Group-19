package com.example.sweproject;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

public class GameLauncher extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //creating stage
        Stage stage = new Stage();
        Group root = new Group();
        Scene scene = new Scene(root, Color.BLACK);
        // this is the icon for the window of the game, you may need to change the source of the image location as this
        // was the only way I could get it to work rn
        Image icon = new Image("C:\\Users\\emman\\OneDrive\\Documents\\GitHub\\Software-Engineering-Projec\\SWEProject\\src\\main\\java\\com\\example\\sweproject\\MainIcon.png");
        stage.getIcons().add(icon);
        //side length and height of hexagon
        double side = 50;
        double height = side * Math.sqrt(3);
        // Maximum number of columns in the middle of the hexagonal grid
        int maxColumns = 9;

        // Create the top half of the hexagonal grid
        for (int i = 0; i < maxColumns / 2 + 1; i++) {
            for (int j = 0; j < i + 5; j++) {
                //sets x coordinate of the next hexagon and changes it by the height of each hexagon to put side by side
                double x = j * height + 100 + ((double) maxColumns / 2 - i) * height / 2;
                //changes y coordinate each time a hexagon is placed
                double y = i * 1.5 * side + 100;
                //calls create hexagon function to place it
                Polygon hexagon = createHexagon(x, y, side);
                //adds to stage
                root.getChildren().add(hexagon);
            }
        }

        // Create the bottom half of the hexagonal grid
        for (int i = maxColumns / 2 - 1; i >= 0; i--) {
            for (int j = 0; j < i + 5; j++) {
                double x = j * height + 100 + ((double) maxColumns / 2 - i) * height / 2;
                double y = (maxColumns - i - 1) * 1.5 * side + 100;
                Polygon hexagon = createHexagon(x, y, side);
                root.getChildren().add(hexagon);
            }
        }
        //sets title of window/stage
        stage.setTitle("Black Box+");

        //below code not needed right now
       // stage.setFullScreen(true);
       // stage.setFullScreenExitHint("Press Esc to leave full screen");
        //how stage is shown
        stage.setScene(scene);
        stage.show();
    }
    // counter for adding Atoms
    private int AtomCount = 0;

    //my class to create the hexagons taking in the 3 parameters
    private Polygon createHexagon(double x, double y, double side) {
        Polygon hexagon = new Polygon();

        double height = side * Math.sqrt(3);

        hexagon.getPoints().addAll(
                x, y,
                x + height / 2, y - side / 2,
                x + height / 2, y - 1.5 * side,
                x, y - 2 * side,
                x - height / 2, y - 1.5 * side,
                x - height / 2, y - side / 2
        );
        // This line makes the fill color of the hexagon transparent
        hexagon.setFill(Color.TRANSPARENT);
        // This line sets the outline color of the hexagon to yellow
        hexagon.setStroke(Color.YELLOW);

        // Add a click event handler to the hexagon
        hexagon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            // Only add an Atom if there are less than 6
            if (AtomCount < 6) {
                // sets the positioning of the atom to the centre of the hexagon
                Circle Atom = new Circle(x, y - side, side / 2);
                // coloring of the Atom
                RadialGradient gradient = new RadialGradient(
                        0, 0, 0.5, 0.5, 0.5, true, CycleMethod.NO_CYCLE,
                        new Stop(0, Color.RED),
                        new Stop(1, Color.DARKRED)
                );
                Atom.setFill(gradient);
                // Add the Atom to the same parent as the hexagon
                ((Group) hexagon.getParent()).getChildren().add(Atom);
                // Increment the Atom counter
                AtomCount++;
            }
        });
        return hexagon;
    }
}

