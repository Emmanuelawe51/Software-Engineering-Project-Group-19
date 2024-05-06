package com.example.sweproject;

import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit.ApplicationTest;

import static com.example.sweproject.GameLauncher.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.TextMatchers.hasText;

public class BlackBoxTests extends ApplicationTest{
    @Test
    public void testAtomCount() {
        GameLauncher gameLauncher = new GameLauncher();
        assertEquals(0, gameLauncher.AtomCount());
    }
    @Test
    public void testRoundNumber() {
        assertEquals(1, round);
    }
    @Test
    public void testPlayerOneScore() {
        assertEquals(0, pOneScore);
    }
    @Test
    public void testPlayerTwoScore() {
        assertEquals(0, pTwoscore);
    }

    @Test
    public void testGuesses() {
        assertEquals(0, Hexagon.guesses);
    }

    @Test
    public void testNumberLabel() {
        assertEquals(0, numberLabel);
    }

    @Test
    public void testIsClicked() {
        assertFalse(Hexagon.isClicked);
    }

    // instance of gamelauncher
    @Override
    public void start(Stage stage) throws Exception {
        new GameLauncher().start(stage);
    }

    @Test
    public void testStartButton() {
        // Verify that the Start button is visible
        verifyThat("Start", isVisible());

        // Click on the Start button
        clickOn("Start");

        // Verify that the round number is 1 and is present
        verifyThat("Round 1", isVisible());

        // Verify that the scene has been changed
        assertEquals(scene, stage.getScene());
    }

    @Test
    public void testRulesButton() {
        // Verify that the Rules button is visible
        verifyThat("Rules", isVisible());

        // Click on the Rules button
        clickOn("Rules");

        // use a delay to ensure that the UI is fully rendered
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verify that the rules text is displayed
        verifyThat("The rules of the game are as follows:\n" +
                "- The terminal tells you who's turn it is as setter and as experimenter.\n" +
                "- If you are the setter, you place 6 atoms and when you are done you press H to hide them.\n" +
                "- The experimenter tries to deduce the positions of the atoms by sending rays into the BLACK BOX, they 6 guesses to guess where each atom is.\n" +
                "- During play, the experimenter tells the setter where they are sending a ray into the box by announcing and pressing the numbered position at the edge of the BLACK BOX. \n" +
                "- Every wrong guess is 5 points and every ray shot is one point.\n" +
                "- The objective of the experimenter is to deduce the atom positions by using the smallest number of ray markers and correct atom guesses\n" +
                "- By reference to the setter's pad, the ray paths should be checked, and for every error made in reporting the result of a ray the experimenter's score is reduced by 5 points. \n" +
                "- After 6 guesses, the terminal will say guesses are done and to press P where the game will reset and roles reversed, Lowest score at the end wins!", isVisible());
    }

    @Test
    public void testExitButton() {
        // Verify that the Exit button is visible
        verifyThat("Exit", isVisible());

        // Click on the Exit button
        clickOn("Exit");

    }
}
