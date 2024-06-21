package com.joehxblog.tictactoe.logic;

import java.util.BitSet;
import java.util.StringJoiner;

public class TicTacToeGame {
    private static final char X = 'x';
    private static final char O = 'o';
    private static final char EMPTY = ' ';

    private final TicTacToeBitBoard xBoard = new TicTacToeBitBoard();
    private final TicTacToeBitBoard oBoard = new TicTacToeBitBoard();

    private boolean xTurn = true; // o's turn when xTurn = false

    /**
     * Returns true if the position is playable, false otherwise.
     *
     * @param x the position along the x-axis
     * @param y the position along the y-axis
     *
     * @return true if the position is playable, false otherwise
     */
    public boolean canPlayPosition(final int x, final int y) {
        return !(this.xBoard.getPosition(x, y) || this.oBoard.getPosition(x, y));
    }

    /**
     * Returns the character value for this given position.
     *
     * @param x the position along the x-axis
     * @param y the position along the y-axis
     *
     * @return the character value
     */
    public char getValue(final int x, final int y) {
        if (this.xBoard.getPosition(x, y)) {
            return X;
        } else if (this.oBoard.getPosition(x, y)) {
            return O;
        } else {
            return EMPTY;
        }
    }

    /**
     * Returns the character representation of the winner, or an empty character if there currently
     * is no winner.
     *
     * @return the character representation of the winner
     */
    public char getWinner() {
        if (this.xBoard.hasWon()) {
            return X;
        } else if (this.oBoard.hasWon()) {
            return O;
        } else {
            return EMPTY;
        }
    }

    /**
     * Returns true if the game has a winner, false otherwise. A false value does not necessarily
     * mean the game is over.
     *
     * @return true if the game has a winner, false otherwise
     */
    public boolean hasWinner() {
        return this.xBoard.hasWon() || this.oBoard.hasWon();
    }

    /**
     * Returns true if the game is unwinnable for either player, false otherwise.
     *
     * @return true if the game is unwinnable for either player, false otherwise
     */
    public boolean isStalemate() {
        return !hasWinner() && getPlayed().cardinality() == 9;
    }

    /**
     * Plays the current player's turn at the ith position.
     *
     * @param i the ith position
     */
    public void playPosition(final int i) {
        playPosition(i % 3, i / 3);
    }

    /**
     * Plays the current player's turn at the given position.
     *
     * @param x the position along the x-axis
     * @param y the position along the y-axis
     */
    public void playPosition(final int x, final int y) {
        if (canPlayPosition(x, y) && !hasWinner()) {

            if (this.xTurn) {
                this.xBoard.playPosition(x, y);
            } else {
                this.oBoard.playPosition(x, y);
            }

            this.xTurn = !this.xTurn;
        }
    }

    /**
     * Resets and clears the board for a new game.
     */
    public void reset() {
        this.xBoard.reset();
        this.oBoard.reset();
        this.xTurn = true;
    }

    /**
     * Restores the game from a hash value.
     *
     * @param hash the hash value to restore the game from
     */
    public void restoreFromHash(final int hash) {
        this.oBoard.restoreFromHash(hash % 512); // 512 is 2^9
        this.xBoard.restoreFromHash(hash >> 9);

        this.xTurn = this.xBoard.getPlayed().cardinality() == this.oBoard.getPlayed().cardinality();
    }

    /**
     * Returns a BitSet of the played positions.
     *
     * @return the BitSet of the played positions
     */
    public BitSet getPlayed() {
        final BitSet played = new BitSet();
        played.or(this.xBoard.getPlayed());
        played.or(this.oBoard.getPlayed());
        return played;
    }

    @Override
    public int hashCode() {
        return (this.xBoard.hashCode() << 9) + this.oBoard.hashCode();
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof TicTacToeGame)) {
            return false;
        }

        final TicTacToeGame g = (TicTacToeGame) o;

        return this.xBoard.equals(g.xBoard) && this.oBoard.equals(g.oBoard) && this.xTurn == g.xTurn;
    }

    @Override
    public String toString() {
        final StringJoiner rows = new StringJoiner(System.lineSeparator());
        rows.add(Integer.toString(this.hashCode()));
        StringJoiner column = new StringJoiner("][", "[", "]");

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
                column = new StringJoiner("][", "[", "]");
            }
        }

        return rows.toString();
    }
}
