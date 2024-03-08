package com.example.sweproject;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

import static com.example.sweproject.GameLauncher.numberLabel;
import static com.example.sweproject.GameLauncher.root;
import static javafx.scene.paint.Color.RED;
import static javafx.scene.paint.Color.WHITE;

public class Arrow {

    private double x;
    private double y;
    //stores the place in the 2d array the arrow is
    private int[] coord = new int[2];
    private double side;
    private Polygon arrow;
    //Arrow takes in the x and y coordiantes you want to print the arrow and the side length
    public static enum Direction {
        EAST,
        NORTHEAST,
        SOUTHEAST,
        WEST,
        SOUTHWEST,
        NORTHWEST
    }
    private Direction arrowDirection;

    private int arrowNum;
    public Arrow(double x, double y, double side, Direction direction) {
        this.x = x;
        this.y = y;
        this.side = side;
        this.arrow = createArrow();
        this.arrowDirection = direction;
        addMouseEnterHandler();
        addMouseExitHandler();
        ShootRay();
    }
    public Arrow(double x, double y, double side, Direction direction, int[] coord) {
        this.x = x;
        this.y = y;
        this.side = side;
        this.arrow = createArrow();
        this.arrowDirection = direction;
        this.coord = coord;
        addMouseEnterHandler();
        addMouseExitHandler();
        ShootRay();
    }

    //creates the arrow
    private Polygon createArrow()
    {
        Polygon arrow = new Polygon();

        double height = side * Math.sqrt(3);

        arrow.getPoints().addAll(
                x, y,
                x + height / 3, y - side,
                x - height / 3, y - side
        );
        // This line makes the fill color of the arrow transparent
        arrow.setFill(Color.TRANSPARENT);
        // This line sets the outline color of the arrow to yellow
        arrow.setStroke(Color.YELLOW);

        //printing the numbers of the arrows
        numberLabel++;
        this.arrowNum = numberLabel;
        Text text = new Text(Integer.toString(numberLabel));

        //logic to push the number out
        if (arrowNum <= 10) {
            text.setX(x);
            text.setY(y - 15);
        } else if (arrowNum <= 19){
            if(arrowNum%2 == 0)
            {
                text.setX(x);
                text.setY(y - 10);
            } else {
                text.setX(x + 10);
                text.setY(y);
            }
        } else if (arrowNum <= 28) {

            if (arrowNum % 2 == 0) {
                text.setX(x);
                text.setY(y + 10);
            } else {
                text.setX(x + 10);
                text.setY(y);
            }
        } else if (arrowNum <= 37) {

            if (arrowNum % 2 == 0) {
                text.setX(x);
                text.setY(y + 10);
            } else {
                text.setX(x - 8);
                text.setY(y + 10);
            }
        } else if (arrowNum <= 45) {

            if (arrowNum % 2 == 0) {
                text.setX(x - 18);
                text.setY(y);
            } else {
                text.setX(x - 4);
                text.setY(y + 11);
            }
        } else if (arrowNum <= 55) {

            if (arrowNum % 2 == 0) {
                text.setX(x - 18);
                text.setY(y);
            } else {
                text.setX(x - 10);
                text.setY(y - 11);
            }
        }
        text.setFill(WHITE);
        text.setStroke(RED);
        text.setStrokeWidth(0.2);

        root.getChildren().add(text);

        return arrow;
    }

