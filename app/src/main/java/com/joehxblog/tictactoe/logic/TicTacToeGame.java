package com.joehxblog.tictactoe.logic;

public class TicTacToeGame {
    public TicTacToeBitBoard xBoard = new TicTacToeBitBoard();
    public TicTacToeBitBoard oBoard = new TicTacToeBitBoard();

    public boolean oTurn = false; // x's turn when oTurn = false

    public boolean canPlayPosition(int x, int y) {
        return xBoard.getPosition(x,y) && oBoard.getPosition(x,y);
    }

    public boolean hasWinner() {
        return xBoard.hasWon() || oBoard.hasWon();
    }

}
