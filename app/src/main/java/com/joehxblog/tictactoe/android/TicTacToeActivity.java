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

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

import static com.joehxblog.tictactoe.logic.TicTacToeAI.Difficulty;

public class TicTacToeActivity extends AppCompatActivity {

    private enum SETTINGS {SINGLE_PLAYER, DIFFICULTY, GAMEHASH};

    private final Button[][] buttons = new Button[3][3];
    private final TicTacToeGame game = new TicTacToeGame();
    private final TicTacToeAI ai = new TicTacToeAI(this.game);

    private boolean singlePlayer = true;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tictactoe);

        setupPlayButtons();

        if (savedInstanceState != null) {
            final int gamehash = savedInstanceState.getInt(SETTINGS.GAMEHASH.toString());

            if (gamehash > 0) {
                this.game.restoreFromHash(gamehash);
                setButtonTexts();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(SETTINGS.GAMEHASH.toString(), this.game.hashCode());
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        final MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tictactoe_menu, menu);

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        setDifficultyFromPreferences(sharedPreferences, menu);
        setSinglePlayerFromPreferences(sharedPreferences, menu);

        return true;
    }

    private void setSinglePlayerFromPreferences(SharedPreferences sharedPreferences, Menu menu) {
        this.singlePlayer = sharedPreferences.getBoolean(SETTINGS.SINGLE_PLAYER.toString(), true);
        menu.findItem(R.id.single_player).setChecked(this.singlePlayer);
    }

    private void setDifficultyFromPreferences(SharedPreferences sharedPreferences, Menu menu) {
        final String difficultyString = sharedPreferences.getString(SETTINGS.DIFFICULTY.toString(), Difficulty.EASY.toString());
        final Difficulty difficulty = Difficulty.valueOf(difficultyString);
        this.ai.setDifficulty(difficulty);
        menu.findItem(difficulty.getMenuId()).setChecked(true);
    }

    public void newGame(final MenuItem item) {
        this.game.reset();
        Arrays.stream(this.buttons).flatMap(Arrays::stream).forEach(b -> {
            b.setText("");
            b.setClickable(true);
        });
        final TextView text = findViewById(R.id.winnerTextView);
        text.setText("");
    }

    public void setDifficulty(@NotNull final MenuItem item) {
        final Difficulty difficulty = Difficulty.getByMenuId(item.getItemId());
        this.ai.setDifficulty(difficulty);

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.edit().putString(SETTINGS.DIFFICULTY.toString(), difficulty.toString()).apply();

        item.setChecked(true);
    }

    public void toggleSinglePlayer(MenuItem item) {
        this.singlePlayer = !this.singlePlayer;
        item.setChecked(this.singlePlayer);

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.edit().putBoolean(SETTINGS.SINGLE_PLAYER.toString(), this.singlePlayer).apply();
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

    private void clickPlayButton(final int x, final int y) {
        this.buttons[x][y].setClickable(false);
        this.game.playPosition(x, y);

        if (this.game.hasWinner()) {
            setWinnerText();
        } else if (this.game.isStalemate()) {
            setStalemateText();
        } else if (singlePlayer) {
            final int position = this.ai.play();
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

        for (final Button[] buttons : this.buttons) {
            for (final Button button : buttons) {
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
        final String winnerTextTemplate = this.getText(R.string.winner_text_template).toString();
        final String winnerText = String.format(winnerTextTemplate, this.game.getWinner());
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