package com.joehxblog.tictactoe.logic;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class WinMapBuilderTest {

    @Test
    void buildWinMap() {
        final Map<Integer, Map<Integer, Integer>> winMap = WinMapBuilder.buildWinMap();
    }

    @Test
    void testConstant() {
        assertThrows(UnsupportedOperationException.class, () -> WinMapBuilder.WIN_MAP.clear());
    }

    @Test
    void testSame() {
        assertSame(WinMapBuilder.WIN_MAP, WinMapBuilder.WIN_MAP);
    }
}