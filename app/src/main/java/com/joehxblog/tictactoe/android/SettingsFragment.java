package com.joehxblog.tictactoe.android;

import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;
import com.joehxblog.tictactoe.R;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(final Bundle savedInstanceState, final String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}
