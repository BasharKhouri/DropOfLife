package com.example.dropoflife;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Registeration extends AppCompatActivity  implements DatePickerDialog.OnDateSetListener{
    EditText fullNameET ,emailET , passwordET , conPasswordET , birthdayET ;
    Date birthDate ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);
        birthdayET = findViewById(R.id.birthdayText);
        fullNameET = (EditText)findViewById(R.id.fullNameTxt);
        emailET = (EditText) findViewById(R.id.emailAddress);
        passwordET=(EditText)findViewById(R.id.password);
        conPasswordET = (EditText)findViewById(R.id.confirmPassword);

        birthdayET.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }
    private void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,this, Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = dayOfMonth+"/"+month+"/"+year;
        birthdayET.setText(date);
        try {
            Date birthDate = new SimpleDateFormat("dd/mm/yy").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void register(View view) {

    }
}