package com.example.sweproject;

import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

public class Hexagon {

    private final double xCord;
    private final double yCord;
    //hold the relative position of the hexagon in the coordinatesOfCenters array
    private final int i, j;
    private final double side;
    private final Polygon hexagon;


    public Hexagon(double xCord, double yCord, double side, int i, int j) {
        this.xCord = xCord;
        this.yCord = yCord;
        this.side = side;
        this.i = i;
        this.j = j;
        this.hexagon = createHexagon();
        addMouseEnterHandler();
        addMouseExitHandler();
        addMouseClickHandler();
    }



    private void addMouseClickHandler() {
        hexagon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
        if (GameLauncher.AtomCount < 6) {
            Circle atom = new Circle(xCord, yCord - side, side / 2);
            RadialGradient gradient = new RadialGradient(
                    0, 0, 0.5, 0.5, 0.5, true, CycleMethod.NO_CYCLE,
                    new javafx.scene.paint.Stop(0, Color.RED),
                    new javafx.scene.paint.Stop(1, Color.DARKRED)
            );
            atom.setFill(gradient);
            GameLauncher.root.getChildren().add(atom);
            GameLauncher.AtomCount++;

            double dottedCircleRadius = side * 1.73;
            Circle dottedCircle = new Circle(xCord, yCord - side, dottedCircleRadius);
            dottedCircle.setStroke(Color.WHITE);
            dottedCircle.setStrokeWidth(1);
            dottedCircle.getStrokeDashArray().addAll(2d, 5d);
            dottedCircle.setFill(Color.TRANSPARENT);
            GameLauncher.root.getChildren().add(dottedCircle);

            int numRows = GameLauncher.coordinatesOfCenters.length;
            int numCols = GameLauncher.coordinatesOfCenters[0].length;

            // Set area of influence for adjacent hexagons
            if (j + 1 < numCols && GameLauncher.coordinatesOfCenters[i][j+1] != null)
                GameLauncher.coordinatesOfCenters[i][j+1].setPointOfAreaOfInfluence(Arrow.Direction.EAST);

            if (j - 1 >= 0 && GameLauncher.coordinatesOfCenters[i][j-1] != null)
                GameLauncher.coordinatesOfCenters[i][j-1].setPointOfAreaOfInfluence(Arrow.Direction.WEST);

            if (i > 0) {
                if (i < 5 && j - 1 >= 0 && GameLauncher.coordinatesOfCenters[i-1][j-1] != null)
                    GameLauncher.coordinatesOfCenters[i-1][j-1].setPointOfAreaOfInfluence(Arrow.Direction.NORTHWEST);
                else if (j >= 0 && GameLauncher.coordinatesOfCenters[i-1][j] != null)
                    GameLauncher.coordinatesOfCenters[i-1][j].setPointOfAreaOfInfluence(Arrow.Direction.NORTHWEST);

                if (i > 4 && j + 1 < numCols && GameLauncher.coordinatesOfCenters[i-1][j+1] != null)
                    GameLauncher.coordinatesOfCenters[i-1][j+1].setPointOfAreaOfInfluence(Arrow.Direction.NORTHEAST);
                else if (j < numCols && GameLauncher.coordinatesOfCenters[i-1][j] != null)
                    GameLauncher.coordinatesOfCenters[i-1][j].setPointOfAreaOfInfluence(Arrow.Direction.NORTHEAST);
            }

            if (i < numRows - 1) {
                if (i < 4 && j + 1 < numCols && GameLauncher.coordinatesOfCenters[i+1][j+1] != null)
                    GameLauncher.coordinatesOfCenters[i+1][j+1].setPointOfAreaOfInfluence(Arrow.Direction.SOUTHEAST);
                else if (j < numCols && GameLauncher.coordinatesOfCenters[i+1][j] != null)
                    GameLauncher.coordinatesOfCenters[i+1][j].setPointOfAreaOfInfluence(Arrow.Direction.SOUTHEAST);

                if (i > 3 && j - 1 >= 0 && GameLauncher.coordinatesOfCenters[i+1][j-1] != null)
                    GameLauncher.coordinatesOfCenters[i+1][j-1].setPointOfAreaOfInfluence(Arrow.Direction.SOUTHWEST);
                else if (j >= 0 && GameLauncher.coordinatesOfCenters[i+1][j] != null)
                    GameLauncher.coordinatesOfCenters[i+1][j].setPointOfAreaOfInfluence(Arrow.Direction.SOUTHWEST);
            }
        }
        printNoOfAreaOfInfluence(GameLauncher.coordinatesOfCenters);
    });
    }

    private void addMouseExitHandler() {hexagon.setOnMouseExited(event -> {
        // Perform actions when mouse exits hexagon
        hexagon.setFill(Color.TRANSPARENT);
    });

    }

    private void addMouseEnterHandler() {
        hexagon.setOnMouseEntered(event -> {
            // Perform actions when mouse enters hexagon
            hexagon.setFill(Color.DARKRED);
        });
    }


    public Polygon getHexagon() {
        return hexagon;
    }

    public void addAtom(Group root)
    {
        //creating the atom
        Circle atom = new Circle(xCord, yCord - side, side / 2);
        RadialGradient gradient = new RadialGradient(
                0, 0, 0.5, 0.5, 0.5, true, CycleMethod.NO_CYCLE,
                new javafx.scene.paint.Stop(0, Color.RED),
                new javafx.scene.paint.Stop(1, Color.DARKRED)
        );
        atom.setFill(gradient);
        root.getChildren().add(atom);

        //creating area of influence
        double dottedCircleRadius = side * 1.75;
        Circle dottedCircle = new Circle(xCord, yCord - side, dottedCircleRadius);
        dottedCircle.setStroke(Color.WHITE);
        dottedCircle.setStrokeWidth(1);
        dottedCircle.getStrokeDashArray().addAll(2d, 5d);
        dottedCircle.setFill(Color.TRANSPARENT);
        root.getChildren().add(dottedCircle);
    }

    private Polygon createHexagon() {
        Polygon hexagon = new Polygon();

        double height = side * Math.sqrt(3);

        hexagon.getPoints().addAll(
                xCord, yCord,
                xCord + height / 2, yCord - side / 2,
                xCord + height / 2, yCord - 1.5 * side,
                xCord, yCord - 2 * side,
                xCord - height / 2, yCord - 1.5 * side,
                xCord - height / 2, yCord - side / 2
        );
        // This line makes the fill color of the hexagon transparent
        hexagon.setFill(Color.TRANSPARENT);
        // This line sets the outline color of the hexagon to yellow
        hexagon.setStroke(Color.YELLOW);

        return hexagon;
    }

//method for debugging
    public static void printNoOfAreaOfInfluence(Coordinate[][] coordinatesOfCenters) {
        int numRows = coordinatesOfCenters.length;
        int numCols = coordinatesOfCenters[0].length;
        System.out.println();
        System.out.println();
        System.out.println();

        // Print upper part of the hexagonal pattern
        int initialNum = 5;
        for (int i = 0; i < numRows; i++) {
            // Adjust spacing for offset rows to make hexagons appear in a honeycomb pattern
            for (int k = initialNum; k < 9; k++) {
                System.out.print("  ");
            }

            // Print number of area of influence
            for (int j = 0; j < initialNum && j < numCols; j++) {
                if (coordinatesOfCenters[i][j] != null) {
                    Coordinate coordinate = coordinatesOfCenters[i][j];
                    System.out.print(coordinate.getNoOfAreaOfInfluence() + "   ");
                } else {
                    System.out.print("0 ");
                }
            }
            System.out.println();

            // Update initialNum for the next row
            if (i < 4) {
                initialNum++;
            } else {
                initialNum--;
            }
        }
    }
}
