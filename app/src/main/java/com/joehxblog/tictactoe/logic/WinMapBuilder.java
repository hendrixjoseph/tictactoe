package com.joehxblog.tictactoe.logic;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class WinMapBuilder {
    private WinMapBuilder() {

    }

    private static final int[][] data = {{41088,0,-1},{41344,5,1},{45440,3,-1},{8320,2,1},{4096,3,1},{8192,1,-1,3,-2},{16384,6,1},{32768,5,2,6,-1},{0,2,-2,3,-1,4,2,5,-1},{21764,8,1},{20484,0,1},{20740,7,-1},{16388,5,-1},{40964,8,-1},{32772,4,-1},{54533,4,1},{21765,2,-1},{57349,5,-1},{40965,3,-1},{40968,7,1},{32776,1,-2,4,-1},{98312,3,1,4,-2},{40970,1,-1},{106506,6,1},{58381,1,-1},{58509,0,-1},{57357,7,-1},{122894,8,1},{106510,3,-1},{98328,0,2},{46496,8,-1},{45472,7,1},{71713,4,1},{69665,6,-1},{4128,1,-1},{8224,1,-2},{46497,6,1},{69664,8,1},{73760,5,-1,7,-1},{74274,2,-1},{73762,8,-1},{73768,7,1},{100136,4,1},{98856,0,1},{99112,7,-1},{98344,8,-1},{104497,0,1},{71729,2,-1},{76386,0,-1},{76642,5,-1},{74338,6,-1}};

    public static final Map<Integer, Map<Integer, Integer>> WIN_MAP = Collections.unmodifiableMap(buildWinMap());

    public static Map<Integer, Map<Integer, Integer>> buildWinMap() {
        final Map<Integer, Map<Integer, Integer>> winMap = new HashMap<>();

        for (final int[] datum : data) {
            final int hash = datum[0];

            final Map<Integer, Integer> playMap = new HashMap<>();

            for (int i = 1; i < datum.length; i += 2) {
                playMap.put(datum[i], datum[i + 1]);
            }

            winMap.put(hash, playMap);
        }

        return winMap;
    }
}
