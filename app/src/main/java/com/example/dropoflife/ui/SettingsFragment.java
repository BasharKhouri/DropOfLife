package com.example.dropoflife.ui;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.example.dropoflife.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}