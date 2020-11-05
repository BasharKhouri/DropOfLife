package com.example.dropoflife;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class Splashscreen extends AppCompatActivity {
    private static int SPLASH_TIMEOUT = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent welcomeIntent = new Intent(Splashscreen.this, MainActivity.class);
                startActivity(welcomeIntent);
                finish();
            }
        }, SPLASH_TIMEOUT);
    }
}
