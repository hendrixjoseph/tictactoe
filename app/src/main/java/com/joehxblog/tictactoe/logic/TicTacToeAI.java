package com.joehxblog.tictactoe.logic;

import com.joehxblog.tictactoe.R;

import java.util.BitSet;
import java.util.Collections;
import java.util.List;
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

    public TicTacToeAI(final TicTacToeGame game) {
        this.game = Objects.requireNonNull(game);
    }

    public void setDifficulty(final Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public int play() {
        switch (this.difficulty) {
            case EASY:
                return playFirstAvailablePosition();
            case MEDIUM:
                return  playRandomPosition();
            case HARD:
                return playToWin();
        }

        throw new AssertionError("Difficulty " + this.difficulty + " not expressed in switch statement.");
    }

    public int playToWin() {
        return playRandomPosition();
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

    public static class Memory {
        private final BitSet xBoard;
        private final BitSet yBoard;
        private final int position;

        public Memory(final BitSet xBoard, final BitSet yBoard, final int position) {
            this.xBoard = xBoard;
            this.yBoard = yBoard;
            this.position = position;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public boolean equals(final Object o) {
            if (!(o instanceof Memory)) {
                return false;
            }

            final Memory m = (Memory) o;

            return m.position == this.position
                    && m.xBoard.equals(this.xBoard)
                    && m.yBoard.equals(this.yBoard);
        }
    }
}
