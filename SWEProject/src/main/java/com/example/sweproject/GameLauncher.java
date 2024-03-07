package com.example.sweproject;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GameLauncher extends Application {
    public static int AtomCount = 0;
    public static Stage stage;
    public static Group root;
    public static Scene scene ;
    //variable used to track the number of arrows
    public static int numberLabel = 0;

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
        //top face
        for (int i = 0; i < 10; i++)
        {
            double x = 34.7 * i + 235;
            double y = 40;

            Arrow arrow = new Arrow(x, y, 10);
            // Rotate the arrow by 60 degrees
            if(i%2 == 0) {
                arrow.getArrow().setRotate(210);
            }else{
                arrow.getArrow().setRotate(30);
            }
            root.getChildren().add(arrow.getArrow());
        }

        //fourth face right-angled arrows
        for (int i = 0; i < 5; i++)
        {
            double x = 561.4 + (i * 34.8);
            double y = 65 + (i * 60) ;
            Arrow arrow = new Arrow(x, y, 10);
            // Rotate the arrow by 30 degrees
            arrow.getArrow().setRotate(90);
            root.getChildren().add(arrow.getArrow());

            if(i < 4)
            {
                x = 583 + (i * 34.8);
                y = 99.5 + (i * 60);
                Arrow arrow1 = new Arrow(x, y, 10);
                // Rotate the arrow by 30 degrees
                arrow1.getArrow().setRotate(30);
                root.getChildren().add(arrow1.getArrow());
            }
        }
        //fifth face right-angled arrows
        for (int i = 0; i < 4; i++)
        {

            double x = 687 - (i * 34.8);
            double y = 332 + (i * 60);
            Arrow arrow1 = new Arrow(x, y, 10);
            // Rotate the arrow by 30 degrees
            arrow1.getArrow().setRotate(30);
            root.getChildren().add(arrow1.getArrow());

            x = 665.7 - (i * 34.8);
            y = 366.5 + (i * 60) ;
            Arrow arrow = new Arrow(x, y, 10);
            // Rotate the arrow by 30 degrees
            arrow.getArrow().setRotate(90);
            root.getChildren().add(arrow.getArrow());

        }
        //bottom face
        for (int i = 9; i >= 0; i--)
        {
            double x = 34.8 * i + 235;
            double y;
            if(i%2 == 0){
                y = 570.6;
            }else{
                y = 573.5;
            }
            Arrow arrow = new Arrow(x, y, 10);
            // Rotate the arrow by 60 degrees
            if(i%2 == 0) {
                arrow.getArrow().setRotate(90);
            }else{
                arrow.getArrow().setRotate(30);
            }
            root.getChildren().add(arrow.getArrow());
        }
        //third face right-angled arrows
        for (int i = 3; i >= 0; i--)
        {
            double x = 120.4 + (i * 34.8);
            double y = 366.5 + (i * 60) ;
            Arrow arrow = new Arrow(x, y, 10);
            // Rotate the arrow by 30 degrees
            arrow.getArrow().setRotate(30);
            root.getChildren().add(arrow.getArrow());

            x = 97.6 + (i * 34.8);
            y = 331 + (i * 60);
            Arrow arrow1 = new Arrow(x, y, 10);
            // Rotate the arrow by 30 degrees
            arrow1.getArrow().setRotate(90);
            root.getChildren().add(arrow1.getArrow());
        }

        //second face right-angled arrows
        for (int i = 4 ; i >= 0; i--)
        {
            if(i < 4)
            {
                double x = 202 - (i * 34.8);
                double y = 98.5 + (i * 60);
                Arrow arrow1 = new Arrow(x, y, 10);
                // Rotate the arrow by 30 degrees
                arrow1.getArrow().setRotate(90);
                root.getChildren().add(arrow1.getArrow());
            }
            double x = 224.8 - (i * 34.8);
            double y = 65 + (i * 60) ;
            Arrow arrow = new Arrow(x, y, 10);
            // Rotate the arrow by 30 degrees
            arrow.getArrow().setRotate(30);
            root.getChildren().add(arrow.getArrow());


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

