package com.example.dropoflife;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dropoflife.Classes.BloodType;
import com.example.dropoflife.Classes.Hospitals;
import com.example.dropoflife.Classes.Roles;
import com.example.dropoflife.Classes.User;
import com.example.dropoflife.Interface.IFirebaseHospitalLoad;
import com.example.dropoflife.ui.Admin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ChangeRole extends AppCompatActivity implements IFirebaseHospitalLoad {
    ImageView userImageIV;
    TextView userNameET;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    LinearLayout hospitalLinearLayout;
    Roles hospitalWorkerRole;
    Spinner roleSpinner;
    SearchableSpinner searchableSpinner;
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    IFirebaseHospitalLoad iFirebaseHospitalLoad;
    User selectedUser;
    List<Hospitals> hospitalsList;
    String UID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_role);

        //init values
        userNameET = (TextView) findViewById(R.id.profile_page_userName_role);
        userImageIV = (ImageView) findViewById(R.id.profile_image_role);
        hospitalLinearLayout = (LinearLayout) findViewById(R.id.select_hospital_changeRole);
        roleSpinner = (Spinner) findViewById(R.id.roleSpinner);
        searchableSpinner = (SearchableSpinner) findViewById(R.id.searchableSpinner_hospitals);
        iFirebaseHospitalLoad = this;
        //init selected user
        Intent intent = getIntent();
        if (intent.hasExtra("user")) {
            selectedUser = intent.getParcelableExtra("user");
        } else {
            Log.w("Error", "Error loading the user into change role ");
        }
            if(intent.hasExtra("UID"))
                UID = intent.getStringExtra("UID");
            selectedUser.setUserID(UID);
            //init adapter for the spinner
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, Roles.roles);
            roleSpinner.setAdapter(spinnerArrayAdapter);
            //set layout gone
            hospitalLinearLayout.setVisibility(View.INVISIBLE);
            hospitalsList = new ArrayList<Hospitals>();
            //get all the hospitals into the list
            fStore.collection("Hospitals").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    hospitalsList.clear();
                    for (QueryDocumentSnapshot tk : task.getResult()) {
                        hospitalsList.add(tk.toObject(Hospitals.class));
                    }
                    iFirebaseHospitalLoad.onLoadSuccess(hospitalsList);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    iFirebaseHospitalLoad.onLoadFail(e.getMessage());
                }
            });


            //set data
            //set username
            userNameET.setText(selectedUser.getUserName());
            //set profile pic
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



        roleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 2) {
                    hospitalLinearLayout.setVisibility(View.VISIBLE);
                } else {
                    hospitalLinearLayout.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void SaveChange(View view) {
        try {
            fStore.collection("users").document(UID).update("role", new Roles(roleSpinner.getSelectedItemPosition()));
        }
        catch (Exception e){
            Log.w("Error",e.getMessage());
        }
        if (roleSpinner.getSelectedItemPosition() == 2) {

            fStore.collection("users").document(UID).update("hospitalID", hospitalsList.get(searchableSpinner.getSelectedItemPosition()).getID());
        }

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        Log.w("userInfo", selectedUser.getUserID());
        Toast.makeText(this, "5555 " + selectedUser.getUserID(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onLoadSuccess(List<Hospitals> hospitalsList) {
        List<String> hospitalNameList = new ArrayList<>();
        for (Hospitals hospital : hospitalsList) {
            hospitalNameList.add(hospital.getName());
        }
        // create adapter and set for spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, hospitalNameList);
        searchableSpinner.setAdapter(adapter);
    }

    @Override
    public void onLoadFail(String message) {
        Log.w("error", message);
    }
}