package com.example.sweproject;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import static com.example.sweproject.Hexagon.toggleAtoms;
import static com.example.sweproject.GameUtils.displayText;
import static com.example.sweproject.GameUtils.setGuessersTurn;
import static javafx.scene.paint.Color.*;

public class GameLauncher extends Application {
    public static int AtomCount = 0;
    public static Stage stage;
    public static Group root;
    public static Scene scene ;
    public static int numberLabel = 0;

    //round number
    public static int round = 1;

    //score of players one and two
    public static int pOneScore = 0;
    public static int pTwoscore = 0;
    public static Text playerOneScoreText;
    public static Text playerTwoScoreText;
    public static Coordinate[][] coordinatesOfCenters = new Coordinate[9][9]; //stores the centres of all the hexagons
    public static ArrayList<Ray> rayArrayList = new ArrayList<>();
    public static ArrayList<Circle> exitPoints = new ArrayList<>();
    public static  ArrayList<Arrow> shotArrows = new ArrayList<>();
    public static final List<Circle> atoms = new ArrayList<>();
    public static final List<Circle> rings = new ArrayList<>();
    public static final List<Circle> guessAtoms = new ArrayList<>();
    private static boolean isQKeyPressed = false;
    private static boolean isWKeyPressed = false;
    private static boolean isEKeyPressed = false;
    private static boolean isRKeyPressed = false;
    private static boolean isTKeyPressed = false;
    public static  boolean guessersTurn = false;
    public static Text playerTurn;
    public static Text roundText;
    public static Text rayInfoText;
    public static final Text  confirmationText = new Text("Click again to confirm atom positions");
    public static final Text settersText = new Text();
    public static ArrayList<Hexagon> guessedHexagons = new ArrayList<>();



