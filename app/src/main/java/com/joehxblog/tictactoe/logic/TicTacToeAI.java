package com.joehxblog.tictactoe.logic;

import com.joehxblog.tictactoe.R;

import java.util.BitSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class TicTacToeAI {

    public enum Difficulty {
        EASY(R.id.easy_difficulty), MEDIUM(R.id.medium_difficulty), HARD(R.id.hard_difficulty);

        private final int menuId;

        public static Difficulty getByMenuId(int menuId) {
            switch (menuId) {
                case R.id.easy_difficulty:
                    return EASY;
                case R.id.medium_difficulty:
                    return MEDIUM;
                case R.id.hard_difficulty:
                    return HARD;
            }

            throw new AssertionError("Difficulty " + menuId + " not expressed in switch statement.");
        }

        Difficulty(final int menuId) {
            this.menuId = menuId;
        }

        public int getMenuId() {
            return this.menuId;
        }

    }

    private final TicTacToeGame game;
    private Difficulty difficulty = Difficulty.EASY;
    private final Map<Integer, Map<Integer, Integer>> winMap;

    public TicTacToeAI(final TicTacToeGame game) {
        this(game, WinMapBuilder.WIN_MAP);
    }

    public TicTacToeAI(TicTacToeGame game, Map<Integer, Map<Integer, Integer>> winMap) {
        this.game = Objects.requireNonNull(game);
        this.winMap = Objects.requireNonNull(winMap);
    }

    public void setDifficulty(final Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public int play() {
        switch (this.difficulty) {
            case EASY:
                return playFirstAvailablePosition();
            case MEDIUM:
                return playRandomPosition();
            case HARD:
                return playToWin();
        }

        throw new AssertionError("Difficulty " + this.difficulty + " not expressed in switch statement.");
    }

    public int playToWin() {
        int board = this.game.hashCode();

        if (this.winMap.containsKey(board)) {
            Map<Integer, Integer> currentMap = this.winMap.get(board);

            Map.Entry<Integer, Integer> maxEntry =
                        this.winMap.get(board)
                       .entrySet()
                       .stream()
                       .max(Comparator.comparingInt(Map.Entry::getValue))
                       .get();

            if (maxEntry.getValue() < 0 && currentMap.size() < 9 - this.game.getPlayed().cardinality()) {
                return playRandomPosition();
            } else {
                this.game.playPosition(maxEntry.getKey());
                return maxEntry.getKey();
            }
        } else {
            return playRandomPosition();
        }
    }

    public int playRandomPosition() {

        final BitSet available = new BitSet();
        available.set(0, 9);
        available.xor(this.game.getPlayed());

        final List<Integer> list = available.stream().boxed().collect(Collectors.toList());
        Collections.shuffle(list);

        this.game.playPosition(list.get(0));
        return list.get(0);
    }

    public int playFirstAvailablePosition() {
        final int position = this.game.getPlayed().nextClearBit(0);
        this.game.playPosition(position);
        return position;
    }
}
