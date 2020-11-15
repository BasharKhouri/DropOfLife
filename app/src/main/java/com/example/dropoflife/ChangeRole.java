package com.example.dropoflife;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.dropoflife.Classes.BloodType;
import com.example.dropoflife.Classes.Roles;
import com.example.dropoflife.Classes.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;

public class ChangeRole extends AppCompatActivity {
    ImageView userImageIV;
    TextView userNameET;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    LinearLayout hospitalLinearLayout;
    Roles hospitalWorkerRole ;
    Spinner roleSpinner ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_role);

       //init values
        userNameET = (TextView)findViewById(R.id.profile_page_userName_role);
        userImageIV=(ImageView) findViewById(R.id.profile_image_role);
        hospitalLinearLayout = (LinearLayout) findViewById(R.id.select_hospital_changeRole);
        roleSpinner = (Spinner) findViewById(R.id.roleSpinner);
        //init adapter for the spinner
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, Roles.roles);
        roleSpinner.setAdapter(spinnerArrayAdapter);
        //set layout gone
        hospitalLinearLayout.setVisibility(View.INVISIBLE);
        try{
            hospitalWorkerRole = new Roles(2);
        }catch (Exception e ){
            Log.w("Error", e.getMessage());
        }

        Intent intent = getIntent();
        User selectedUser;
        if (intent.hasExtra("user")) {
            selectedUser = intent.getParcelableExtra("user");
                if (selectedUser.getRole().equals(hospitalWorkerRole)) {
                hospitalLinearLayout.setVisibility(View.VISIBLE);
            }


            if (selectedUser.getProfilePic() != null) {
                StorageReference riversRef = storage.getReferenceFromUrl(selectedUser.getProfilePic());
                try {
                    final File localFile = File.createTempFile("images", "jpg");
                    riversRef.getFile(localFile)
                            .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    // Successfully downloaded data to local file
                                    // ...
                                    Picasso.get().load(localFile).placeholder(R.drawable.profile).into(userImageIV);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle failed download
                            // ...
                        }
                    });
                } catch (Exception e) {
                    Log.w("Error", e.getMessage());
                }
            }

            userNameET.setText(selectedUser.getUserName());



        } else {
            Log.w("Error", "Error loading the user into change role ");
        }
    }

    public void SaveChange(View view) {
        //todo save changes to the firesStore

        if(roleSpinner.getSelectedItemPosition()>2){

        }else if (roleSpinner.getSelectedItemPosition()==2){

        }

    }
}