package com.joehxblog.tictactoe.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static com.joehxblog.tictactoe.logic.TestHelper.createPlays;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        this.game = new TicTacToeGame();
    }

    @ParameterizedTest
    @MethodSource("com.joehxblog.tictactoe.logic.TestHelper#positions")
    void canPlayPosition_New(final int x, final int y) {
        assertTrue(this.game.canPlayPosition(x,y));
    }

    @Test
    void hasWinner_New() {
        assertFalse(this.game.hasWinner());
    }

    @ParameterizedTest
    @MethodSource("plays")
    void playPosition(final List<int[]> plays) {
        for (final int[] play : plays) {
            assertFalse(this.game.hasWinner());
            this.game.playPosition(play[0], play[1]);
        }

        assertTrue(this.game.hasWinner());
    }

    @ParameterizedTest
    @MethodSource("plays")
    void hashCodeTest(final List<int[]> plays) {
        final Set<Integer> hashSet = new HashSet<>();
        hashSet.add(this.game.hashCode());

        for (final int[] play : plays) {
            this.game.playPosition(play[0], play[1]);
            assertFalse(hashSet.contains(this.game.hashCode()));
            hashSet.add(this.game.hashCode());

            System.out.println(this.game);
            System.out.println();
        }
    }

    @ParameterizedTest
    @MethodSource({"plays"})
    void testRestoreFromHash(final List<int[]> plays) {
        final TicTacToeGame otherGame = new TicTacToeGame();

        plays.forEach(play -> {
            otherGame.restoreFromHash(this.game.hashCode());

            assertEquals(this.game, otherGame);

            this.game.playPosition(play[0], play[1]);
        });

        otherGame.restoreFromHash(this.game.hashCode());

        assertEquals(this.game, otherGame);
    }
}