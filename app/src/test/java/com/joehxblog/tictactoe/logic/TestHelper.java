package com.joehxblog.tictactoe.logic;

import org.junit.jupiter.params.provider.Arguments;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

class TestHelper {
    static Stream<Arguments> positions() {
        final List<Arguments> list = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                list.add(arguments(i,j));
            }
        }

        return list.stream();
    }

    static List<int[]> createPlays(final int... plays) {
        final List<int[]> list = new ArrayList<>();

        for (int i = 0; i < plays.length - 1; i++) {
            list.add(new int[]{plays[i], plays[i + 1]});
        }

        return list;
    }
}
