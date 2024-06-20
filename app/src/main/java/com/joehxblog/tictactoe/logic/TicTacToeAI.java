package com.joehxblog.tictactoe.logic;

import com.joehxblog.tictactoe.R;

import java.util.BitSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class TicTacToeAI {

    public enum Difficulty {
        EASY(R.id.easy_difficulty), MEDIUM(R.id.medium_difficulty), HARD(R.id.hard_difficulty);

        private final int menuId;

        public static Difficulty getByMenuId(final int menuId) {
            if (menuId == R.id.easy_difficulty) {
                return EASY;
            } else if (menuId == R.id.medium_difficulty) {
                return MEDIUM;
            } else if (menuId == R.id.hard_difficulty) {
                return HARD;
            } else {
                throw new AssertionError("Difficulty " + menuId + " not expressed in switch statement.");
            }
        }

        Difficulty(final int menuId) {
            this.menuId = menuId;
        }

        /**
         * Gets the menu id associated with this difficulty.
         *
         * @return the menu id associated with this difficulty
         */
        public int getMenuId() {
            return this.menuId;
        }

    }

    private final TicTacToeGame game;
    private Difficulty difficulty = Difficulty.EASY;
    private final Map<Integer, Map<Integer, Integer>> winMap;

    /**
     * Creates a new TicTacToe AI with the default win map.
     *
     * @param game the tic tac toe game
     *
     * @see WinMapBuilder#WIN_MAP
     */
    public TicTacToeAI(final TicTacToeGame game) {
        this(game, WinMapBuilder.WIN_MAP);
    }

    /**
     * Creates a new TicTacToe AI with a provided win map. Useful for training.
     *
     * @param game the tic tac toe game
     * @param winMap a win map, which is a map of integers to a map of integers to an integer
     */
    public TicTacToeAI(final TicTacToeGame game, final Map<Integer, Map<Integer, Integer>> winMap) {
        this.game = Objects.requireNonNull(game);
        this.winMap = Objects.requireNonNull(winMap);
    }

    /**
     * Sets the difficulty.
     *
     * @param difficulty the difficulty
     */
    public void setDifficulty(final Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Plays this AI's turn based on the set difficulty.
     *
     * @return the position the AI played
     */
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

    /**
     * Plays this AI's turn as smartly as it can.
     *
     * @return the position the AI played
     */
    public int playToWin() {
        final int board = this.game.hashCode();

        if (this.winMap.containsKey(board)) {
            final Map<Integer, Integer> currentMap = this.winMap.get(board);

            final Map.Entry<Integer, Integer> maxEntry =
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

    /**
     * Plays this AI's turn as a random position.
     *
     * @return the position the AI played
     */
    public int playRandomPosition() {

        final BitSet available = new BitSet();
        available.set(0, 9);
        available.xor(this.game.getPlayed());

        final List<Integer> list = available.stream().boxed().collect(Collectors.toList());
        Collections.shuffle(list);

        this.game.playPosition(list.get(0));
        return list.get(0);
    }

    /**
     * Plays this AI's turn as the next available position. Very predictable.
     *
     * @return the position the AI played
     */
    public int playFirstAvailablePosition() {
        final int position = this.game.getPlayed().nextClearBit(0);
        this.game.playPosition(position);
        return position;
    }
}
