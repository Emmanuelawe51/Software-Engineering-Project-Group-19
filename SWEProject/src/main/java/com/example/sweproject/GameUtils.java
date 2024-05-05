package com.example.sweproject;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import static com.example.sweproject.GameLauncher.*;
import static com.example.sweproject.Hexagon.toggleAtoms;
import static javafx.scene.paint.Color.BLUE;
import static com.example.sweproject.Hexagon.guessersTurnBuffer;
import static com.example.sweproject.Hexagon.guesses;


public class GameUtils {
    private static boolean isVisible = true;

    public static void displayText(){ //setting text values at the start of a round

        if(round  == 1){
            playerOneScoreText = new Text("Player 1 Score: " + pOneScore);
            playerOneScoreText.setFill(Color.WHITE);
            playerOneScoreText.setFont(Font.font("Arial", FontWeight.BOLD, 16));
            playerOneScoreText.setX(scene.getWidth() - 150);
            playerOneScoreText.setY(scene.getHeight() - 30);

            playerTwoScoreText = new Text("Player 2 Score: " + pTwoscore);
            playerTwoScoreText.setFill(Color.WHITE);
            playerTwoScoreText.setFont(Font.font("Arial", FontWeight.BOLD, 16));
            playerTwoScoreText.setX(scene.getWidth() - 150);
            playerTwoScoreText.setY(scene.getHeight() - 10);
            if(round % 2 != 0) {
                playerTurn = new Text("Player 1's turn");
            }else{
                playerTurn = new Text ("Player 2's turn");
            }
            playerTurn.setX(600);
            playerTurn.setY(20);
            playerTurn.setFill(Color.RED);
            playerTurn.setStroke(Color.RED);
            playerTurn.setStrokeWidth(0.2);
            playerTurn.setFont(Font.font("Arial", FontWeight.BOLD, 20));

            confirmationText.setX(20);
            confirmationText.setY(30);
            confirmationText.setFill(Color.WHITE);
            confirmationText.setStroke(Color.RED);
            confirmationText.setStrokeWidth(0.2);
            confirmationText.setVisible(false);

            roundText = new Text("Round " + round);
            roundText.setX(10);
            roundText.setY(590);
            roundText.setFill(Color.WHITE);
            roundText.setStroke(Color.RED);
            roundText.setStrokeWidth(0.2);
            roundText.setFont(Font.font("Arial", FontWeight.BOLD, 20));

            rayInfoText = new Text(" ");
            rayInfoText.setX(10);
            rayInfoText.setY(560);
            rayInfoText.setFill(Color.BLUE);
            rayInfoText.setStroke(BLUE);
            rayInfoText.setStrokeWidth(0.2);
            rayInfoText.setFont(Font.font("Arial", FontWeight.BOLD, 25));


            settersText.setText("Please select hexagons to place atoms");
            settersText.setX(570);
            settersText.setY(40);
            settersText.setFill(Color.RED);
            settersText.setStroke(Color.RED);
            settersText.setStrokeWidth(0.2);
            settersText.setFont(Font.font("Arial", FontWeight.BOLD, 10));
            if(round == 1) {
                root.getChildren().add(settersText);
                root.getChildren().add(confirmationText);
                root.getChildren().add(playerTurn);
                root.getChildren().add(roundText);
                root.getChildren().add(rayInfoText);
                root.getChildren().addAll(playerOneScoreText, playerTwoScoreText);
            }

            if (round >= 2)
                updatePlayerScores();
        }
        else {
            playerTurn.setFill(Color.RED);
            playerTurn.setStroke(Color.RED);
            confirmationText.setVisible(false);
            System.out.println("Round: " + round);
            updatePlayerScores();
        }

    }
    public static void setGuessersTurn(){ //setting text values for the guessers turn
        confirmationText.setVisible(false);
        settersText.setText("Click arrows to shoot rays and \n\tdeduce where atoms are\nexit points are shown by blue\n\tcircles with red outline"); //setting text
        settersText.setX(590);
        settersText.setFill(Color.BLUE);
        settersText.setStroke(Color.BLUE);
        toggleAtoms(false);

        if(round % 2 != 0) {
            playerTurn.setText("Player 2's Turn");
        }else{
            playerTurn.setText("Player 1's Turn");
        }
        playerTurn.setFill(Color.BLUE);
        playerTurn.setStroke(Color.BLUE);

    }

    public static void updatePlayerScores() {
        // Update the Text nodes with current scores
        playerOneScoreText.setText("Player 1 Score: " + pOneScore);
        playerTwoScoreText.setText("Player 2 Score: " + pTwoscore);
    }

    public static void endRound() {
        System.out.println("All Guesses Complete press P to start next round");
        Ray.showRays();
        for (Circle atom : atoms){
            atom.setVisible(isVisible);
        }
        for (Circle ring : rings){
            ring.setVisible(isVisible);
        }

        scene.setOnKeyPressed(keyEvent -> {             //resets everything for the next round
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
                guessersTurn = false;
                guessersTurnBuffer = false;

                rayInfoText.setText(" ");
                if(round % 2 != 0) {
                    playerTurn.setText("Player 1's turn");
                }else{
                    playerTurn.setText("Player 2's turn");
                }
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
}
