package com.joehxblog.tictactoe.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TicTacToeAITest {

    TicTacToeAI ai;

    @BeforeEach
    void beforeEach() {
        ai = new TicTacToeAI(new TicTacToeGame());
    }

    @Test
    void playRandomPosition() {
        int position = ai.playRandomPosition();
        assertAll(() -> assertTrue(position >= 0),
                  () -> assertTrue(position < 9));
    }

    @Test
    void playFirstAvailablePosition() {
        for (int i = 0; i < 7; i++) { // only up to (less than) seven because at that point we have a winner
            assertEquals(i, ai.playFirstAvailablePosition());
        }
    }


}