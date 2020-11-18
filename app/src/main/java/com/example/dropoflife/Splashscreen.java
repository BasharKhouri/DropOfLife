package com.example.dropoflife;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.dropoflife.ui.SettingsFragment;

import static com.example.dropoflife.Login.SHARED_NAME;

public class Splashscreen extends AppCompatActivity {
    private static int SPLASH_TIMEOUT = 100;
    static boolean dark_theme=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
               new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent welcomeIntent = new Intent(Splashscreen.this, Login.class);
                startActivity(welcomeIntent);
                finish();
            }
        }, SPLASH_TIMEOUT);
               loadDark();
    }


    public  void loadDark(){
        SharedPreferences shared = getSharedPreferences(SHARED_NAME,Context.MODE_PRIVATE);
        dark_theme = shared.getBoolean("DARK",false);

        if (dark_theme){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

    }

}
