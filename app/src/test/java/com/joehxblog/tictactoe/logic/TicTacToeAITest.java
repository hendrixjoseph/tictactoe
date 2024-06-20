package com.joehxblog.tictactoe.logic;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TicTacToeAITest {

    TicTacToeAI ai;

    @BeforeEach
    void beforeEach() {
        this.ai = new TicTacToeAI(new TicTacToeGame());
    }

    @Test
    void playRandomPosition() {
        final int position = this.ai.playRandomPosition();
        assertAll(() -> assertTrue(position >= 0),
                  () -> assertTrue(position < 9));
    }

    @Test
    void playFirstAvailablePosition() {
        for (int i = 0; i < 7; i++) { // only up to (less than) seven because at that point we have a winner
            assertEquals(i, this.ai.playFirstAvailablePosition());
        }
    }


}