    public Polygon getArrow() {
        return arrow;
    }
    public void ShootRay() {
        arrow.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
            //when the mouse is clicked set the coords to the coords initialised
            int xCord = coord[0];
            int yCord = coord[1];
            //getting the coordinates of the center of the hexagons we need
            double rayEndX = GameLauncher.coordinatesOfCenters[xCord][yCord].getX();
            double rayEndY = GameLauncher.coordinatesOfCenters[xCord][yCord].getY();
            //starting with the arrows location
            double rayStartX = x;
            double rayStartY = y;

            if (arrowDirection == Direction.EAST) {

                //while the coordinate is valid
                while (isValidCoordinate(xCord, yCord)) {
                    Ray ray = new Ray(rayStartX, rayStartY, rayEndX, rayEndY, arrowDirection);
                    root.getChildren().add(ray.getOutline());

                    //reseting the starting position of the next ray to the end of this one
                    rayStartX = rayEndX;
                    rayStartY = rayEndY;

                    // Move to the next coordinate
                    yCord++;
                    if (isValidCoordinate(xCord, yCord)) {
                        rayEndX = GameLauncher.coordinatesOfCenters[xCord][yCord].getX();
                        rayEndY = GameLauncher.coordinatesOfCenters[xCord][yCord].getY();
                    }
                }
            } else if (arrowDirection == Direction.WEST) {
                while (isValidCoordinate(xCord, yCord)) {
                    Ray ray = new Ray(rayStartX, rayStartY, rayEndX, rayEndY, arrowDirection);
                    root.getChildren().add(ray.getOutline());

                    //reseting the starting position of the next ray to the end of this one
                    rayStartX = rayEndX;
                    rayStartY = rayEndY;

                    // Move to the next coordinate
                    yCord--;
                    if (isValidCoordinate(xCord, yCord)) {
                        rayEndX = GameLauncher.coordinatesOfCenters[xCord][yCord].getX();
                        rayEndY = GameLauncher.coordinatesOfCenters[xCord][yCord].getY();
                    }
                }

            } else if (arrowDirection == Direction.NORTHWEST) {
                while (isValidCoordinate(xCord, yCord)) {
                    Ray ray = new Ray(rayStartX, rayStartY, rayEndX, rayEndY, arrowDirection);
                    root.getChildren().add(ray.getOutline());

                    //reseting the starting position of the next ray to the end of this one
                    rayStartX = rayEndX;
                    rayStartY = rayEndY;

                    // Move to the next coordinate
                    xCord--;
                    if(xCord < 4)
                        yCord--;
                    if (isValidCoordinate(xCord, yCord)) {
                        rayEndX = GameLauncher.coordinatesOfCenters[xCord][yCord].getX();
                        rayEndY = GameLauncher.coordinatesOfCenters[xCord][yCord].getY();
                    }
                }

            } else if (arrowDirection == Direction.NORTHEAST) {
                while (isValidCoordinate(xCord, yCord)) {
                    Ray ray = new Ray(rayStartX, rayStartY, rayEndX, rayEndY, arrowDirection);
                    root.getChildren().add(ray.getOutline());

                    //reseting the starting position of the next ray to the end of this one
                    rayStartX = rayEndX;
                    rayStartY = rayEndY;

                    // Move to the next coordinate
                    xCord--;

                    if(xCord>3)
                        yCord++;

                    if (isValidCoordinate(xCord, yCord)) {
                        rayEndX = GameLauncher.coordinatesOfCenters[xCord][yCord].getX();
                        rayEndY = GameLauncher.coordinatesOfCenters[xCord][yCord].getY();
                    }
                }

            } else if (arrowDirection == Direction.SOUTHEAST) {
                while (isValidCoordinate(xCord, yCord)) {
                    Ray ray = new Ray(rayStartX, rayStartY, rayEndX, rayEndY, arrowDirection);
                    root.getChildren().add(ray.getOutline());

                    //reseting the starting position of the next ray to the end of this one
                    rayStartX = rayEndX;
                    rayStartY = rayEndY;

                    // Move to the next coordinate
                    xCord++;
                    if(xCord<5)
                    yCord++;


                    if (isValidCoordinate(xCord, yCord)) {
                        rayEndX = GameLauncher.coordinatesOfCenters[xCord][yCord].getX();
                        rayEndY = GameLauncher.coordinatesOfCenters[xCord][yCord].getY();
                    }
                }

            } else if (arrowDirection == Direction.SOUTHWEST) {
                while (isValidCoordinate(xCord, yCord)) {
                    Ray ray = new Ray(rayStartX, rayStartY, rayEndX, rayEndY, arrowDirection);
                    root.getChildren().add(ray.getOutline());

                    //reseting the starting position of the next ray to the end of this one
                    rayStartX = rayEndX;
                    rayStartY = rayEndY;

                    // Move to the next coordinate
                    xCord++;
                    if(xCord>4)
                        yCord--;


                    if (isValidCoordinate(xCord, yCord)) {
                        rayEndX = GameLauncher.coordinatesOfCenters[xCord][yCord].getX();
                        rayEndY = GameLauncher.coordinatesOfCenters[xCord][yCord].getY();
                    }
                }

            }
        });
    }
    //function to check validity
    private boolean isValidCoordinate(int x, int y) {
        return x >= 0 && x < GameLauncher.coordinatesOfCenters.length &&
                y >= 0 && y < GameLauncher.coordinatesOfCenters[x].length && GameLauncher.coordinatesOfCenters[x][y] != null;
    }

    //resets the colour of the arrow when the mouse is no longer hovering
    private void addMouseExitHandler() {arrow.setOnMouseExited(event -> {
        arrow.setFill(Color.TRANSPARENT);
    });
    }
    //sets the colour of the arrow when the mouse is hovering above the arrow
    private void addMouseEnterHandler() {
        arrow.setOnMouseEntered(event -> {
            arrow.setFill(Color.DARKRED);
        });
    }
}
