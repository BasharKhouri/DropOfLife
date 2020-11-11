package com.example.dropoflife;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class AddHospital extends AppCompatActivity {

    EditText hospitalNameET , hospitalPhoneET , hospitalAddress;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hospital);


    }
}