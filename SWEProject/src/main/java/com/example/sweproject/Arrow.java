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
import static com.example.sweproject.Hexagon.guesses;
import static javafx.scene.paint.Color.*;

/**
 * The `Arrow` class represents arrows used in the game. Arrows can be placed on the game board and used to shoot rays.
 * This class provides methods to create and manage arrows, including handling mouse events for shooting rays,
 * determining arrow directions, and calculating ray paths.
 *
 */
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
    /**
     * The `Arrow` class represents arrows used in the game. Arrows can be placed on the game board and used to shoot rays.
     * This class provides methods to create and manage arrows, including handling mouse events for shooting rays,
     * determining arrow directions, and calculating ray paths.
     *
     * @see Direction
     *
     * @param x The x-coordinate of the arrow's position.
     * @param y The y-coordinate of the arrow's position.
     * @param side The length of the side.
     * @param direction The direction in which the arrow is pointing.
     * @param coord An array containing the coordinates of the arrow's position in the 2D array representing the game board.
     *
     * @return void
     */
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
        arrow.setFill(Color.TRANSPARENT);
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
        } else if (arrowNum < 55) {

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
    /**
     *   The `ShootRay` method handles ray shooting in a game.
     *   <p>
     *    It increments player scores, checks if a ray has been shot,
     *   and implements ray shooting logic. This includes getting hexagon center coordinates, setting ray positions,
     *   <p>
     *   Initializing absorption status, and looping until coordinates are valid and the ray is not absorbed.
     *   In the loop, it checks coordinate validity and deflection type, handles absorption, deflection, and reflection,
     *   creates a new Ray object, updates ray positions, and moves to the next coordinate.
     *   <p>
     *   If the ray is not absorbed by the end of the loop, it sets the last ray path, creates a Circle object at the ray's
     *   ending position, and updates the rayShot and arrow variables.
     *
     * @see MouseEvent#MOUSE_PRESSED
     * @see Coordinate
     * @see Direction
     * @see Ray
     *
     * @return void
     */
    public void ShootRay() {
        arrow.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
            if(GameLauncher.round % 2 == 0){
                pOneScore = pOneScore + 1;
            }else{
                pTwoscore = pTwoscore + 1;
            }
        });

        arrow.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
            if(!rayShot && guesses != 6) {
                int xCord = coord[0];
                int yCord = coord[1];
                //getting the coordinates of the center of the hexagons we need
                double rayEndX = coordinatesOfCenters[xCord][yCord].getX();
                double rayEndY = coordinatesOfCenters[xCord][yCord].getY();
                double rayStartX = x;
                double rayStartY = y;

                boolean absorbed = false;
                rayInfoText.setText("No hit");

                while (isValidCoordinate(xCord, yCord) && !absorbed) {

                    if (isValidCoordinate(xCord, yCord) && coordinatesOfCenters[xCord][yCord].getDeflectionType() != 0) {
                        Coordinate nextPoint = coordinatesOfCenters[xCord][yCord];
                        Direction dOfAoi = nextPoint.getPointOfAreaOfInfluence1();
                        int defType = nextPoint.getDeflectionType();

                        //System.out.println("detected");
                        if (dOfAoi == this.arrowDirection.iterate(3) && defType == 1 || defType == -1) {    //when there is an absorbtion
                            absorbed = true;
                            rayInfoText.setText("Absorbed");
                        } else if (defType == 1) {                           //when there is a single collision i.e. 60 degrees
                            rayInfoText.setText("Deflected ");
                            if (dOfAoi.iterate(2) == this.arrowDirection) {  //deflection logic
                                this.arrowDirection = dOfAoi.iterate(1);
                            } else {
                                this.arrowDirection = dOfAoi.iterate(5);
                            }
                        } else if (defType == 2) {      ;//when there is a 120 degree deflection
                            rayInfoText.setText("Deflected");
                            if (this.arrowDirection == dOfAoi.iterate(3)) {
                                this.arrowDirection = nextPoint.getPointOfAreaOfInfluence2();
                                System.out.println(nextPoint.getPointOfAreaOfInfluence2());
                            } else {
                                this.arrowDirection = dOfAoi;
                            }
                        } else {                                                //when there is a 180 degrees i.e reflection
                            this.arrowDirection = this.arrowDirection.iterate(3);
                            rayInfoText.setText("Reflected");
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
                    if (isValidCoordinate(xCord, yCord)) {
                        rayEndX = coordinatesOfCenters[xCord][yCord].getX();
                        rayEndY = coordinatesOfCenters[xCord][yCord].getY();
                    }
                }


                //setting the last ray path
                if (!absorbed) {
                    rayEndX = rayStartX + getXchange(arrowDirection) / 2;
                    rayEndY = rayStartY + getYchange(arrowDirection) / 2;

                    Circle circle = new Circle(rayEndX, rayEndY, 7);
                    circle.setFill(BLUE);
                    circle.setStroke(PURPLE);
                    root.getChildren().add(circle);
                    exitPoints.add(circle);
                }

                rayShot = true;
                //Ray.promptForExitPoint();
                arrow.setStroke(BLUE);
                arrow.setFill(BLUE);
                shotArrows.add(this);
            }
        });
    }

    public void clear(){
        arrow.setFill(Color.TRANSPARENT);
        arrow.setStroke(Color.YELLOW);
        this.rayShot = false;
    }

    //resets the colour of the arrow when the mouse is no longer hovering
    private void addMouseExitHandler() {arrow.setOnMouseExited(event -> {
        if(!rayShot)
        arrow.setFill(Color.TRANSPARENT);
    });

    }
    //sets the colour of the arrow when the mouse is hovering above the arrow
    private void addMouseEnterHandler() {
        arrow.setOnMouseEntered(event -> {
                if(!rayShot && guesses != 6)
                arrow.setFill(Color.DARKRED);
            });
    }

    private boolean isValidCoordinate(int x, int y) {
        return x >= 0 && x < coordinatesOfCenters.length &&
                y >= 0 && y < coordinatesOfCenters[x].length && coordinatesOfCenters[x][y] != null;
    }

    //two functions to get the change of coordinates for each direction
    //this is for the calculation of the exit point
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