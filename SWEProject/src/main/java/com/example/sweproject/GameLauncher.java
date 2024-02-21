package com.example.sweproject;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

public class GameLauncher extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Stage stage = new Stage();
        Group root = new Group();
        Scene scene = new Scene(root, Color.BLACK);

        double side = 50;
        double height = side * Math.sqrt(3);
        int maxColumns = 9; // Maximum number of columns in the middle of the hexagonal grid

        // Create the top half of the hexagonal grid
        for (int i = 0; i < maxColumns / 2 + 1; i++) {
            for (int j = 0; j < i + 5; j++) {
                double x = j * height + 100 + ((double) maxColumns / 2 - i) * height / 2;
                double y = i * 1.5 * side + 100;
                Polygon hexagon = createHexagon(x, y, side);
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

        stage.setTitle("Black Box+");
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("Press Esc to leave full screen");

        stage.setScene(scene);
        stage.show();
    }


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

        hexagon.setFill(Color.TRANSPARENT); // This line makes the fill color of the hexagon transparent
        hexagon.setStroke(Color.YELLOW); // This line sets the outline color of the hexagon to yellow

        return hexagon;
    }
}

