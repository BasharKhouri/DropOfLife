package com.example.dropoflife;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.dropoflife.Classes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    User user ;
    private TextView forgotPasswordButton;
    public String email , password ;
    public EditText emailET , passwordET;
    private FirebaseAuth mAuth;
    public static final String SHARED_NAME=".shared";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        emailET = findViewById(R.id.loginUsername);
        passwordET = findViewById(R.id.loginUserPassword);
        load();

        forgotPasswordButton=(TextView)findViewById(R.id.forgotPassTextButton);
        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPassowrd();
            }
        });
    }

    private void forgotPassowrd() {
        final EditText restMail = new EditText(this);
        AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(this);
        passwordResetDialog.setTitle(R.string.Reset_password);
        passwordResetDialog.setMessage(R.string.enter_email_to_receive_link);
        passwordResetDialog.setView(restMail);

        passwordResetDialog.setPositiveButton(R.string.Okay, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String mail = restMail.getText().toString();
                    if(!mail.isEmpty())
                    mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(Login.this, R.string.reset_password_email_has_been_sent, Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Login.this, R.string.reset_password_email_has_been_sent, Toast.LENGTH_SHORT).show();

                        }
                    });

            }
        });
        passwordResetDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });


        passwordResetDialog.create().show();

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
        }else{
            save(email,password);
        validateLogIn(email,password);
        }


    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

       // add go to home fragment.
        if(currentUser!=null)
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
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
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
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
    public void save(String username,String password){
        SharedPreferences shared = getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit= shared.edit();
        edit.putString("USER_NAME",username);
        edit.putString("PASSWORD",password);
        edit.apply();

    }
    public void load(){
        SharedPreferences shared = getSharedPreferences(SHARED_NAME,Context.MODE_PRIVATE);
        String usern = shared.getString("USER_NAME","");
        String passs = shared.getString("PASSWORD","");

            emailET.setText(usern);
            passwordET.setText(passs);


    }
//    @Override
//    public void onBackPressed(){
//
//      finish();
//    }



}
