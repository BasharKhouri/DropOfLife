package com.example.dropoflife;

import androidx.annotation.NonNull;

import com.example.dropoflife.Classes.BloodType;
import com.example.dropoflife.Classes.Roles;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.appevents.AppEventsLogger;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dropoflife.Classes.User;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;


public class Login extends AppCompatActivity {
    private static final int RC_SIGN_IN =123 ;
    User user;
    private TextView forgotPasswordButton;
    public String email, password;
    public EditText emailET, passwordET;
    private FirebaseAuth mAuth;
    public static final String SHARED_NAME = ".shared";
    private CallbackManager callbackManager;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseStorage storage;
    private StorageReference mStorageRef;
    LoginButton facebook_button;
    private  GoogleSignInClient mGoogleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();
        emailET = findViewById(R.id.loginUsername);
        passwordET = findViewById(R.id.loginUserPassword);
        load();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        forgotPasswordButton = (TextView) findViewById(R.id.forgotPassTextButton);
        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPassowrd();
            }
        });
        callbackManager = CallbackManager.Factory.create();
//        facebookLogin();
//        facebook_button2 = findViewById(R.id.facebook_button);
        facebook_button = findViewById(R.id.login_button);
        facebook_button.setReadPermissions("email", "public_profile", "user_gender", "user_age_range");
//        facebook_button2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                loginManager.logInWithReadPermissions(Login.this, Arrays.asList("email", "public_profile", "user_birthday","user_gender","user_age_range"));
//
//            }
//        });
//
        facebook_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signIn();
            }
        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            User googleUser = new User();
                            googleUser.setEmail(user.getEmail());
                            googleUser.setUserName(user.getDisplayName());
                            googleUser.setProfilePic(user.getPhotoUrl().toString());

                            try {
                                googleUser.setBloodType(new BloodType(8));
                            } catch (BloodType.IncorrectBloodIDException e) {
                                e.printStackTrace();
                            }
                            db.collection("users").document(mAuth.getUid()).set(googleUser)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(Login.this, "Success", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                        }
                                    });
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "Task failed successfully", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    private void handleFacebookToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                FirebaseUser user = mAuth.getCurrentUser();
                String name = user.getDisplayName();
                String email = user.getEmail();
                String userId = user.getProviderId();
                User facebookUser = new User();
                facebookUser.setEmail(email);
                facebookUser.setUserName(name);
                try {
                    facebookUser.setBloodType(new BloodType(8));
                } catch (BloodType.IncorrectBloodIDException e) {
                    e.printStackTrace();
                }

                try {
                    facebookUser.setProfilePic(getStringImage(getFacebookProfilePicture(userId)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                db.collection("users").document(mAuth.getUid()).set(facebookUser)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Login.this, "Success", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }
                        });
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
                if (!mail.isEmpty())
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
        Intent intent = new Intent(this, Registration.class);
        startActivity(intent);
    }


    public void signIn(View view) {

        email = emailET.getText().toString();
        password = passwordET.getText().toString();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            if (TextUtils.isEmpty(email))
                emailET.setError(null);
            if (TextUtils.isEmpty(password))
                passwordET.setError(null);
        } else {
            save(email, password);
            validateLogIn(email, password);
        }


    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
    }

    /**
     * @param email    the user Email
     * @param password the user Password
     */
    public void validateLogIn(final String email, final String password) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            System.out.println("success");
                            FirebaseUser currentUser = mAuth.getCurrentUser();

                            //go to Home fragment
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
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

    public void save(String username, String password) {
        SharedPreferences shared = getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = shared.edit();
        edit.putString("USER_NAME", username);
        edit.putString("PASSWORD", password);
        edit.apply();

    }

    public void load() {
        SharedPreferences shared = getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE);
        String usern = shared.getString("USER_NAME", "");
        String passs = shared.getString("PASSWORD", "");

        emailET.setText(usern);
        passwordET.setText(passs);


    }

    public void face(View view) {
    }

    @Override
    public void onBackPressed() {

        finish();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.e("EL GOOGLE" ,e.getMessage());
            }
        } else
            callbackManager.onActivityResult(requestCode, resultCode, data);
    }



    public static Bitmap getFacebookProfilePicture(String userID) throws IOException {
        URL imageURL = new URL("https://graph.facebook.com/" + userID + "/picture?type=large");
        Bitmap bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());

        return bitmap;
    }

    //    public String getStringImage(Bitmap bmp) {
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        bmp.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
//        byte[] imageBytes = byteArrayOutputStream.toByteArray();
//        return  Base64.encodeToString(imageBytes, Base64.DEFAULT);
//
//    }
    public String getStringImage(Bitmap bmp) {


        try {
            final boolean[] success = {false};
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] imageBytes = byteArrayOutputStream.toByteArray();
            storage = FirebaseStorage.getInstance();
            mStorageRef = storage.getReference();
            final String imagePath = "images/" + MainActivity.firebaseUser.getUid() + ".jpeg";
            StorageReference riversRef = mStorageRef.child(imagePath);
            riversRef.putBytes(imageBytes).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    success[0] = true;
                }
            });

            if (success[0])
                return imagePath;


        } catch (Exception ignored) {

        }
        return null;
    }
}



