package com.example.dropoflife;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.dropoflife.Classes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Registeration extends AppCompatActivity  implements DatePickerDialog.OnDateSetListener{
    EditText fullNameET ,emailET , passwordET , conPasswordET , birthdayET ;
    Date birthDate ;
    RadioGroup radioGroup ;
    RadioButton radioButton ;

    private FirebaseAuth mAuth ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);

        mAuth =FirebaseAuth.getInstance().getInstance();

        radioGroup = (RadioGroup)findViewById(R.id.genderRadioButton);

        birthdayET = (EditText) findViewById(R.id.birthdayText);
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
        final String fullName = fullNameET.getText().toString();

        boolean emptyFieldsBool = false;
        if(TextUtils.isEmpty(fullName)){
            fullNameET.setError("full name is REQUIRED.");
            emptyFieldsBool=true;
            return ;
        }
        final String email = emailET.getText().toString();
        if(TextUtils.isEmpty(email)){
            emailET.setError("full email is REQUIRED.");
            emptyFieldsBool=true;
            return ;
        }
        final String password = passwordET.getText().toString();
        if(TextUtils.isEmpty(password)){
            passwordET.setError("full email is REQUIRED.");
            emptyFieldsBool=true;
            return ;
        }
        final String conPassword = conPasswordET.getText().toString();
        if(TextUtils.isEmpty(email)){
            conPasswordET.setError("full Confirm  password is REQUIRED.");
            emptyFieldsBool=true;
            return ;
        }
        final String BirthDate = birthdayET.getText().toString();
        if(TextUtils.isEmpty(email)){
            emailET.setError("full email is REQUIRED.");
            emptyFieldsBool=true;
            return ;
        }
        int radioID =radioGroup.getCheckedRadioButtonId();
        radioButton=findViewById(radioID);
       final String  sex =radioButton.getText().toString();
        if(TextUtils.isEmpty(sex)){
            Toast.makeText(this, "sex is required", Toast.LENGTH_SHORT).show();
            emptyFieldsBool=true;
            return ;
        }

        if(!emptyFieldsBool){
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        //Need to  add UI changes now
                        Toast.makeText(getApplicationContext(), R.string.sign_up_successfully,Toast.LENGTH_SHORT).show();
                        User user = new User(mAuth.getUid(),fullName,birthDate,sex);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), R.string.sign_up_successfully,Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}