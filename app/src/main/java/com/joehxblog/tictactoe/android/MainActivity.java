package com.joehxblog.tictactoe.android;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import com.joehxblog.tictactoe.R;
import com.joehxblog.tictactoe.logic.TicTacToeGame;

import java.util.Arrays;
import java.util.stream.Stream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final GridLayout layout = findViewById(R.id.gridLayout);

        final int count = layout.getChildCount();

        final Button[][] buttons = new Button[3][3];

        final TicTacToeGame game = new TicTacToeGame();

        for (int i = 0; i < count; i++) {
            final int x = i % 3;
            final int y = i / 3;
            buttons[x][y] = (Button) layout.getChildAt(i);

            buttons[x][y].setOnClickListener(v -> {
                game.playPosition(x, y);
                buttons[x][y].setText(Character.toString(game.getValue(x, y)));
            });
        }

        Button newGameButton = findViewById(R.id.newGameButton);
        newGameButton.setOnClickListener(v -> {
            game.reset();
            Arrays.stream(buttons).flatMap(Arrays::stream).forEach(b -> b.setText(""));
        });
    }
}