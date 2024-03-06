package com.example.sweproject;

import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

public class Hexagon {

    private double x;
    private double y;
    private double side;
    private Polygon hexagon;


    public Hexagon(double x, double y, double side) {
        this.x = x;
        this.y = y;
        this.side = side;
        this.hexagon = createHexagon();
        addMouseEnterHandler();
        addMouseExitHandler();
        addMouseClickHandler();
    }

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

            double dottedCircleRadius = side * 1.75;
            Circle dottedCircle = new Circle(x, y - side, dottedCircleRadius);
            dottedCircle.setStroke(Color.WHITE);
            dottedCircle.setStrokeWidth(1);
            dottedCircle.getStrokeDashArray().addAll(2d, 5d);
            dottedCircle.setFill(Color.TRANSPARENT);
            GameLauncher.root.getChildren().add(dottedCircle);
        }
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
        Circle atom = new Circle(x, y - side, side / 2);
        RadialGradient gradient = new RadialGradient(
                0, 0, 0.5, 0.5, 0.5, true, CycleMethod.NO_CYCLE,
                new javafx.scene.paint.Stop(0, Color.RED),
                new javafx.scene.paint.Stop(1, Color.DARKRED)
        );
        atom.setFill(gradient);
        root.getChildren().add(atom);

        //creating area of influence
        double dottedCircleRadius = side * 1.75;
        Circle dottedCircle = new Circle(x, y - side, dottedCircleRadius);
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
