package com.example.sweproject;

import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;
import java.util.List;

public class Hexagon {

    private final double x;
    private final double y;
    private final double side;
    private final Polygon hexagon;
    public static List<Coordinate> atomCoordinates = null;



    public Hexagon(double x, double y, double side) {
        this.x = x;
        this.y = y;
        this.side = side;
        this.hexagon = createHexagon();
        atomCoordinates= new ArrayList<>();
        addMouseEnterHandler();
        addMouseExitHandler();
        addMouseClickHandler();
    }

public static double dottedCircleRadius;
    private void addMouseClickHandler() {
        hexagon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
        if (GameLauncher.AtomCount < 6) {
            Circle atom = new Circle(x, y - side, side / 2);
            RadialGradient gradient = new RadialGradient(
                    0, 0, 0.5, 0.5, 0.5, true, CycleMethod.NO_CYCLE,
                    new javafx.scene.paint.Stop(0, Color.RED),
                    new javafx.scene.paint.Stop(1, Color.DARKRED)
            );
            atom.setFill(gradient);
            GameLauncher.root.getChildren().add(atom);
            GameLauncher.AtomCount++;

             dottedCircleRadius = side * 1.73;
            Circle dottedCircle = new Circle(x, y - side, dottedCircleRadius);
            dottedCircle.setStroke(Color.WHITE);
            dottedCircle.setStrokeWidth(1);
            dottedCircle.getStrokeDashArray().addAll(2d, 5d);
            dottedCircle.setFill(Color.TRANSPARENT);
            GameLauncher.root.getChildren().add(dottedCircle);
            // Store the coordinates of the atom
            Coordinate atomCoordinate = new Coordinate(x, y + 45);
            atomCoordinates.add(atomCoordinate);
            System.out.println("Atom Coordinates: " + atomCoordinates);
            System.out.println("Radius of dotted circle: " + dottedCircleRadius);
        }
    });
    }
    public List<Coordinate> getAtomCoordinates() {
        return atomCoordinates;
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
    private Polygon createHexagon() {
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

        return hexagon;
    }
}
