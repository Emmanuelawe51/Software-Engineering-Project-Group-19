package com.example.sweproject;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

import java.math.RoundingMode;
import java.util.*;

import static com.example.sweproject.GameLauncher.*;
import static com.example.sweproject.Ray.hideRays;
import static javafx.scene.paint.Color.RED;
import static javafx.scene.paint.Color.WHITE;

public class Hexagon {

    private static boolean isVisible = true;
    private static final List<Circle> atoms = new ArrayList<>();
    private static final List<Circle> rings = new ArrayList<>();
    private static final List<Circle> guessAtoms = new ArrayList<>();
    private final double xCord;
    private final double yCord;
    //hold the relative position of the hexagon in the coordinatesOfCenters array
    private final int i, j;
    public static int guesses = 0;
    private final double side;
    private final Polygon hexagon;
    private boolean isClicked = false;



    Map<Polygon, List<Circle>> hexagonToCircles = new HashMap<>();
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

    private void handleAtomSetting(){
            Circle atomCircle = new Circle(xCord, yCord - side, side / 2);
            RadialGradient gradient = new RadialGradient(
                    0, 0, 0.5, 0.5, 0.5, true, CycleMethod.NO_CYCLE,
                    new javafx.scene.paint.Stop(0, Color.RED),
                    new javafx.scene.paint.Stop(1, Color.DARKRED)
            );
            hexagonToCircles.computeIfAbsent(hexagon, k -> new ArrayList<>()).add(atomCircle);


            atomCircle.setFill(gradient);
            GameLauncher.root.getChildren().add(atomCircle);
            GameLauncher.AtomCount++;
            atoms.add(atomCircle);

            double dottedCircleRadius = side * 1.73;
            Circle dottedCircle = new Circle(xCord, yCord - side, dottedCircleRadius);
            dottedCircle.setStroke(Color.WHITE);
            dottedCircle.setStrokeWidth(1);
            dottedCircle.getStrokeDashArray().addAll(2d, 5d);
            dottedCircle.setFill(Color.TRANSPARENT);
            GameLauncher.root.getChildren().add(dottedCircle);
            rings.add(dottedCircle);
            hexagonToCircles.computeIfAbsent(hexagon, k -> new ArrayList<>()).add(atomCircle);

            setAreaOfInfluence(i, j);
    }

    private void handleGuessTurn(){
        confirmationText.setVisible(false);
        settersText.setText("Click arrows to shoot rays and \n\tdeduce where atoms are\nexit points are shown by blue\n\tcircles with red outline");
        settersText.setX(590);
        settersText.setFill(Color.BLUE);
        settersText.setStroke(Color.BLUE);
        toggleAtoms(false);

        playerTurn.setText("Player 2's Turn");
        playerTurn.setFill(Color.BLUE);
        playerTurn.setStroke(Color.BLUE);

        if (!hexagonToCircles.getOrDefault(hexagon, Collections.emptyList()).isEmpty()) {
            System.out.println("Atom found");
            guesses++;
        } else {
            System.out.println("No atom");
            guesses++;
            if (GameLauncher.round % 2 != 0) {
                pTwoscore = pTwoscore + 5;
            } else {
                pOneScore = pOneScore + 5;
            }
        }

        if(!guessersTurn){
            toggleGuessersTurn();
        }else {
            Circle atomCircle = new Circle(xCord, yCord - side, side / 2);
            RadialGradient gradient = new RadialGradient(
                    0, 0, 0.5, 0.5, 0.5, true, CycleMethod.NO_CYCLE,
                    new javafx.scene.paint.Stop(0, Color.BLUE),
                    new javafx.scene.paint.Stop(1, Color.BLUE)
            );
            hexagonToCircles.computeIfAbsent(hexagon, k -> new ArrayList<>()).add(atomCircle);


            atomCircle.setFill(gradient);
            GameLauncher.root.getChildren().add(atomCircle);
            guessAtoms.add(atomCircle);

            this.toggleIsClicked();
            guessedHexagons.add(this);
        }

    }

