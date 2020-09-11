package com.joehxblog.tictactoe.logic;

import java.util.BitSet;

public class TicTacToeBitBoard {
    private static final int BOARD_SIZE = 3;

    //private final boolean[][] board = new boolean[BOARD_SIZE][BOARD_SIZE];
    private final BitSet board = new BitSet();

    private int convert(final int x, final int y) {
        return x + y * 3;
    }

    public boolean getPosition(final int x, final int y) {
        return this.board.get(convert(x,y));
    }

    public void playPosition(final int x, final int y) {
        this.board.set(convert(x,y));
    }

    public void reset() {
        this.board.clear();
    }

    public boolean hasWon() {
               // horizontals
        return this.board.get(0, 3).cardinality() == 3
            || this.board.get(3, 6).cardinality() == 3
            || this.board.get(6, 9).cardinality() == 3
               // verticals
            || this.board.get(0) && this.board.get(3) && this.board.get(6)
            || this.board.get(1) && this.board.get(4) && this.board.get(7)
            || this.board.get(2) && this.board.get(5) && this.board.get(8)
               // diagonals
            || this.board.get(0) && this.board.get(4) && this.board.get(8)
            || this.board.get(2) && this.board.get(4) && this.board.get(6);
        /*
        // horizontals
        return (this.board[0][0] && this.board[0][1] && this.board[0][2])
            || (this.board[1][0] && this.board[1][1] && this.board[1][2])
            || (this.board[2][0] && this.board[2][1] && this.board[2][2])
               // verticals
            || (this.board[0][0] && this.board[1][0] && this.board[2][0])
            || (this.board[0][1] && this.board[1][1] && this.board[2][1])
            || (this.board[0][2] && this.board[1][2] && this.board[2][2])
               // diagonals
            || (this.board[0][0] && this.board[1][1] && this.board[2][2])
            || (this.board[0][2] && this.board[1][1] && this.board[2][0]);

         */
    }
}
