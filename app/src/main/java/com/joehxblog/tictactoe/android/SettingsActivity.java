package com.joehxblog.tictactoe.android;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.joehxblog.tictactoe.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
    }
}
