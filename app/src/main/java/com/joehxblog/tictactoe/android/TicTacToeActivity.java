package com.joehxblog.tictactoe.android;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import com.joehxblog.tictactoe.R;
import com.joehxblog.tictactoe.logic.TicTacToeAI;
import com.joehxblog.tictactoe.logic.TicTacToeGame;

import java.util.Arrays;

public class TicTacToeActivity extends AppCompatActivity {

    final Button[][] buttons = new Button[3][3];
    final TicTacToeGame game = new TicTacToeGame();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tictactoe);

        setupPlayButtons();
        setupNewGameButton();
    }

    private void setupPlayButtons() {
        final GridLayout layout = findViewById(R.id.gridLayout);
        final int count = layout.getChildCount();

        final TicTacToeAI ai = new TicTacToeAI(this.game);

        for (int i = 0; i < count; i++) {
            final int x = i % 3;
            final int y = i / 3;
            this.buttons[x][y] = (Button) layout.getChildAt(i);

            this.buttons[x][y].setOnClickListener(v -> {
                this.game.playPosition(x, y);

                if (this.game.hasWinner()) {
                    setWinnerText();
                } else {
                    ai.playRandomPosition();

                    if (this.game.hasWinner()) {
                        setWinnerText();
                    }
                }

                setButtonTexts();
            });
        }
    }

    private void setupNewGameButton() {
        final Button newGameButton = findViewById(R.id.newGameButton);
        newGameButton.setOnClickListener(v -> {
            this.game.reset();
            Arrays.stream(this.buttons).flatMap(Arrays::stream).forEach(b -> b.setText(""));
            final TextView text = findViewById(R.id.winnerTextView);
            text.setText("");
        });
    }

    private void setWinnerText() {
        final TextView text = findViewById(R.id.winnerTextView);
        text.setText(this.game.getWinner() + " has won!");
    }

    private void setButtonTexts() {
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                this.buttons[x][y].setText(Character.toString(this.game.getValue(x, y)));
            }
        }
    }
}