    private void addMouseClickHandler() {

        hexagon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {

            if (GameLauncher.AtomCount < 6) {
                //System.out.println("here 1");
                handleAtomSetting();
                if (GameLauncher.AtomCount == 6 && !guessersTurn) {
                    confirmationText.setVisible(true);
                }

            }  else if (guesses != 7) {
                //System.out.println("setter mate432242 " + guessersTurn);
                //System.out.println("here 3");
                handleGuessTurn();
            }

            if (guesses == 7) {
                System.out.println("All Guesses Complete press P to start next round");
                Ray.showRays();
                for (Circle atom : atoms){
                    atom.setVisible(isVisible);
                }
                for (Circle ring : rings){
                    ring.setVisible(isVisible);
                }

                scene.setOnKeyPressed(keyEvent -> {
                    if (keyEvent.getCode() == KeyCode.P) {
                        for (Circle atom : atoms) {
                            GameLauncher.root.getChildren().remove(atom);
                        }
                        for (Circle ring : rings) {
                            GameLauncher.root.getChildren().remove(ring);
                        }
                        for (Hexagon c: guessedHexagons){
                            c.toggleIsClicked();
                        }
                        for (Circle c : guessAtoms) {
                            GameLauncher.root.getChildren().remove(c);
                        }
                        for (Circle c : exitPoints){
                            GameLauncher.root.getChildren().remove(c);
                        }
                        for (Arrow c : shotArrows){
                            c.clear();
                            GameLauncher.root.getChildren().remove(c);
                        }
                        shotArrows.clear();
                        guessedHexagons.clear();
                        atoms.clear();
                        rings.clear();
                        exitPoints.clear();
                        Ray.clearRays();
                        GameLauncher.round++;
                        guesses = 0;
                        toggleGuessersTurn();

                        rayInfoText.setText(" ");
                        playerTurn.setText("Player 1's turn");
                        settersText.setText("Please select hexagons to place atoms");
                        roundText.setText("Round " + round);
                        settersText.setX(570);
                        settersText.setY(40);
                        settersText.setFill(Color.RED);
                        settersText.setStroke(Color.RED);

                        GameLauncher.startBoard();
                    }
                });
            }

           //printNoOfAreaOfInfluence(GameLauncher.coordinatesOfCenters);
    });
    }
    public static void toggleAtoms(boolean b) {
        for (Circle atom : atoms) {
            atom.setVisible(b);
        }
        for (Circle ring : rings) {
            ring.setVisible(b);
        }
    }


    public void setAreaOfInfluence(int i, int j){
        int numRows = GameLauncher.coordinatesOfCenters.length;
        int numCols = GameLauncher.coordinatesOfCenters[0].length;

        // Set area of influence for adjacent hexagons
        // creates a circle of area of influence around the atom each with their own direction which is relative to the atom

        //sets the deflection type of the edge cases to 180 to it is reflected
        //if the atom is on the top half of the game hexagon
        if(i == 0 || i == 8){
            if (j- 1 >= 0 && GameLauncher.coordinatesOfCenters[i][j - 1] != null)
                GameLauncher.coordinatesOfCenters[i][j - 1].setDeflectionType(3);

            if (GameLauncher.coordinatesOfCenters[i][j + 1] != null)
                GameLauncher.coordinatesOfCenters[i][j + 1].setDeflectionType(3);
        }


        if ((j == 0 || (j == 4 + i)) && i < 5) {

            //set the current atom to absorb
            GameLauncher.coordinatesOfCenters[i][j].setDeflectionType(-1);

            if (j == 0) {
                if (i - 1 >= 0 && GameLauncher.coordinatesOfCenters[i - 1][j] != null)
                    GameLauncher.coordinatesOfCenters[i - 1][j].setDeflectionType(3);

                if (GameLauncher.coordinatesOfCenters[i + 1][j] != null)
                    GameLauncher.coordinatesOfCenters[i + 1][j].setDeflectionType(3);

            } else {

                if (j + 1 <= 8 && GameLauncher.coordinatesOfCenters[i + 1][j + 1] != null)
                    GameLauncher.coordinatesOfCenters[i + 1][j + 1].setDeflectionType(3);
                if (i - 1 >= 0 && GameLauncher.coordinatesOfCenters[i - 1][j - 1] != null)
                    GameLauncher.coordinatesOfCenters[i - 1][j - 1].setDeflectionType(3);
            }


        }
        // if the atom is on the bottom half of the game hexagon
        if ((j == 0 || j == 7 - (i - 5)) && i >= 5) {

            //set the current atom to absorb
            GameLauncher.coordinatesOfCenters[i][j].setDeflectionType(-1);

            if(i == 8){
                if (j- 1 >= 0 && GameLauncher.coordinatesOfCenters[i][j - 1] != null)
                    GameLauncher.coordinatesOfCenters[i][j - 1].setDeflectionType(3);

                if (GameLauncher.coordinatesOfCenters[i][j + 1] != null)
                    GameLauncher.coordinatesOfCenters[i][j + 1].setDeflectionType(3);
            }

            if (j == 0) {
                if (GameLauncher.coordinatesOfCenters[i - 1][j] != null)
                    GameLauncher.coordinatesOfCenters[i - 1][j].setDeflectionType(3);

                if (i + 1 <= 8 && GameLauncher.coordinatesOfCenters[i + 1][j] != null)
                    GameLauncher.coordinatesOfCenters[i + 1][j].setDeflectionType(3);

            } else {

                if (GameLauncher.coordinatesOfCenters[i - 1][j + 1] != null)
                    GameLauncher.coordinatesOfCenters[i - 1][j + 1].setDeflectionType(3);
                if (i + 1 <= 8 && GameLauncher.coordinatesOfCenters[i + 1][j - 1] != null)
                    GameLauncher.coordinatesOfCenters[i + 1][j - 1].setDeflectionType(3);
            }
        }

        //regular area of influence setting
        //sets the direction of area of influences to the direction relative to the atom being placed

        if (j + 1 < numCols && GameLauncher.coordinatesOfCenters[i][j + 1] != null)
            GameLauncher.coordinatesOfCenters[i][j + 1].setPointOfAreaOfInfluence(Arrow.Direction.EAST);

        if (j - 1 >= 0 && GameLauncher.coordinatesOfCenters[i][j - 1] != null)
            GameLauncher.coordinatesOfCenters[i][j - 1].setPointOfAreaOfInfluence(Arrow.Direction.WEST);

        if (i > 0) {
            if (i < 5 && j - 1 >= 0 && GameLauncher.coordinatesOfCenters[i - 1][j - 1] != null)
                GameLauncher.coordinatesOfCenters[i - 1][j - 1].setPointOfAreaOfInfluence(Arrow.Direction.NORTHWEST);
            else if (j >= 0 && GameLauncher.coordinatesOfCenters[i - 1][j] != null && !(j == 0 && i < 5))
                GameLauncher.coordinatesOfCenters[i - 1][j].setPointOfAreaOfInfluence(Arrow.Direction.NORTHWEST);

            if (i > 4 && j + 1 < numCols && GameLauncher.coordinatesOfCenters[i - 1][j + 1] != null)
                GameLauncher.coordinatesOfCenters[i - 1][j + 1].setPointOfAreaOfInfluence(Arrow.Direction.NORTHEAST);
            else if (j < numCols && GameLauncher.coordinatesOfCenters[i - 1][j] != null)
                GameLauncher.coordinatesOfCenters[i - 1][j].setPointOfAreaOfInfluence(Arrow.Direction.NORTHEAST);
        }

        if (i < numRows - 1) {
            if (i < 4 && j + 1 < numCols && GameLauncher.coordinatesOfCenters[i + 1][j + 1] != null)
                GameLauncher.coordinatesOfCenters[i + 1][j + 1].setPointOfAreaOfInfluence(Arrow.Direction.SOUTHEAST);
            else if (j < numCols && GameLauncher.coordinatesOfCenters[i + 1][j] != null)
                GameLauncher.coordinatesOfCenters[i + 1][j].setPointOfAreaOfInfluence(Arrow.Direction.SOUTHEAST);

            if (i > 3 && j - 1 >= 0 && GameLauncher.coordinatesOfCenters[i + 1][j - 1] != null)
                GameLauncher.coordinatesOfCenters[i + 1][j - 1].setPointOfAreaOfInfluence(Arrow.Direction.SOUTHWEST);
            else if (j >= 0 && GameLauncher.coordinatesOfCenters[i + 1][j] != null && !(j == 0 && i > 3))
                GameLauncher.coordinatesOfCenters[i + 1][j].setPointOfAreaOfInfluence(Arrow.Direction.SOUTHWEST);
        }

    }

    private void addMouseExitHandler() {hexagon.setOnMouseExited(event -> {
        // Perform actions when mouse exits hexagon
        hexagon.setFill(Color.TRANSPARENT);
    });

    }

    private void addMouseEnterHandler() {
        hexagon.setOnMouseEntered(event -> {
            // Perform actions when mouse enters hexagon
            if(!isClicked)
            hexagon.setFill(Color.DARKRED);
        });
    }


    public Polygon getHexagon() {
        return hexagon;
    }
    private void toggleGuessersTurn()
    {
        guessersTurn = !guessersTurn;
    }
    private void toggleIsClicked(){
        isClicked = !isClicked;
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
                    System.out.print(coordinate.getDeflectionType() + "   ");
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
