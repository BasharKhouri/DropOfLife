package com.example.dropoflife;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.dropoflife.Classes.BloodType;
import com.example.dropoflife.Classes.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class AddRequest extends AppCompatActivity {

    private EditText location , description , phoneNumber;
    private Spinner bloodSpinner;
    private Button postButton;
    private BloodType requestedBlood;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Posts");
   FirebaseUser publicher = FirebaseAuth.getInstance().getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_request);
        description=(EditText) findViewById(R.id.descriptionEditText);
        bloodSpinner = findViewById(R.id.req_blood_Type);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, BloodType.bloodTypes);
        bloodSpinner.setAdapter(spinnerArrayAdapter);
        postButton=(Button)findViewById(R.id.addRequest);
        postButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
              try {
                  requestedBlood = new BloodType(bloodSpinner.getSelectedItemPosition());

                  Post post;

                  post = new Post(MainActivity.user.getHospital(),requestedBlood.getBloodID(),description.getText().toString(),
                     new Date());
                  myRef.push().setValue(post);
                  Intent intent =new Intent(getApplicationContext(), MainActivity.class);
                  startActivity(intent);
                  finish();
              }catch (Exception e){
                  Toast.makeText(AddRequest.this, e.getMessage(), Toast.LENGTH_SHORT).show();
              }

               // myRef.setValue(post);

            }
        });
    }
}
