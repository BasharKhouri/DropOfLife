package com.example.dropoflife;

import androidx.annotation.NonNull;

import com.example.dropoflife.Classes.BloodType;
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
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dropoflife.Classes.User;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;


public class Login extends AppCompatActivity {
    User user;
    private TextView forgotPasswordButton;
    public String email, password;
    public EditText emailET, passwordET;
    private FirebaseAuth mAuth;
    private CallbackManager mCallbackManager;
    public static final String SHARED_NAME = ".shared";
    //private ImageView facebook_button;
    private CallbackManager callbackManager;
    private LoginManager loginManager;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Bundle bundle = new Bundle();
    LoginButton facebook_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
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
        //facebook_button = findViewById(R.id.facebook_button);
        facebook_button= findViewById(R.id.login_button);
        facebook_button.setReadPermissions("email","public_profile");
//        facebook_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                loginManager.logInWithReadPermissions(Login.this, Arrays.asList("email", "public_profile", "user_birthday"));
//
//            }
//        });

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

    }

    private void handleFacebookToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                FirebaseUser user= mAuth.getCurrentUser();
                Toast.makeText(Login.this, "Faaack youuuuuuuuu", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
    }

//    private void facebookLogin() {
//

    /**
     * Bashar comment
     *
     * User faceBooUser =   new User(fullName, blood,birthDate, sex, email,imagePath, new Roles(1));
     *
     *   pic
     *   final String imagePath = "images/"+MainActivity.firebaseUser.getUid()+".jpeg";
     *                 StorageReference riversRef = mStorageRef.child(imagePath);
     *
     *         riversRef.putFile(imageUri)
     *                 .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
     *                     @Override
     *                     public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
     *                         pd.dismiss();
     *                         user.setProfilePic( taskSnapshot.getStorage()+"");
     *                         fStore.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set(user);
     *
     *                     }
     *                 })
     *                 .addOnFailureListener(new OnFailureListener() {
     *                     @Override
     *                     public void onFailure(@NonNull Exception exception) {
     *                         pd.dismiss();
     *                         Toast.makeText(getContext(), R.string.upload_not_successful, Toast.LENGTH_SHORT).show();
     *                         // Handle unsuccessful uploads
     *                         // ...
     *
     *                     }
     *                 }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
     *             @Override
     *             public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
     *                 double progressPercent =(100*snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
     *                 ;
     *                 pd.setMessage((int)progressPercent+" ");
     *             }
     *         });
     *
     *                                 db.collection("users").document(mAuth.getUid()).set(user)
     *                                 .addOnSuccessListener(new OnSuccessListener<Void>() {
     *                                     @Override
     *                                     public void onSuccess(Void aVoid) {
     *                                         Toast.makeText(Registration.this, "User Registerd", Toast.LENGTH_SHORT).show();
     *                                         Intent intent =new Intent(getApplicationContext(), MainActivity.class);
     *                                         startActivity(intent);
     *                                     }
     *                                 });
     *
     */

//        loginManager = LoginManager.getInstance();
//        callbackManager = CallbackManager.Factory.create();
//
//        loginManager
//                .registerCallback(
//                        callbackManager,
//                        new FacebookCallback<LoginResult>() {
//
//                            @Override
//                            public void onSuccess(LoginResult loginResult) {
//                                GraphRequest request = GraphRequest.newMeRequest(
//
//                                        loginResult.getAccessToken(),
//
//                                        new GraphRequest.GraphJSONObjectCallback() {
//
//                                            @Override
//                                            public void onCompleted(JSONObject object,
//                                                                    GraphResponse response) {
//
//                                                if (object != null) {
//                                                    try {
//                                                        String name = object.getString("name");
//
//                                                        String birth = object.getString("birthday");
//                                                        Date bod=new SimpleDateFormat("dd/MM/yyyy").parse(birth);
//                                                        String email = object.getString("email");
//                                                        String fbUserID = object.getString("id");
//                                                        User user = new User(name,bod,"Male", email,null);
//                                                        Intent in = new Intent(getApplicationContext(),MainActivity.class);
//
//                                                        bundle.putSerializable("elUser", user);
//                                                        in.putExtras(bundle);
//                                                        startActivity(in);
//                                                        disconnectFromFacebook();
//                                                        //userName,new BloodType(1),dateOfBirth,sex,email,profilePic
//
//                                                        // or call your API
//                                                    } catch (JSONException | NullPointerException | BloodType.IncorrectBloodIDException | ParseException e) {
//                                                        e.printStackTrace();
//                                                    }
//                                                }
//                                            }
//                                        });
//
//                                Bundle parameters = new Bundle();
//                                parameters.putString("fields", "id, name, email, gender, birthday");
//                                request.setParameters(parameters);
//                                request.executeAsync();
//
//                            }
//
//                            @Override
//                            public void onCancel() {
//
//                            }
//
//                            @Override
//                            public void onError(FacebookException error) {
//
//                            }
//                        });
//    }

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
        FirebaseUser currentUser = mAuth.getCurrentUser();

        // add go to home fragment.
        if (currentUser != null)
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
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
//    @Override
//    public void onBackPressed(){
//
//      finish();
//    }



    private void disconnectFromFacebook() {

        if (AccessToken.getCurrentAccessToken() == null) {
            return; // already logged out
        }

        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/permissions/",
                null,
                HttpMethod.DELETE,
                new GraphRequest
                        .Callback() {
                    @Override
                    public void onCompleted(GraphResponse graphResponse)
                    {
                        LoginManager.getInstance().logOut();
                    }
                })
                .executeAsync();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}