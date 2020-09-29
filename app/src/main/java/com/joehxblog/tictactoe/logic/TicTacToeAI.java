package com.joehxblog.tictactoe.logic;

import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class TicTacToeAI {

    public enum Difficulty {EASY, MEDIUM, HARD}

    private final TicTacToeGame game;
    private Difficulty difficulty = Difficulty.EASY;

    public TicTacToeAI(final TicTacToeGame game) {
        this.game = Objects.requireNonNull(game);
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public int play() {
        switch (difficulty) {
            case EASY:
                return playFirstAvailablePosition();
            case MEDIUM:
                return  playRandomPosition();
            case HARD:
                return playToWin();
        }

        throw new AssertionError("Difficulty " + difficulty + " not expressed in switch statement.");
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
}
