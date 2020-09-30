package com.joehxblog.tictactoe.logic;

import java.util.BitSet;
import java.util.Objects;
import java.util.StringJoiner;

public class TicTacToeGame {
    private static final char X = 'x';
    private static final char O = 'o';
    private static final char EMPTY = ' ';

    private final TicTacToeBitBoard xBoard = new TicTacToeBitBoard();
    private final TicTacToeBitBoard oBoard = new TicTacToeBitBoard();

    private boolean xTurn = true; // o's turn when xTurn = false

    public boolean canPlayPosition(final int x, final int y) {
        return !(this.xBoard.getPosition(x,y) || this.oBoard.getPosition(x,y));
    }

    public char getValue(final int x, final int y) {
        if (this.xBoard.getPosition(x,y)) {
            return X;
        } else if (this.oBoard.getPosition(x,y)) {
            return O;
        } else {
            return EMPTY;
        }
    }

    public char getWinner() {
        if (this.xBoard.hasWon()) {
            return X;
        } else if (this.oBoard.hasWon()) {
            return O;
        } else {
            return EMPTY;
        }
    }

    public boolean hasWinner() {
        return this.xBoard.hasWon() || this.oBoard.hasWon();
    }

    public boolean isStalemate() {
        return !hasWinner() && getPlayed().cardinality() == 9;
    }

    public void playPosition(final int i) {
        playPosition(i % 3, i / 3);
    }

    public void playPosition(final int x, final int y) {
        if (canPlayPosition(x,y) && !hasWinner()) {

            if (this.xTurn) {
                this.xBoard.playPosition(x, y);
            } else {
                this.oBoard.playPosition(x, y);
            }

            this.xTurn = !this.xTurn;
        }
    }

    public void reset() {
        this.xBoard.reset();
        this.oBoard.reset();
        this.xTurn = true;
    }

    public BitSet getPlayed() {
        final BitSet played = new BitSet();
        played.or(this.xBoard.getPlayed());
        played.or(this.oBoard.getPlayed());
        return played;
    }

    @Override
    public int hashCode() {
        return (hashCode(this.xBoard.getPlayed()) << 9) + hashCode(this.oBoard.getPlayed());
    }

    private int hashCode(BitSet bitSet) {
        int hash = 0;

        for (int i = 0; i < 9; i++) {
            hash = (hash << 1) + (bitSet.get(i) ? 1 : 0);
        }

        return hash;
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof TicTacToeGame)) {
            return false;
        }

        return this.xBoard.getPlayed().equals(this.oBoard.getPlayed());
    }

    public String toString() {
        StringJoiner rows = new StringJoiner(System.lineSeparator());
        rows.add(Integer.toString(this.hashCode()));
        StringJoiner column = new StringJoiner("][","[","]");

        for (int i = 0; i < 9; i++) {

            if (this.xBoard.getPlayed().get(i)) {
                column.add(Character.toString(X));
            } else if (this.oBoard.getPlayed().get(i)) {
                column.add(Character.toString(O));
            } else {
                column.add(Character.toString(EMPTY));
            }

            if (i % 3 == 2) {
                rows.add(column.toString());
                column = new StringJoiner("][","[","]");
            }
        }

        return rows.toString();
    }
}
