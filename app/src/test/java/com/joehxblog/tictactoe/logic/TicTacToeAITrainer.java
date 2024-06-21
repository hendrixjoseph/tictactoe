package com.joehxblog.tictactoe.logic;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;

class TicTacToeAITrainer {
    private final Map<Integer, Map<Integer, Integer>> winMap = new HashMap<>();
    private final Map<Integer, Integer> currentXMap = new LinkedHashMap<>();
    private final Map<Integer, Integer> currentOMap = new LinkedHashMap<>();

    private final TicTacToeGame game = new TicTacToeGame();
    private final TicTacToeAI ai = new TicTacToeAI(this.game, this.winMap);

    @Test
    void train() throws IOException {
        final PrintStream out = new PrintStream("game.txt");

        final Set<Integer> played = new HashSet<>();

        for (int i = 0; i < 10; i++) {
            boolean xTurn = true;
            this.game.reset();
            this.currentXMap.clear();
            this.currentOMap.clear();

            out.println("Game " + i);
            out.println(this.game);
            out.println();

            while (!this.game.hasWinner() && !this.game.isStalemate()) {
                final int board = this.game.hashCode();

                final int pos = this.ai.playToWin();

                if (xTurn) {
                    this.currentXMap.put(board, pos);
                } else {
                    this.currentOMap.put(board, pos);
                }

                played.add(board);

                xTurn = !xTurn;

                out.println(this.game);
                out.println();
                out.flush();
            }

            if (this.game.hasWinner() && this.game.getWinner() == 'x') {
                add(this.currentXMap, 1);
                add(this.currentOMap, -1);
            } else if (this.game.hasWinner() && this.game.getWinner() == 'o') {
                add(this.currentXMap, -1);
                add(this.currentOMap, 1);
            } else {
                add(this.currentXMap, -1);
                add(this.currentOMap, -1);
            }
        }

        out.close();

        final StringJoiner outer = new StringJoiner(",", "{", "}");

        this.winMap.forEach((hash, map) -> {
            final StringJoiner inner = new StringJoiner(",", "{", "}");
            inner.add(Integer.toString(hash));

            map.forEach((k, v) -> {
                inner.add(Integer.toString(k));
                inner.add(Integer.toString(v));
            });

            outer.add(inner.toString());
        });

        final PrintStream winMapOut = new PrintStream("winMap.txt");
        winMapOut.print(outer.toString());

        winMapOut.close();
    }

    private void add(final Map<Integer, Integer> map, final int value) {
        map.forEach((board, position) -> {
            this.winMap.computeIfAbsent(board, k -> new HashMap<>()).merge(position, value, (o, i) -> i + value);
        });
    }
}
