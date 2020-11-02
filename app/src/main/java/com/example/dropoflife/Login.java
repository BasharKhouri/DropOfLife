package com.example.dropoflife;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.core.utilities.Utilities;

public class Login extends AppCompatActivity {
    public String email , password ;
    public EditText emailET , passwordET;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
    }

    public void goToRegister(View view) {
        Intent intent = new Intent(this,Registration.class);
        startActivity(intent);
    }

    public void signIn(View view) {
     email = emailET.getText().toString();
     password=passwordET.getText().toString();
        if(TextUtils.isEmpty(email)||TextUtils.isEmpty(password)) {
            if(TextUtils.isEmpty(email))
                emailET.setError(null);
            if(TextUtils.isEmpty(password))
                passwordET.setError(null);
        }else
        validateLogIn(email,password);
    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
       // add go to home fragment.
        if(currentUser!=null)
        startActivity(new Intent(getApplicationContext(),MainActivity.class).putExtra("currentUser",currentUser));
    }

    /**
     * @param email the user Email
     * @param password the user Password
     */
    public void validateLogIn(final String email , final String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            System.out.println("success");
                            FirebaseUser currentUser = mAuth.getCurrentUser();
                          //go to Home fragment
                            startActivity(new Intent(getApplicationContext(),MainActivity.class).putExtra("currentUser",currentUser));
                        } else {

                            // If sign in fails, display a message to the user.
                            System.out.println("failure");
                            Toast.makeText(Login.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                           emailET.setError(null);

                        }
                        // ...
                    }
                });
    }


}