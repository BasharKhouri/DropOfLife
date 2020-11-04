package com.example.dropoflife;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.dropoflife.Classes.BloodType;
import com.example.dropoflife.Classes.User;

public class AddRequest extends AppCompatActivity {

     static User user ;
    private AlertDialog.Builder dialogBuilder;
    private  AlertDialog dialog;
    private EditText location , description , phoneNumber;
    private Spinner bloodSpinner;
    private Button postButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_request);
        user = (User) getIntent().getSerializableExtra("User");
        location=(EditText) findViewById(R.id.addPostLocation);
        description=(EditText) findViewById(R.id.descriptionEditText);
        phoneNumber=(EditText) findViewById(R.id.enterPhoneNumber);
        bloodSpinner = (Spinner)findViewById(R.id.req_blood_Type);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, BloodType.bloodTypes);
        bloodSpinner.setAdapter(spinnerArrayAdapter);
        postButton=(Button)findViewById(R.id.addRequest);

    }
}