    public static void main(String[] args) {
        pOneScore = 0;
        pTwoscore = 0;
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        root = new Group();
        scene = new Scene(root, 760, 600, Color.BLACK);
        stage.setResizable(false);

        // Preloader stage
        Stage preloader = new Stage();
        VBox preRoot = new VBox();
        preRoot.setAlignment(Pos.CENTER);
        preRoot.setSpacing(20);
        Scene preScene = new Scene(preRoot, 760, 600, Color.BLACK);
        preloader.setScene(preScene);

        // Image view
        Image image = new Image("/MainIcon.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(preScene.getHeight() / 3); // Set the height to be one third of the scene's height
        imageView.setPreserveRatio(true); // Preserve the image's aspect ratio

        // Production text
        Text productionText = new Text("Brought to you by Sean, Mo and Emmanuel!");
        productionText.setFont(new Font(30)); // Set the font size to 20
        productionText.setFill(RED);

        // Start text
        Text startText = new Text("Start");
        startText.setFont(new Font(50)); // Set the font size to 50
        startText.setFill(WHITE);
        startText.setOnMouseClicked(event -> {
            round = 1;
            preloader.close();
            startBoard();
            stage.setScene(scene);
            stage.show();
        });

        // Rules text
        Text rulesText = new Text("Rules");
        rulesText.setFont(new Font(50)); // Set the font size to 50
        rulesText.setFill(BLUE);
        rulesText.setOnMouseClicked(event -> {
            preloader.close();
            rulesBoard();
        });

        // Exit text
        Text exitText = new Text("Exit");
        exitText.setFont(new Font(50)); // Set the font size to 50
        exitText.setFill(RED);
        exitText.setOnMouseClicked(event -> System.exit(0));

        // Add the image view and text to the preloader root
        preRoot.getChildren().addAll(imageView, productionText, startText, rulesText, exitText);

        // Show the preloader
        preloader.show();
    }
    public void rulesBoard(){
        //loading the main icon from the resources directory
        Image icon = new Image(getClass().getResource("/MainIcon.png").toExternalForm());
        stage.getIcons().add(icon);

        Stage preloader = new Stage();
        VBox preRoot = new VBox();
        preRoot.setAlignment(Pos.CENTER);
        preRoot.setSpacing(20);
        Scene preScene = new Scene(preRoot, 760, 600, Color.BLACK);
        preloader.setScene(preScene);

        // Add the rules text
        Text rulesText = new Text(
                "The rules of the game are as follows:\n" +
                        "- The terminal tells you who's turn it is as setter and as experimenter.\n" +
                        "- If you are the setter, you place 6 atoms and when you are done you press H to hide them.\n" +
                        "- The experimenter tries to deduce the positions of the atoms by sending rays into the BLACK BOX, they 6 guesses to guess where each atom is.\n" +
                        "- During play, the experimenter tells the setter where they are sending a ray into the box by announcing and pressing the numbered position at the edge of the BLACK BOX. \n" +
                        "- Every wrong guess is 5 points and every ray shot is one point.\n" +
                        "- The objective of the experimenter is to deduce the atom positions by using the smallest number of ray markers and correct atom guesses\n" +
                        "- By reference to the setter's pad, the ray paths should be checked, and for every error made in reporting the result of a ray the experimenter's score is reduced by 5 points. \n" +
                        "- After 6 guesses, the terminal will say guesses are done and to press P where the game will reset and roles reversed, Lowest score at the end wins!"

        );
        rulesText.setFill(Color.WHITE);
        rulesText.setWrappingWidth(700);
        rulesText.setFont(new Font(20));
        rulesText.setTextAlignment(TextAlignment.JUSTIFY); // Justify the text


        Text startText = new Text("Start");
        startText.setFont(new Font(50)); // Set the font size to 50
        startText.setFill(Color.WHITE);
        startText.setOnMouseClicked(event -> {
            preloader.close();
            startBoard();
            stage.setScene(scene);
            stage.show();
        });

        // Add the rules text and start button to the root
        preRoot.getChildren().addAll(rulesText, startText);
        preloader.show();
    }
    /**
     * This method initializes the game board.
     * <p>
     * It displays the text, sets the atom count, and prints the player's turn.
     * It also sets up the hexagonal grid for the game and adds mouse event handlers for the scene.
     * <p>
     * The mouse event handlers handle the logic for marking the board by the experimenter and setting the guesser's turn.
     * <p>
     * The method also sets up key event handlers for the scene to handle the logic for marking deflections, absorptions, and no hits.
     * <p>
     * Finally, it creates and places the arrows on the board
     *
     * @see Hexagon
     * @see Arrow
     *
     * @return void
     */
    public static void startBoard()
    {
        displayText();
        AtomCount = 0;
        if(round % 2 == 0){
            System.out.println("\nPlayer two's turn to set and player ones's turn to experiment");
        }else{
            System.out.println("\nPlayer one's turn to set and player two's turn to experiment");
        }

        double side = 40;
        double height = side * Math.sqrt(3);
        // Maximum number of columns in the middle of the hexagonal grid
        int maxColumns = 9;
        // counter for adding Atoms

    // this is for the markers for the experimenter to use
        scene.setOnMouseClicked(mouseEvent -> {
            // Check if 'q' key is pressed
            if (isQKeyPressed) {
                // Get the x and y coordinates of the mouse click
                double x = mouseEvent.getX();
                double y = mouseEvent.getY();

                // Create a blue rectangle at the mouse click position
                Rectangle rectangle = new Rectangle(x, y, 10, 10);
                rectangle.setFill(Color.BLUE);

                // Add the rectangle to the root group
                root.getChildren().add(rectangle);
            } else if (isWKeyPressed) {
                double x = mouseEvent.getX();
                double y = mouseEvent.getY();
                Rectangle rectangle = new Rectangle(x, y, 10, 10);
                rectangle.setFill(MAGENTA);
                root.getChildren().add(rectangle);
            } else if (isEKeyPressed) {
                double x = mouseEvent.getX();
                double y = mouseEvent.getY();
                Rectangle rectangle = new Rectangle(x, y, 10, 10);
                rectangle.setFill(PURPLE);
                root.getChildren().add(rectangle);
            } else if (isRKeyPressed) {
                double x = mouseEvent.getX();
                double y = mouseEvent.getY();
                Rectangle rectangle = new Rectangle(x, y, 10, 10);
                rectangle.setFill(GREEN);
                root.getChildren().add(rectangle);
            } else if (isTKeyPressed) {
                double x = mouseEvent.getX();
                double y = mouseEvent.getY();
                Rectangle rectangle = new Rectangle(x, y, 10, 10);
                rectangle.setFill(RED);
                root.getChildren().add(rectangle);
            }

            if(confirmationText.isVisible()){
                if(!guessersTurn)               //buffer to allow for confirmation text to be visible
                    guessersTurn = true;
                else
                setGuessersTurn();
            }
        });

        scene.setOnKeyPressed(keyEvent -> {
            // for the experimenter to mark what he thinks to be 60 degree deflections
            if (keyEvent.getCode() == KeyCode.Q) {
                isQKeyPressed = !isQKeyPressed;
                // if the key is pressed again it cancels out
            }
            // for the experimenter to mark what he thinks to be 120 degree deflections
            if (keyEvent.getCode() == KeyCode.W) {
                isWKeyPressed = !isWKeyPressed;
            }
            // for the experimenter to mark what he thinks to be 180 degree deflections
            if (keyEvent.getCode() == KeyCode.E) {
                isEKeyPressed = !isEKeyPressed;
            }
            // for the experimenter to mark what he thinks to be an absorption
            if (keyEvent.getCode() == KeyCode.R) {
                isRKeyPressed = !isRKeyPressed;
            }
            // for the experimenter to mark what he thinks to be no hit
            if (keyEvent.getCode() == KeyCode.T) {
                isTKeyPressed = !isTKeyPressed;
            }
        });

        // Create the top half of the hexagonal grid
        for (int i = 0; i < maxColumns / 2 + 1; i++) {
            for (int j = 0; j < i + 5; j++) {
                //sets x coordinate of the next hexagon and changes it by the height of each hexagon to put side by side
                double x = j * height + 100 + ((double) maxColumns / 2 - i) * height / 2;
                //changes y coordinate each time a hexagon is placed
                double y = i * 1.5 * side + 100;


                Text text = new Text("o");
                // Calculate the position to place the text in the center of the hexagon
                // Adjust the x-coordinate based on the text size to center it properly
                double textX = x - text.getLayoutBounds().getWidth() / 2;
                // Adjust the y-coordinate based on the text size and hexagon size to center it properly
                double textY = y + text.getLayoutBounds().getHeight() / 4;
                text.setX(textX);
                text.setY(textY - side);
                text.setFill(WHITE);
                text.setStroke(RED);
                text.setStrokeWidth(0.2);
                root.getChildren().add(text);

                // Add the coordinates to the ArrayList
                Coordinate coordinate = new Coordinate(textX, textY);
                coordinatesOfCenters[i][j] = coordinate;


                //calls create hexagon function to place it
                Hexagon hexagon = new Hexagon(x, y, side, i, j);

                //adds to stage
                root.getChildren().add(hexagon.getHexagon());

            }
        }

        // Create the bottom half of the hexagonal grid
        for (int i = maxColumns / 2 - 1; i >= 0; i--) {
            for (int j = 0; j < i + 5; j++) {
                double x = j * height + 100 + ((double) maxColumns / 2 - i) * height / 2;
                double y = (maxColumns - i - 1) * 1.5 * side + 100;

                Hexagon hexagon = new Hexagon(x, y, side, (8 - i), j);
                root.getChildren().add(hexagon.getHexagon());

                Text text = new Text("o");
                // Calculate the position to place the text in the center of the hexagon
                // Adjust the x-coordinate based on the text size to center it properly
                double textX = x - text.getLayoutBounds().getWidth() / 2;
                // Adjust the y-coordinate based on the text size and hexagon size to center it properly
                double textY = y + text.getLayoutBounds().getHeight() / 4;
                text.setX(textX);
                text.setY(textY - side);
                text.setFill(WHITE);
                text.setStroke(RED);
                text.setStrokeWidth(0.2);
                root.getChildren().add(text);

                // Add the coordinates to the ArrayList
                Coordinate coordinate = new Coordinate(textX, textY);
                coordinatesOfCenters[8 - i][j] = coordinate;

            }
        }
        //top face
        for (int i = 0; i < 10; i++)
        {
            double x = 34.7 * i + 235;
            double y = 40;


            // Rotate the arrow by 60 degrees
            if(i%2 == 0) {
                Arrow arrow = new Arrow(x, y, 10, Arrow.Direction.SOUTHEAST,new int[]{0, (int) Math.ceil((double) i / 2)});
                arrow.getArrow().setRotate(210);
                root.getChildren().add(arrow.getArrow());
            }else{
                Arrow arrow = new Arrow(x, y, 10, Arrow.Direction.SOUTHWEST,new int[]{0, i/2});
                arrow.getArrow().setRotate(30);
                root.getChildren().add(arrow.getArrow());
            }

        }

        //fourth face right-angled arrows
        for (int i = 0; i < 5; i++)
        {
            double x = 561.4 + (i * 34.8);
            double y = 65 + (i * 60) ;
            Arrow arrow = new Arrow(x, y, 10, Arrow.Direction.WEST,new int[]{i, 4 + i});
            // Rotate the arrow by 30 degrees
            arrow.getArrow().setRotate(90);
            root.getChildren().add(arrow.getArrow());

            if(i < 4)
            {
                x = 583 + (i * 34.8);
                y = 99.5 + (i * 60);
                Arrow arrow1 = new Arrow(x, y, 10, Arrow.Direction.SOUTHWEST,new int[]{i + 1, 5 + i});
                // Rotate the arrow by 30 degrees
                arrow1.getArrow().setRotate(30);
                root.getChildren().add(arrow1.getArrow());
            }
        }
        //fifth face right-angled arrows
        for (int i = 0; i < 4; i++)
        {

            double x = 687 - (i * 34.8);
            double y = 332 + (i * 60);
            Arrow arrow1 = new Arrow(x, y, 10, Arrow.Direction.NORTHWEST,new int[]{i + 4, 8 - i});
            // Rotate the arrow by 30 degrees
            arrow1.getArrow().setRotate(30);
            root.getChildren().add(arrow1.getArrow());

            x = 665.7 - (i * 34.8);
            y = 366.5 + (i * 60) ;
            Arrow arrow = new Arrow(x, y, 10, Arrow.Direction.WEST,new int[]{i + 5, 7 - i});
            // Rotate the arrow by 30 degrees
            arrow.getArrow().setRotate(90);
            root.getChildren().add(arrow.getArrow());

        }
        //bottom face
        for (int i = 9; i >= 0; i--)
        {
            double x = 34.8 * i + 235;
            double y;
            if(i%2 == 0){
                y = 570.6;
            }else{
                y = 573.5;
            }

            // Rotate the arrow by 60 degrees
            if(i%2 == 0) {
                Arrow arrow = new Arrow(x, y, 10, Arrow.Direction.NORTHEAST,new int[]{8, i/2});
                arrow.getArrow().setRotate(90);
                root.getChildren().add(arrow.getArrow());
            }else{
                Arrow arrow = new Arrow(x, y, 10, Arrow.Direction.NORTHWEST,new int[]{8, i/2});
                arrow.getArrow().setRotate(30);
                root.getChildren().add(arrow.getArrow());
            }
        }
        //third face right-angled arrows
        for (int i = 3; i >= 0; i--)
        {
            double x = 120.4 + (i * 34.8);
            double y = 366.5 + (i * 60) ;
            Arrow arrow = new Arrow(x, y, 10, Arrow.Direction.EAST, new int[]{i + 5, 0});
            // Rotate the arrow by 30 degrees
            arrow.getArrow().setRotate(30);
            root.getChildren().add(arrow.getArrow());

            x = 97.6 + (i * 34.8);
            y = 331 + (i * 60);
            Arrow arrow1 = new Arrow(x, y, 10, Arrow.Direction.NORTHEAST, new int[]{i + 4, 0});
            // Rotate the arrow by 30 degrees
            arrow1.getArrow().setRotate(90);
            root.getChildren().add(arrow1.getArrow());
        }

        //second face right-angled arrows
        for (int i = 4 ; i >= 0; i--)
        {
            if(i < 4)
            {
                double x = 202 - (i * 34.8);
                double y = 98.5 + (i * 60);
                Arrow arrow1 = new Arrow(x, y, 10, Arrow.Direction.SOUTHEAST, new int[]{i + 1, 0});
                // Rotate the arrow by 30 degrees
                arrow1.getArrow().setRotate(90);
                root.getChildren().add(arrow1.getArrow());
            }
            double x = 224.8 - (i * 34.8);
            double y = 65 + (i * 60) ;
            Arrow arrow = new Arrow(x, y, 10, Arrow.Direction.EAST, new int[]{i, 0});
            // Rotate the arrow by 30 degrees
            arrow.getArrow().setRotate(30);
            root.getChildren().add(arrow.getArrow());


        }
        //sets title of window/stage
        stage.setTitle("Black Box+");

        //how stage is shown
        stage.setScene(scene);
        stage.show();

    }
}
