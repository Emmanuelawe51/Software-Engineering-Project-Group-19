package com.example.sweproject;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameLauncher extends Application {
    public static int AtomCount = 0;
    public static Stage stage;
    public static Group root;
    public static Scene scene ;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        root = new Group();
        scene =new Scene(root, 760, 600, Color.BLACK);
        stage.setResizable(false);
        startBoard();
    }

    public void startBoard()
    {
        //loading the main icon from the resources directory
        Image icon = new Image(getClass().getResource("/MainIcon.png").toExternalForm());
        stage.getIcons().add(icon);
        //side length and height of hexagon
        double side = 40;
        double height = side * Math.sqrt(3);
        // Maximum number of columns in the middle of the hexagonal grid
        int maxColumns = 9;
        // counter for adding Atoms

        // Create the top half of the hexagonal grid
        for (int i = 0; i < maxColumns / 2 + 1; i++) {
            for (int j = 0; j < i + 5; j++) {
                //sets x coordinate of the next hexagon and changes it by the height of each hexagon to put side by side
                double x = j * height + 100 + ((double) maxColumns / 2 - i) * height / 2;
                //changes y coordinate each time a hexagon is placed
                double y = i * 1.5 * side + 100;

                //calls create hexagon function to place it
                Hexagon hexagon = new Hexagon(x, y, side);
                //adds to stage
                root.getChildren().add(hexagon.getHexagon());
            }
        }

        // Create the bottom half of the hexagonal grid
        for (int i = maxColumns / 2 - 1; i >= 0; i--) {
            for (int j = 0; j < i + 5; j++) {
                double x = j * height + 100 + ((double) maxColumns / 2 - i) * height / 2;
                double y = (maxColumns - i - 1) * 1.5 * side + 100;

                Hexagon hexagon = new Hexagon(x, y, side);
                root.getChildren().add(hexagon.getHexagon());
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

}

