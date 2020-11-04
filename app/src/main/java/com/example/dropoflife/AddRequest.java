package com.example.dropoflife;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dropoflife.Classes.BloodType;
import com.example.dropoflife.Classes.Post;
import com.example.dropoflife.Classes.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class AddRequest extends AppCompatActivity {

    private AlertDialog.Builder dialogBuilder;
    private  AlertDialog dialog;
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
        location=(EditText) findViewById(R.id.addPostLocation);
        description=(EditText) findViewById(R.id.descriptionEditText);
        phoneNumber=(EditText) findViewById(R.id.enterPhoneNumber);
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
                  post = new Post(publicher.getUid(),requestedBlood.getBloodID(),description.getText().toString(),
                          Calendar.getInstance().getTime(),location.getText().toString(),phoneNumber.getText().toString());
                  myRef.setValue(post);
              }catch (Exception e){
                  Toast.makeText(AddRequest.this, e.getMessage(), Toast.LENGTH_SHORT).show();
              }

               // myRef.setValue(post);

            }
        });
    }
}
