package com.joehxblog.tictactoe.logic;

import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;

class TicTacToeAITrainer {
    private final Map<Integer, Map<Integer, Integer>> winMap = new HashMap<>();
    private final Map<Integer, Integer> currentXMap = new LinkedHashMap<>();
    private final Map<Integer, Integer> currentOMap = new LinkedHashMap<>();

    private final TicTacToeGame game = new TicTacToeGame();
    private final TicTacToeAI ai = new TicTacToeAI(this.game, this.winMap);

    @Test
    void train() throws IOException {
        PrintStream out = new PrintStream("game.txt");

        Set<Integer> played = new HashSet<>();

        for (int i = 0; i < 10; i++) {
            boolean xTurn = true;
            this.game.reset();
            currentXMap.clear();
            currentOMap.clear();

            out.println("Game " + i);
            out.println(game);
            out.println();

            while (!game.hasWinner() && !game.isStalemate()) {
                int board = game.hashCode();

                int pos = ai.playToWin();

                if (xTurn) {
                    currentXMap.put(board, pos);
                } else {
                    currentOMap.put(board, pos);
                }

                played.add(board);

                xTurn = !xTurn;

                out.println(game);
                out.println();
                out.flush();
            }

            if (game.hasWinner() && game.getWinner() == 'x') {
                add(currentXMap, 1);
                add(currentOMap, -1);
            } else if (game.hasWinner() && game.getWinner() == 'o') {
                add(currentXMap, -1);
                add(currentOMap, 1);
            } else {
                add(currentXMap, -1);
                add(currentOMap, -1);
            }
        }
    }

    private void add(Map<Integer, Integer> map, int value) {
        map.forEach((board, position) -> {
            winMap.computeIfAbsent(board, k -> new HashMap<>()).merge(position, value, (o, i) -> i + value);
        });
    }
}
