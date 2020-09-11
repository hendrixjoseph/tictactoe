package com.joehxblog.tictactoe.logic;

public class TicTacToeGame {
    private final TicTacToeBitBoard xBoard = new TicTacToeBitBoard();
    private final TicTacToeBitBoard oBoard = new TicTacToeBitBoard();

    private boolean xTurn = true; // o's turn when xTurn = false

    public boolean canPlayPosition(final int x, final int y) {
        return !(this.xBoard.getPosition(x,y) || this.oBoard.getPosition(x,y));
    }

    public char getValue(final int x, final int y) {
        if (this.xBoard.getPosition(x,y)) {
            return 'x';
        } else if (this.oBoard.getPosition(x,y)) {
            return 'o';
        } else {
            return ' ';
        }
    }

    public boolean hasWinner() {
        return this.xBoard.hasWon() || this.oBoard.hasWon();
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
        xTurn = true;
    }
}
