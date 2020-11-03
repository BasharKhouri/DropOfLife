package com.example.dropoflife;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.LocaleData;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dropoflife.Classes.BloodType;
import com.example.dropoflife.Classes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;

/**
 * author Bashar Khouri
 */
public class Registration extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private EditText fullNameET, emailET, passwordET, conPasswordET, birthdayET;
    private Date birthDate;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Spinner bloodSpinner;
    private FirebaseAuth mAuth;


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance().getInstance();
        radioGroup = findViewById(R.id.genderRadioButton);
        birthdayET = findViewById(R.id.birthdayText);
        fullNameET = findViewById(R.id.fullName);
        emailET = findViewById(R.id.emailAddress);
        passwordET = findViewById(R.id.password);
        conPasswordET = findViewById(R.id.confirmPassword);
        bloodSpinner = findViewById(R.id.bloodTypeSpinner);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, BloodType.bloodTypes);
        bloodSpinner.setAdapter(spinnerArrayAdapter);
        birthdayET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this, Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = dayOfMonth + "/" + month + "/" + year;
        birthdayET.setText(date);
        try {
            birthDate = new SimpleDateFormat("dd/mm/yy").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    Intent intent;

    public void register(View view) {


        if (checkMinReqForRegistry()) {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        //Need to  add UI changes now
                        Toast.makeText(getApplicationContext(), R.string.sign_up_successfully, Toast.LENGTH_SHORT).show();
                        User user = new User(mAuth.getUid(), fullName, birthDate, sex, blood);
                        myRef.push().getKey();
                        myRef.setValue(user);
                        Intent intent =new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("user", (Parcelable) user);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.sign_up_unsuccessfully, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


    String fullName, email, password, conPassword, birthDateStr, sex;
    BloodType blood;

    /**
     * @return true if the user can sign up with the current data note this process dose not validate if the email exists.
     */

    public boolean checkMinReqForRegistry() {


        boolean dataValidated = true;
        //assign the values to the Strings and to blood type;
        int radioID = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioID);
        fullName = fullNameET.getText().toString();
        email = emailET.getText().toString();
        password = passwordET.getText().toString();
        conPassword = conPasswordET.getText().toString();
        birthDateStr = birthdayET.getText().toString();
        try {
            sex = radioButton.getText().toString();
        } catch (Exception e) {
            System.out.println(e);

        }


        try {
            blood = new BloodType(bloodSpinner.getSelectedItemPosition());
        } catch (Exception e) {
            Toast.makeText(this,getString( R.string.error_during_blood_selection), Toast.LENGTH_SHORT).show();
            dataValidated = false;
        }

        if (TextUtils.isEmpty(fullName)) {
            fullNameET.setError(getString(R.string.enter_full_name_error ));
            dataValidated = false;
        }
        if (TextUtils.isEmpty(email)) {
            emailET.setError(getString(R.string.enter_email_error ));
            dataValidated = false;
        }
        if (TextUtils.isEmpty(password)) {
            passwordET.setError(getString(R.string.enter_password_error ));
            dataValidated = false;
        } else if (!(password.length() >= 8 && password.length() <= 16)) {
            passwordET.setError(getString(R.string.error_password_length_incorrect) + password.length());
            dataValidated = false;
        } else if (TextUtils.isEmpty(conPassword)) {
            conPasswordET.setError(getString(R.string.enter_confirm_password_error ));
            dataValidated = false;
        } else if (!password.equals(conPassword)) {
            conPasswordET.setError(getString(R.string.error_password_did_not_match ));
        }
        if (TextUtils.isEmpty(birthDateStr)) {
            birthdayET.setError(getString( R.string.enter_BirthDate_error));
            dataValidated = false;
        }
        if (TextUtils.isEmpty(sex)) {
            Toast.makeText(this, getString(R.string.selct_sex), Toast.LENGTH_SHORT).show();
            dataValidated = false;
        }
//Calendar.getInstance().getTime()
        int year = birthDate.getYear()+1900;
        int now = Calendar.getInstance().getTime().getYear()+1900;
        if(now-year<16){
            Toast.makeText(this, getString(R.string.error_younger_than_the_legal_age), Toast.LENGTH_SHORT).show();
            dataValidated=false;
        }
        return dataValidated;
    }





}