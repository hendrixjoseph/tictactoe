package com.joehxblog.tictactoe.logic;

import java.util.BitSet;

public class TicTacToeBitBoard {
    private static final int BOARD_SIZE = 3;

    private final BitSet board = new BitSet();

    private int convert(final int x, final int y) {
        return x + y * BOARD_SIZE;
    }

    /**
     * Gets the boolean value for the supplied position. True means the position is
     * played, false means it has not.
     *
     * @param x the position along the x-axis
     * @param y the position along the y-axis
     *
     * @return whether this position has been played
     */
    public boolean getPosition(final int x, final int y) {
        return this.board.get(convert(x,y));
    }

    /**
     * Plays based on the given position. In other words, sets the position to true.
     *
     * @param x the position along the x-axis
     * @param y the position along the y-axis
     */
    public void playPosition(final int x, final int y) {
        this.board.set(convert(x,y));
    }

    /**
     * Resets and clears the board.
     */
    public void reset() {
        this.board.clear();
    }

    /**
     * Returns the played BitSet. This is a defensive copy - the original is not returned.
     *
     * @return the played BitSet
     */
    public BitSet getPlayed() {
        final BitSet played = new BitSet();
        played.or(this.board);
        return played;
    }

    /**
     * Returns true if this board is in a winning position, false otherwise.
     *
     * @return true if this board is in a winning position, false otherwise
     */
    public boolean hasWon() {
               // horizontals
        return this.board.get(0, 3).cardinality() == BOARD_SIZE
            || this.board.get(3, 6).cardinality() == BOARD_SIZE
            || this.board.get(6, 9).cardinality() == BOARD_SIZE
               // verticals
            || this.board.get(0) && this.board.get(3) && this.board.get(6)
            || this.board.get(1) && this.board.get(4) && this.board.get(7)
            || this.board.get(2) && this.board.get(5) && this.board.get(8)
               // diagonals
            || this.board.get(0) && this.board.get(4) && this.board.get(8)
            || this.board.get(2) && this.board.get(4) && this.board.get(6);
    }

    /**
     * Restore this board from a hash.
     *
     * @param hash the hash to restore this board from
     */
    public void restoreFromHash(final int hash) {
        this.board.clear();

        int tempHash = hash;

        for (int i = 8; i >= 0 ; i--) {
            if (tempHash % 2 == 1) {
                this.board.set(i);
            }

            tempHash = tempHash >> 1;
        }
    }

    @Override
    public int hashCode() {
        int hash = 0;

        for (int i = 0; i < 9; i++) {
            hash = (hash << 1) + (this.board.get(i) ? 1 : 0);
        }

        return hash;
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof TicTacToeBitBoard)) {
            return false;
        }

        final TicTacToeBitBoard g = (TicTacToeBitBoard) o;

        return this.board.equals(g.board);
    }
}
