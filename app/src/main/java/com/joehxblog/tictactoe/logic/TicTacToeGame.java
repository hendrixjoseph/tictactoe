package com.joehxblog.tictactoe.logic;

public class TicTacToeGame {
    private TicTacToeBitBoard xBoard = new TicTacToeBitBoard();
    private TicTacToeBitBoard oBoard = new TicTacToeBitBoard();

    private boolean xTurn = true; // o's turn when xTurn = false

    public boolean canPlayPosition(int x, int y) {
        return xBoard.getPosition(x,y) && oBoard.getPosition(x,y);
    }

    public boolean hasWinner() {
        return xBoard.hasWon() || oBoard.hasWon();
    }

    public void playPosition(int x, int y) {
        if (canPlayPosition(x,y)) {

            if (xTurn) {
                xBoard.playPosition(x, y);
            } else {
                oBoard.playPosition(x, y);
            }

            xTurn = !xTurn;
        }
    }

}
