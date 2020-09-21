package com.joehxblog.tictactoe.android;

import android.os.Bundle;

import androidx.preference.CheckBoxPreference;
import androidx.preference.PreferenceFragmentCompat;
import com.joehxblog.tictactoe.R;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(final Bundle savedInstanceState, final String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        CheckBoxPreference easy = findPreference("tictactoe-easy-difficulty");
        CheckBoxPreference medium = findPreference("tictactoe-medium-difficulty");
        CheckBoxPreference hard = findPreference("tictactoe-hard-difficulty");

        easy.setOnPreferenceChangeListener((box, newValue) -> {

            if (Boolean.TRUE.equals(newValue)) {
                medium.setChecked(false);
                hard.setChecked(false);
            }

            return false;
        });
    }
}
