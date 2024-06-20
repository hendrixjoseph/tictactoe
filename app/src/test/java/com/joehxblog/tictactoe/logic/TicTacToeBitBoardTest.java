package com.joehxblog.tictactoe.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static com.joehxblog.tictactoe.logic.TestHelper.createPlays;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TicTacToeBitBoardTest {

    TicTacToeBitBoard board;

    static Stream<List<int[]>> winningPlays() {
        return Stream.of(createPlays(0,0, 0,1, 0,2),
                createPlays(0,0, 1,0, 2,0));
    }

    static Stream<List<int[]>> losingPlays() {
        return Stream.of(Collections.emptyList(),
                createPlays(0,0),
                createPlays(0,0, 0,1));
    }

    @BeforeEach
    void beforeEach() {
        this.board = new TicTacToeBitBoard();
    }

    @Test
    void getPosition_New() {
        boolean shouldBeFalse = false;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                shouldBeFalse = shouldBeFalse || this.board.getPosition(i,j);
            }
        }

        assertFalse(shouldBeFalse);
    }

    @ParameterizedTest
    @MethodSource("com.joehxblog.tictactoe.logic.TestHelper#positions")
    void playPosition(final int x, final int y) {
        assertFalse(this.board.getPosition(x,y));

        this.board.playPosition(x,y);

        assertTrue(this.board.getPosition(x,y));
    }

    @Test
    void hasWon_New() {
        assertFalse(this.board.hasWon());
    }

    @ParameterizedTest
    @MethodSource("winningPlays")
    void testWinningGame(final List<int[]> plays) {
        plays.forEach(play -> this.board.playPosition(play[0], play[1]));

        assertTrue(this.board.hasWon());
    }

    @ParameterizedTest
    @MethodSource("losingPlays")
    void testLosingGame(final List<int[]> plays) {
        plays.forEach(play -> this.board.playPosition(play[0], play[1]));

        assertFalse(this.board.hasWon());
    }

    @ParameterizedTest
    @MethodSource({"winningPlays", "losingPlays"})
    void testRestoreFromHash(final List<int[]> plays) {
        final TicTacToeBitBoard otherBoard = new TicTacToeBitBoard();

        plays.forEach(play -> {
            otherBoard.restoreFromHash(this.board.hashCode());

            assertEquals(this.board, otherBoard);

            this.board.playPosition(play[0], play[1]);
        });

        otherBoard.restoreFromHash(this.board.hashCode());

        assertEquals(this.board, otherBoard);
    }
}