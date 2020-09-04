package com.joehxblog.tictactoe.logic;

public class TicTacToeBitBoard {
    private static final int BOARD_SIZE = 3;

    private final boolean[][] board = new boolean[BOARD_SIZE][BOARD_SIZE];

    public boolean getPosition(final int x, final int y) {
        return this.board[x][y];
    }

    public void playPosition(final int x, final int y) {
        this.board[x][y] = true;
    }

    public boolean hasWon() {
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
    }
}
