package com.example.sweproject;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
public class Ray {
    private final double setStartX;
    private final double setStartY;
    private Arrow.Direction rayDirection;
    Line line;
    Line outline;
    public Ray(double startX, double startY, double setEndX, double setEndY, Arrow.Direction direction)
    {
        this.setStartX=startX;
        this.setStartY= startY;
        // creating the inner white line
        line = new Line();
        line.setStartX(setStartX);
        line.setStartY(setStartY);
        line.setEndX(setEndX);
        line.setEndY(setEndY);
        line.setStroke(Color.WHITE);
        line.setStrokeWidth(2);

        // Create the outline (a thicker, Blue line)
        outline = new Line();
        outline.setStartX(setStartX);
        outline.setStartY(setStartY);
        outline.setEndX(setEndX);
        outline.setEndY(setEndY);
        outline.setStroke(Color.DEEPSKYBLUE);
        outline.setStrokeWidth(5);

        this.rayDirection = direction;
        // this is just here to see the coordinates of the ray
       System.out.println("Ray created from (" + startX + ", " + startY + ") to (" + setEndX + ", " + setEndY + ")"); // Add this line

    }
    public Line getOutline() {return outline;}
    public Line getLine() {
        return line;
    }
}