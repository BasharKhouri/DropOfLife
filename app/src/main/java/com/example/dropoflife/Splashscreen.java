package com.example.dropoflife;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.dropoflife.Classes.Roles;
import com.example.dropoflife.Classes.SingletonPost;
import com.example.dropoflife.Classes.User;
import com.example.dropoflife.ui.SettingsFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import static com.example.dropoflife.Login.SHARED_NAME;

public class Splashscreen extends AppCompatActivity {
    private static int SPLASH_TIMEOUT = 100;
    static boolean dark_theme=false;
    private FirebaseAuth mAuth;
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    private Roles role;
    public static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        // add go to home fragment.
        if (currentUser != null) {
            fStore.collection("users").document(currentUser.getUid())
                    .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                            user = value.toObject(User.class);
                            role = user.getRole();
                            Log.w("success ", "LoadedUser");
                            SingletonPost singletonPost = SingletonPost.getInstance();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));

                        }
                    });


        }else {
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
