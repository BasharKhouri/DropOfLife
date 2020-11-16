package com.example.dropoflife;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dropoflife.Classes.Hospitals;
import com.example.dropoflife.Classes.Roles;
import com.example.dropoflife.Classes.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.io.File;

import bolts.Task;

/**
 * author Bashar Khouri,Hassan wael ,Bashar Nimri
 */
public class MainActivity extends AppCompatActivity {
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    private Roles role ;
    public static User user;
    private FirebaseAuth mAuth;
    public static FirebaseUser firebaseUser;
    public static File localFile ;
    Intent intent;
    private static Hospitals hospital ;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        mAuth= FirebaseAuth.getInstance();
        firebaseUser =mAuth.getCurrentUser();
        loadUser();
        try {
            loadImageAndHospital();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        //init bar
            try {
                setContentView(R.layout.activity_main);
                BottomNavigationView navView = findViewById(R.id.nav_view);

                // Passing each menu ID as a set of Ids because each
                // menu should be considered as top level destinations.

                     intent = getIntent();
                if (role==null ) {
                    AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                            R.id.navigation_home, R.id.navigation_profile, R.id.navigation_notifications, R.id.navigation_settings1,R.id.navigation_AdminView)
                            .build();
                findViewById(R.id.navigation_AdminView).setVisibility(View.GONE);
                    NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
                    NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
                    NavigationUI.setupWithNavController(navView, navController);
                }else if (role.equals( new Roles(1))){
                    AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                            R.id.navigation_home, R.id.navigation_profile, R.id.navigation_notifications, R.id.navigation_settings1,R.id.navigation_AdminView)
                            .build();
                    NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
                    NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
                    NavigationUI.setupWithNavController(navView, navController);
                }
                else {
                    AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                            R.id.navigation_home, R.id.navigation_profile, R.id.navigation_notifications, R.id.navigation_settings1,R.id.navigation_AdminView)
                            .build();
                    findViewById(R.id.navigation_AdminView).setVisibility(View.GONE);
                    NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
                    NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
                    NavigationUI.setupWithNavController(navView, navController);

                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }


    }

    private void loadUser(){
        try {
            //get the required reference
            DocumentReference documentReference = fStore.collection("users").document(firebaseUser.getUid());
            synchronized (documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {

                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    user = value.toObject(User.class);
                    role = user.getRole();
                }
            })) {  }
        }catch (Exception ignore){
            Log.w("Error",ignore.getMessage());
        }
    }


    private void loadImageAndHospital() throws InterruptedException {
       while (user==null){
           Thread.sleep(1);
       }
       loadImage();
       loadHospital();
    }




    private Task loadImage(){
        try {
            StorageReference riversRef = storage.getReferenceFromUrl(user.getProfilePic());
            localFile = File.createTempFile("userPic", "jpg");
            riversRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Log.w("success ","ImageLoaded");
                }
            });

        }catch (Exception e ){
            Log.w("Error",e.getMessage());
        }
        return null ;
    }

    private Task loadHospital(){
        if(user.getHospital()!=null){
            fStore.collection("Hospitals").document(user.getHospital()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    hospital = value.toObject(Hospitals.class);
                }
            });

        }
     return  null;
    }

    public static Hospitals getHospital() {
        return hospital;
    }
}
