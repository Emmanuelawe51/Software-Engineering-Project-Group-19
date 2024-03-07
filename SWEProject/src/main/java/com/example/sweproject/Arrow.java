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
    private double side;
    private Polygon arrow;
    //Arrow takes in the x and y coordiantes you want to print the arrow and the side length

    private int arrowNum;
    public Arrow(double x, double y, double side) {
        this.x = x;
        this.y = y;
        this.side = side;
        this.arrow = createArrow();
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
        } else if (arrowNum <= 25) {
            text.setX(x);
            if (arrowNum % 2 == 0) {
                text.setY(y);
            } else {
                text.setY(y);
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
    public void ShootRay(){
        arrow.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
            // Calculate the direction of the ray based on the rotation of the arrow
            double angle = Math.toRadians(arrow.getRotate());
            double rayEndX = x * Math.cos(angle);
            double rayEndY = y  * Math.sin(angle);
            // Create the ray
            Ray ray = new Ray(x, y, rayEndX, rayEndY);
            // Add the ray to the root group
            GameLauncher.root.getChildren().addAll(ray.getOutline(), ray.getLine());
        });
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
