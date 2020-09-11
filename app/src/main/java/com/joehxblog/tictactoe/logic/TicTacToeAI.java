package com.joehxblog.tictactoe.logic;

import java.util.BitSet;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TicTacToeAI {

    private final TicTacToeGame game;

    public TicTacToeAI(final TicTacToeGame game) {
        this.game = Objects.requireNonNull(game);
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
