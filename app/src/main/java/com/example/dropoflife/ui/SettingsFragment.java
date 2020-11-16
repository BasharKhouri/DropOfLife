package com.example.dropoflife.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreferenceCompat;

import com.example.dropoflife.Classes.User;
import com.example.dropoflife.Login;
import com.example.dropoflife.MainActivity;
import com.example.dropoflife.R;
import com.example.dropoflife.Splashscreen;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

import static android.content.Context.CONTEXT_IGNORE_SECURITY;
import static com.example.dropoflife.Login.SHARED_NAME;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * @author Hassan , Bashar
 */
public class SettingsFragment extends PreferenceFragmentCompat {

    Preference logoutET , adminET;
    SwitchPreferenceCompat darkMode;
    public boolean test;
    User user ;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        logoutET=findPreference("log");
        adminET=findPreference("admin");
        darkMode=findPreference("dark_mode_switch");
        FacebookSdk.sdkInitialize(getApplicationContext());
     user = MainActivity.user;

        if (logoutET != null) {
            logoutET.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {


                        FirebaseAuth.getInstance().signOut();
                        disconnectFromFacebook();
                        Intent in = new Intent(getContext(), Login.class);
                        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(in);

                    return true;
                }
            });
        }
        if(!user.getRole().getRole().equals("admin")){
            adminET.setVisible(false);
        }
        if(adminET!=null){
            adminET.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Intent intent = new Intent(getContext(),Admin.class);
                    startActivity(intent);
                    return true;
                }
            });
        }


        assert darkMode != null;
        darkMode.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                test = darkMode.isChecked();
                if (test){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

                }else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                saveDark(test);
                return false;
            }
        });

    }
    public void saveDark(boolean dark_theme){
        SharedPreferences shared = getActivity().getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit= shared.edit();
        edit.putBoolean("DARK",dark_theme);
        edit.apply();


    }
    private void disconnectFromFacebook() {

        if (AccessToken.getCurrentAccessToken() == null) {
            return; // already logged out
        }else {
            LoginManager.getInstance().logOut();
        }

    }

}

