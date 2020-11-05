package com.example.dropoflife.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.example.dropoflife.Classes.User;
import com.example.dropoflife.Login;
import com.example.dropoflife.MainActivity;
import com.example.dropoflife.R;
import com.google.firebase.auth.FirebaseAuth;

public class SettingsFragment extends PreferenceFragmentCompat {

    Preference logoutET;
    SwitchPreferenceCompat darkMode;
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        logoutET=findPreference("log");
        darkMode=findPreference("dark_mode_switch");

        if (logoutET != null) {
            logoutET.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {


                        FirebaseAuth.getInstance().signOut();
                        Intent in = new Intent(getContext(), Login.class);
                        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(in);

                    return true;
                }
            });
        }


    }



}