package com.example.sweproject;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

public class Arrow {

    private double x;
    private double y;
    private double side;
    private Polygon arrow;
    //Arrow takes in the x and y coordiantes you want to print the arrow and the side length
    public Arrow(double x, double y, double side) {
        this.x = x;
        this.y = y;
        this.side = side;
        this.arrow = createArrow();
        addMouseEnterHandler();
        addMouseExitHandler();
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
        arrow.setFill(Color.GREEN);
        // This line sets the outline color of the arrow to yellow
        arrow.setStroke(Color.YELLOW);

        return arrow;
    }

    public Polygon getArrow() {
        return arrow;
    }
    //resets the colour of the arrow when the mouse is no longer hovering
    private void addMouseExitHandler() {arrow.setOnMouseExited(event -> {
        arrow.setFill(Color.GREEN);
    });

    }
    //sets the colour of the arrow when the mouse is hovering above the arrow
    private void addMouseEnterHandler() {
        arrow.setOnMouseEntered(event -> {
            arrow.setFill(Color.DARKRED);
        });
    }
}
