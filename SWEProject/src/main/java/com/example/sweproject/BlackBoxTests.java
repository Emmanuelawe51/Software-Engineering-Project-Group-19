package com.example.sweproject;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.junit.Test;
import static com.example.sweproject.GameLauncher.*;
import static jdk.dynalink.linker.support.Guards.isNotNull;
import static org.junit.Assert.*;
import static org.testfx.api.FxAssert.verifyThat;

import org.testfx.framework.junit.ApplicationTest;

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

}