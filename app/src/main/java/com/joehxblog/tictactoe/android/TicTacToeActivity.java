package com.joehxblog.tictactoe.android;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;
import androidx.preference.PreferenceManager;

import com.joehxblog.tictactoe.R;
import com.joehxblog.tictactoe.logic.TicTacToeAI;
import com.joehxblog.tictactoe.logic.TicTacToeGame;

import static com.joehxblog.tictactoe.logic.TicTacToeAI.Difficulty;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class TicTacToeActivity extends AppCompatActivity {

    private static final String DIFFICULTY = "DIFFICULTY";

    private final Button[][] buttons = new Button[3][3];
    private final TicTacToeGame game = new TicTacToeGame();
    private final TicTacToeAI ai = new TicTacToeAI(this.game);

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tictactoe);

        setupPlayButtons();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tictactoe_menu, menu);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String difficultyString = sharedPreferences.getString(DIFFICULTY, Difficulty.EASY.toString());
        Difficulty difficulty = Difficulty.valueOf(difficultyString);

        ai.setDifficulty(difficulty);

        menu.findItem(difficulty.getMenuId()).setChecked(true);

        return true;
    }

    public void newGame(MenuItem item) {
        this.game.reset();
        Arrays.stream(this.buttons).flatMap(Arrays::stream).forEach(b -> {
            b.setText("");
            b.setClickable(true);
        });
        final TextView text = findViewById(R.id.winnerTextView);
        text.setText("");
    }

    public void setDifficulty(@NotNull MenuItem item) {
        Difficulty difficulty = Difficulty.getByMenuId(item.getItemId());
        ai.setDifficulty(difficulty);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.edit().putString(DIFFICULTY, difficulty.toString()).apply();

        item.setChecked(true);
    }

    private void setupPlayButtons() {
        final GridLayout layout = findViewById(R.id.gridLayout);
        final int count = layout.getChildCount();

        for (int i = 0; i < count; i++) {
            final int x = i % 3;
            final int y = i / 3;
            this.buttons[x][y] = (Button) layout.getChildAt(i);

            this.buttons[x][y].setOnClickListener(v -> clickPlayButton(x,y));
        }
    }

    private void clickPlayButton(int x, int y) {
        this.buttons[x][y].setClickable(false);
        this.game.playPosition(x, y);

        if (this.game.hasWinner()) {
            setWinnerText();
        } else if (this.game.isStalemate()) {
            setStalemateText();
        } else {
            int position = ai.play();
            this.buttons[position % 3][position / 3].setClickable(false);

            if (this.game.hasWinner()) {
                setWinnerText();
            // reasonably, stalemate can only occur on odd
            // plays, so this else if condition should never be true
            } else if (this.game.isStalemate()) {
                setStalemateText();
            }
        }

        setButtonTexts();
    }

    private void think() {
        Button prevButton = null;

        for (Button[] buttons : this.buttons) {
            for (Button button : buttons) {
                if (prevButton != null) {
                    prevButton.setText("");
                }

                if (TextUtils.isEmpty(button.getText())) {
                    button.setText("?");
                    prevButton = button;
                }
            }
        }
    }

    private void setWinnerText() {
        final TextView text = findViewById(R.id.winnerTextView);
        String winnerTextTemplate = this.getText(R.string.winner_text_template).toString();
        String winnerText = String.format(winnerTextTemplate, this.game.getWinner());
        text.setText(winnerText);
    }

    private void setStalemateText() {
        final TextView text = findViewById(R.id.winnerTextView);
        text.setText(R.string.stalemate_text);
    }

    private void setButtonTexts() {
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                this.buttons[x][y].setText(Character.toString(this.game.getValue(x, y)));
            }
        }
    }
}