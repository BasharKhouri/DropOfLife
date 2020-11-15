package com.example.dropoflife;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
    EditText roleET, hospitalET;
    FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_role);
        Intent intent = getIntent();
        User selectedUser;
        if (intent.hasExtra("user")) {
            selectedUser = intent.getParcelableExtra("user");
            try {
                if (selectedUser.getRole().equals(new Roles(2))) {
                    hospitalET.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                Log.w("Error", e.getMessage());
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

            if (selectedUser.getRole() != null) {
                roleET.setText(selectedUser.getRole().getRole());
            }
            if (selectedUser.getHospital() != null) {
                hospitalET.setText(selectedUser.getHospital().getName());
            }


            roleET.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            hospitalET.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        } else {
            Log.w("Error", "Error loading the user into change role ");
        }
    }
}