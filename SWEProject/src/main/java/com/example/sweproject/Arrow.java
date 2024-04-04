package com.example.sweproject;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

import static com.example.sweproject.GameLauncher.*;
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
        NORTHEAST,
        EAST,
        SOUTHEAST,
        SOUTHWEST,
        WEST,
        NORTHWEST;
        //this method will help make changing the direction of the ray easier
        public Direction iterate(int i) {
            return values()[(ordinal() + i) % 6];
        }
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
            int xCord = coord[0];
            int yCord = coord[1];
            //getting the coordinates of the center of the hexagons we need
            double rayEndX = coordinatesOfCenters[xCord][yCord].getX();
            double rayEndY = coordinatesOfCenters[xCord][yCord].getY();
            double rayStartX = x;
            double rayStartY = y;

            boolean absorbed = false;

            while (isValidCoordinate(xCord, yCord) && !absorbed) {

                if (isValidCoordinate(xCord, yCord) && coordinatesOfCenters[xCord][yCord].getNoOfAreaOfInfluence() != 0) {
                        Coordinate nextPoint = coordinatesOfCenters[xCord][yCord];
                    System.out.println("detected");
                        if (nextPoint.getPointOfAreaOfInfluence() == this.arrowDirection.iterate(3)) { //iterating by 3 produces the opposite direction so there is a direct collision
                            absorbed = true;
                            System.out.println("absorbed");
                        } else if (nextPoint.getNoOfAreaOfInfluence() == 1) {   //when there is a single collision i.e. 60 degrees
                            System.out.println(arrowDirection);

                            if(nextPoint.getPointOfAreaOfInfluence().iterate(2) == this.arrowDirection) {
                                this.arrowDirection = nextPoint.getPointOfAreaOfInfluence().iterate(1);
                            } else{
                                this.arrowDirection = nextPoint.getPointOfAreaOfInfluence().iterate(5);
                            }

                            System.out.println("detected1");
                            System.out.println(arrowDirection);
                        } else if (nextPoint.getNoOfAreaOfInfluence() == 2) {   //when there is two collisions i.e. 120 degrees
                            System.out.println(arrowDirection);
                            this.arrowDirection = this.arrowDirection.iterate(2);
                            System.out.println("detected2");
                            System.out.println(arrowDirection);
                        } else {                                                //when there are greater than 2 i.e. 180 degrees
                            System.out.println(arrowDirection);
                            this.arrowDirection = this.arrowDirection.iterate(3);
                            System.out.println("detected3");
                            System.out.println(arrowDirection);
                        }
                    }

                // Create and add the ray
                Ray ray = new Ray(rayStartX, rayStartY, rayEndX, rayEndY, arrowDirection);
                root.getChildren().add(ray.getOutline());

                // Update ray starting position for the next iteration
                rayStartX = rayEndX;
                rayStartY = rayEndY;

                // Move to the next coordinate based on arrow direction
                switch (arrowDirection) {
                    case EAST:
                        yCord++;
                        break;
                    case WEST:
                        yCord--;
                        break;
                    case NORTHWEST:
                        xCord--;
                        if(xCord < 4)
                            yCord--;
                        break;
                    case NORTHEAST:
                        xCord--;
                        if(xCord>3)
                            yCord++;
                        break;
                    case SOUTHEAST:
                        xCord++;
                        if(xCord<5)
                            yCord++;
                        break;
                    case SOUTHWEST:
                        xCord++;
                        if(xCord>4)
                            yCord--;
                        break;
                }


                // Update ray ending position if the next coordinate is valid
                if (isValidCoordinate(xCord, yCord)) {
                    rayEndX = coordinatesOfCenters[xCord][yCord].getX();
                    rayEndY = coordinatesOfCenters[xCord][yCord].getY();
                }
                }
        });
    }

    //function to check validity
    private boolean isValidCoordinate(int x, int y) {
        return x >= 0 && x < coordinatesOfCenters.length &&
                y >= 0 && y < coordinatesOfCenters[x].length && coordinatesOfCenters[x][y] != null;
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
