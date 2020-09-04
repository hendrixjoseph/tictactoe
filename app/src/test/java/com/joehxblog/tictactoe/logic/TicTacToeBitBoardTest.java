package com.joehxblog.tictactoe.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class TicTacToeBitBoardTest {

    TicTacToeBitBoard board;

    static Stream<Arguments> positions() {
        final List<Arguments> list = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                list.add(arguments(i,j));
            }
        }

        return list.stream();
    }

    static Stream<List<int[]>> winningPlays() {
        return Stream.of(createPlays(new int[]{0, 0}, new int[]{0, 1}, new int[]{0, 2}));
    }

    static Stream<List<int[]>> losingPlays() {
        return Stream.of(
                createPlays(),
                createPlays(new int[]{0, 0}),
                createPlays(new int[]{0, 0}, new int[]{0, 1}));
    }

    static List<int[]> createPlays(final int[]... plays) {
        final List<int[]> list = new ArrayList<>();
        Collections.addAll(list, plays);
        return list;
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
    @MethodSource("positions")
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
}