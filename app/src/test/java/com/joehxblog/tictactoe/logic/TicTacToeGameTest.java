package com.joehxblog.tictactoe.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static com.joehxblog.tictactoe.logic.TestHelper.createPlays;
import static org.junit.jupiter.api.Assertions.*;

class TicTacToeGameTest {
    TicTacToeGame game;

    static Stream<List<int[]>> plays() {
        return Stream.of(// x     y
                createPlays(0,0,  0,1,
                            1,0,  0,2,
                            2,0),
                createPlays(0,1,  0,0,
                            1,1,  1,0,
                            2,1)
        );
    }

    @BeforeEach
    void beforeEach() {
        game = new TicTacToeGame();
    }

    @ParameterizedTest
    @MethodSource("com.joehxblog.tictactoe.logic.TestHelper#positions")
    void canPlayPosition_New(final int x, final int y) {
        assertTrue(game.canPlayPosition(x,y));
    }

    @Test
    void hasWinner_New() {
        assertFalse(game.hasWinner());
    }

    @ParameterizedTest
    @MethodSource("plays")
    void playPosition(final List<int[]> plays) {
        for (int[] play : plays) {
            assertFalse(game.hasWinner());
            game.playPosition(play[0], play[1]);
        }

        assertTrue(game.hasWinner());
    }
}