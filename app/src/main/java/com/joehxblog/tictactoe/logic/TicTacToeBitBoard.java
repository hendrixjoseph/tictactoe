package com.joehxblog.tictactoe.logic;

public class TicTacToeBitBoard {
    private static final int BOARD_SIZE = 3;

    private boolean[][] board = new boolean[BOARD_SIZE][BOARD_SIZE];

    public boolean getPosition(int x, int y) {
        return board[x][y];
    }

    public void playPosition(int x, int y) {
        board[x][y] = true;
    }

    public boolean hasWon() {
               // horizontals
        return (board[0][0] && board[0][1] && board[0][2])
            || (board[1][0] && board[1][1] && board[1][2])
            || (board[2][0] && board[2][1] && board[2][2])
               // verticals
            || (board[0][0] && board[1][0] && board[2][0])
            || (board[0][1] && board[1][1] && board[2][1])
            || (board[0][2] && board[1][2] && board[2][2])
               // diagonals
            || (board[0][0] && board[1][1] && board[2][2])
            || (board[0][2] && board[1][1] && board[2][0]);
    }
}
