package com.example.sweproject;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

import java.util.List;

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
    private Hexagon hexagon;

    public Arrow(double x, double y, double side, Direction direction, int[] coord, Hexagon hexagon) {
        this.x = x;
        this.y = y;
        this.side = side;
        this.arrow = createArrow();
        this.arrowDirection = direction;
        this.coord = coord;
        this.hexagon = hexagon; // Set the reference to the Hexagon class
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
            // Check if atom count is 6 before shooting the ray
            if (GameLauncher.AtomCount == 6){
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
                System.out.println("Shooting ray towards EAST");
                //while the coordinate is valid
                while (isValidCoordinate(xCord, yCord)) {
                    Ray ray = new Ray(rayStartX, rayStartY, rayEndX, rayEndY, arrowDirection);
                    root.getChildren().addAll(ray.getOutline(), ray.getLine());

                    // this handles how the ray changes direction
                    if (checkCollision(rayEndX, rayEndY)) {
                        System.out.println("Collision detected, changing direction to SouthEast");
                        while (isValidCoordinate(xCord, yCord)) {
                            // Reset starting coordinates for southeast direction
                            Ray ray1 = new Ray(rayStartX, rayStartY, rayEndX, rayEndY, arrowDirection);
                            root.getChildren().addAll(ray1.getOutline(), ray1.getLine());
                            //reseting the starting position of the next ray to the end of this one
                            rayStartX = rayEndX;
                            rayStartY = rayEndY;

                            // Move to the next coordinate
                            xCord++;
                            if (xCord < 5)
                                yCord++;
                            if (isValidCoordinate(xCord, yCord)) {
                                rayEndX = GameLauncher.coordinatesOfCenters[xCord][yCord].getX();
                                rayEndY = GameLauncher.coordinatesOfCenters[xCord][yCord].getY();
                            }
                            System.out.println("Direction " + Direction.SOUTHEAST);
                        }
                        break;
                    }
                    // this is if we want our ray to hav an outline, so it looks like the example,
                    // but it looks weird with it so ima comment out the statement to make it optional
                    // root.getChildren().addAll(ray.getOutline(), ray.getLine());

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
                    if (checkCollision(rayEndX, rayEndY)) {
                        System.out.println("Collision detected, changing direction to SouhWest");
                        while (isValidCoordinate(xCord, yCord)) {
                            // Reset starting coordinates for southeast direction
                            Ray ray1 = new Ray(rayStartX, rayStartY, rayEndX, rayEndY, arrowDirection);
                            root.getChildren().addAll(ray1.getOutline(), ray1.getLine());
                            //reseting the starting position of the next ray to the end of this one
                            rayStartX = rayEndX;
                            rayStartY = rayEndY;
                            // Move to the next coordinate
                            xCord++;
                            if (xCord > 4)
                                yCord--;
                            if (isValidCoordinate(xCord, yCord)) {
                                rayEndX = GameLauncher.coordinatesOfCenters[xCord][yCord].getX();
                                rayEndY = GameLauncher.coordinatesOfCenters[xCord][yCord].getY();
                            }
                            System.out.println("Direction " + Direction.SOUTHWEST);
                        }
                        break;
                    }
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
                    if (checkCollision(rayEndX, rayEndY)) {
                        System.out.println("Collision detected, changing direction to NorthEast");
                        while (isValidCoordinate(xCord, yCord)) {
                            // Reset starting coordinates for southeast direction
                            Ray ray1 = new Ray(rayStartX, rayStartY, rayEndX, rayEndY, arrowDirection);
                            root.getChildren().addAll(ray1.getOutline(), ray1.getLine());
                            //reseting the starting position of the next ray to the end of this one
                            //reseting the starting position of the next ray to the end of this one
                            rayStartX = rayEndX;
                            rayStartY = rayEndY;
                            // Move to the next coordinate
                            xCord--;
                            if (xCord > 3)
                                yCord++;
                            if (isValidCoordinate(xCord, yCord)) {
                                rayEndX = GameLauncher.coordinatesOfCenters[xCord][yCord].getX();
                                rayEndY = GameLauncher.coordinatesOfCenters[xCord][yCord].getY();
                            }
                            System.out.println("Direction " + Direction.NORTHEAST);
                        }
                        break;
                    }
                    //reseting the starting position of the next ray to the end of this one
                    rayStartX = rayEndX;
                    rayStartY = rayEndY;
                    // Move to the next coordinate
                    xCord--;
                    if (xCord < 4)
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
                    // this one still needs a bit of fine tuning
                    if (checkCollision(rayEndX, rayEndY)) {
                        System.out.println("Collision detected, changing direction to NorthWest");
                        while (isValidCoordinate(xCord, yCord)) {
                            // Reset starting coordinates for  direction
                            Ray ray1 = new Ray(rayStartX, rayStartY, rayEndX, rayEndY, arrowDirection);
                            root.getChildren().addAll(ray1.getOutline(), ray1.getLine());
                            //reseting the starting position of the next ray to the end of this one
                            rayStartX = rayEndX;
                            rayStartY = rayEndY;
                            // Move to the next coordinate
                            xCord--;
                            if (xCord < 4)
                                yCord--;
                            if (isValidCoordinate(xCord, yCord)) {
                                rayEndX = GameLauncher.coordinatesOfCenters[xCord][yCord].getX();
                                rayEndY = GameLauncher.coordinatesOfCenters[xCord][yCord].getY();
                            }
                            System.out.println("Direction " + Direction.NORTHWEST);
                        }
                        break;
                    }
                    //reseting the starting position of the next ray to the end of this one
                    rayStartX = rayEndX;
                    rayStartY = rayEndY;
                    // Move to the next coordinate
                    xCord--;
                    if (xCord > 3)
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
                    if (checkCollision(rayEndX, rayEndY)) {
                        System.out.println("Collision detected, changing direction to Southwest");
                        while (isValidCoordinate(xCord, yCord)) {
                            // Reset starting coordinates for  direction
                            Ray ray1 = new Ray(rayStartX, rayStartY, rayEndX, rayEndY, arrowDirection);
                            root.getChildren().addAll(ray1.getOutline(), ray1.getLine());
                            //reseting the starting position of the next ray to the end of this one
                            rayStartX = rayEndX;
                            rayStartY = rayEndY;
                            // Move to the next coordinate
                            xCord++;
                            if (xCord > 4)
                                yCord--;
                            if (isValidCoordinate(xCord, yCord)) {
                                rayEndX = GameLauncher.coordinatesOfCenters[xCord][yCord].getX();
                                rayEndY = GameLauncher.coordinatesOfCenters[xCord][yCord].getY();
                            }
                            System.out.println("Direction " + Direction.SOUTHWEST);
                        }
                        break;
                    }
                    //reseting the starting position of the next ray to the end of this one
                    rayStartX = rayEndX;
                    rayStartY = rayEndY;

                    // Move to the next coordinate
                    xCord++;
                    if (xCord < 5)
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
                    if (checkCollision(rayEndX, rayEndY)) {
                        System.out.println("Collision detected, changing direction to Southeast");
                        while (isValidCoordinate(xCord, yCord)) {
                            // Reset starting coordinates for  direction
                            Ray ray1 = new Ray(rayStartX, rayStartY, rayEndX, rayEndY, arrowDirection);
                            root.getChildren().addAll(ray1.getOutline(), ray1.getLine());
                            //reseting the starting position of the next ray to the end of this one
                            rayStartX = rayEndX;
                            rayStartY = rayEndY;
                            // Move to the next coordinate
                            xCord++;
                            if (xCord < 5)
                                yCord++;
                            if (isValidCoordinate(xCord, yCord)) {
                                rayEndX = GameLauncher.coordinatesOfCenters[xCord][yCord].getX();
                                rayEndY = GameLauncher.coordinatesOfCenters[xCord][yCord].getY();
                            }
                            System.out.println("Direction " + Direction.SOUTHEAST);
                        }
                        break;
                    }
                    //reseting the starting position of the next ray to the end of this one
                    rayStartX = rayEndX;
                    rayStartY = rayEndY;
                    // Move to the next coordinate
                    xCord++;
                    if (xCord > 4)
                        yCord--;
                    if (isValidCoordinate(xCord, yCord)) {
                        rayEndX = GameLauncher.coordinatesOfCenters[xCord][yCord].getX();
                        rayEndY = GameLauncher.coordinatesOfCenters[xCord][yCord].getY();
                    }
                }

            }
        }
        });
    }
    // this checks for collisons
    private boolean checkCollision(double rayEndX, double rayEndY) {
        List<Coordinate> atomCoordinates = hexagon.getAtomCoordinates(); // Retrieve atom coordinates
        System.out.println("Number of atoms: " + atomCoordinates.size());
        // Iterate through each dotted circle's coordinates
        for (Coordinate atomCoordinate : atomCoordinates) {
            // Calculate the distance between the end point of the ray and the center of the dotted circle
            double distance = Math.sqrt(Math.pow(atomCoordinate.getX() - rayEndX, 2) + Math.pow(atomCoordinate.getY() - rayEndY, 2));

            // If the distance is less than or equal to the dotted circle's radius, collision occurs
            if (distance <= 79) {
                System.out.println("Distance to atom: " + distance);
                return true; // Collision detected
            }
        }
        return false; // No collision detected
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
