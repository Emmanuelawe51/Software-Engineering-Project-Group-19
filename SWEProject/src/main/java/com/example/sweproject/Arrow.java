package com.example.sweproject;

import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

import static com.example.sweproject.GameLauncher.*;
import static javafx.scene.paint.Color.RED;
import static javafx.scene.paint.Color.WHITE;


public class Arrow {
    private boolean rayShot = false;
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
            if(GameLauncher.round % 2 != 0){
                pOneScore = pOneScore + 1;
            }else{
                pTwoscore = pTwoscore + 1;
            }
        });

        arrow.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
            if(!rayShot) {
                int xCord = coord[0];
                int yCord = coord[1];
                //getting the coordinates of the center of the hexagons we need
                double rayEndX = coordinatesOfCenters[xCord][yCord].getX();
                double rayEndY = coordinatesOfCenters[xCord][yCord].getY();
                double rayStartX = x;
                double rayStartY = y;

                boolean absorbed = false;

                while (isValidCoordinate(xCord, yCord) && !absorbed) {

                    if (isValidCoordinate(xCord, yCord) && coordinatesOfCenters[xCord][yCord].getDeflectionType() != 0) {
                        Coordinate nextPoint = coordinatesOfCenters[xCord][yCord];
                        Direction dOfAoi = nextPoint.getPointOfAreaOfInfluence1();
                        int defType = nextPoint.getDeflectionType();

                        System.out.println("detected");
                        if (dOfAoi == this.arrowDirection.iterate(3) && defType == 1 || defType == -1) {    //when there is an absorbtion
                            absorbed = true;
                            System.out.println("absorbed");
                        } else if (defType == 1) {                           //when there is a single collision i.e. 60 degrees
                            if (dOfAoi.iterate(2) == this.arrowDirection) {  //deflection logic
                                this.arrowDirection = dOfAoi.iterate(1);
                            } else {
                                this.arrowDirection = dOfAoi.iterate(5);
                            }
                            System.out.println("deflected 60");
                        } else if (defType == 2) {                          //when there is a 120 degree deflection
                            if (this.arrowDirection == dOfAoi.iterate(3)) {
                                this.arrowDirection = nextPoint.getPointOfAreaOfInfluence2();
                                System.out.println(nextPoint.getPointOfAreaOfInfluence2());
                            } else {
                                this.arrowDirection = dOfAoi;
                            }
                            System.out.println("deflected 120");
                        } else {                                                //when there is a 180 degrees i.e reflection
                            this.arrowDirection = this.arrowDirection.iterate(3);
                            System.out.println("reflected 180");
                        }
                    }

                    // Create and add the ray
                    Ray ray = new Ray(rayStartX, rayStartY, rayEndX, rayEndY, arrowDirection);
                    rayArrayList.add(ray);
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
                            if (xCord < 4)
                                yCord--;
                            break;
                        case NORTHEAST:
                            xCord--;
                            if (xCord > 3)
                                yCord++;
                            break;
                        case SOUTHEAST:
                            xCord++;
                            if (xCord < 5)
                                yCord++;
                            break;
                        case SOUTHWEST:
                            xCord++;
                            if (xCord > 4)
                                yCord--;
                            break;
                    }
                    // Update ray ending position if the next coordinate is valid
                    if (isValidCoordinate(xCord, yCord)) {
                        rayEndX = coordinatesOfCenters[xCord][yCord].getX();
                        rayEndY = coordinatesOfCenters[xCord][yCord].getY();
                    }
                }


                //setting the last ray path
                if (!absorbed) {
                    rayEndX = rayStartX + getXchange(arrowDirection) / 2;
                    rayEndY = rayStartY + getYchange(arrowDirection) / 2;
                    Ray ray = new Ray(rayStartX, rayStartY, rayEndX, rayEndY, arrowDirection);
                    ray.setVisible(true);
                    root.getChildren().add(ray.getOutline());
                }
                rayShot = true;
                arrow.setFill(Color.TRANSPARENT);
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
                if(!rayShot)
                arrow.setFill(Color.DARKRED);
            });
    }

    //two functions to get the change of coordinates for each direction
    private double getYchange(Direction d){
        return switch (d) {
            case NORTHEAST -> coordinatesOfCenters[2][3].getY() - coordinatesOfCenters[3][3].getY();
            case EAST -> coordinatesOfCenters[3][4].getY() - coordinatesOfCenters[3][3].getY();
            case SOUTHEAST -> coordinatesOfCenters[4][4].getY() - coordinatesOfCenters[3][3].getY();
            case SOUTHWEST -> coordinatesOfCenters[4][3].getY() - coordinatesOfCenters[3][3].getY();
            case WEST -> coordinatesOfCenters[3][2].getY() - coordinatesOfCenters[3][3].getY();
            case NORTHWEST -> coordinatesOfCenters[2][2].getY() - coordinatesOfCenters[3][3].getY();
        };
    }

    private double getXchange(Direction d){
        return switch (d) {
            case NORTHEAST -> coordinatesOfCenters[2][3].getX() - coordinatesOfCenters[3][3].getX();
            case EAST -> coordinatesOfCenters[3][4].getX() - coordinatesOfCenters[3][3].getX();
            case SOUTHEAST -> coordinatesOfCenters[4][4].getX() - coordinatesOfCenters[3][3].getX();
            case SOUTHWEST -> coordinatesOfCenters[4][3].getX() - coordinatesOfCenters[3][3].getX();
            case WEST -> coordinatesOfCenters[3][2].getX() - coordinatesOfCenters[3][3].getX();
            case NORTHWEST -> coordinatesOfCenters[2][2].getX() - coordinatesOfCenters[3][3].getX();
        };
    }
}