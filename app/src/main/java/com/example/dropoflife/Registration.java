package com.example.dropoflife;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.LocaleData;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
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
 * author Bashar Khouri,Hassan wael ,Bashar Nimri
 */
public class Registration extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private EditText fullNameET, emailET, passwordET, conPasswordET, birthdayET;
    private Date birthDate;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Spinner bloodSpinner;

    private FirebaseAuth mAuth ;
    ArrayAdapter<String> spinnerArrayAdapter;


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth =FirebaseAuth.getInstance().getInstance();
        radioGroup = (RadioGroup)findViewById(R.id.genderRadioButton);
        birthdayET = (EditText) findViewById(R.id.birthdayText);
        fullNameET = (EditText)findViewById(R.id.fullName);
        emailET = (EditText) findViewById(R.id.emailAddress);
        passwordET=(EditText)findViewById(R.id.password);
        conPasswordET = (EditText)findViewById(R.id.confirmPassword);
        bloodSpinner =(Spinner)findViewById(R.id.bloodTypeSpinner);

        spinnerArrayAdapter = new ArrayAdapter<String>(this , android.R.layout.simple_spinner_item,  BloodType.bloodTypes);

        bloodSpinner.setAdapter(spinnerArrayAdapter);


        birthdayET.setOnClickListener(new View.OnClickListener(){

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
                        myRef.setValue(user);

                        startActivity(new Intent(getApplicationContext(),Login.class));
                       finish();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), R.string.sign_up_unsuccessfully,Toast.LENGTH_SHORT).show();
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
        Date now = new Date(); //now =  new SimpleDateFormat("dd/mm/yy").parse(date);

        try {
            blood = new BloodType(bloodSpinner.getId());
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage()+"current ID "+bloodSpinner.getId(), Toast.LENGTH_SHORT).show();
            dataValidated = false;
        }

        if (TextUtils.isEmpty(fullName)) {
            fullNameET.setError((R.string.enter_full_name_error)+"");
            dataValidated = false;
        }
        if (TextUtils.isEmpty(email)) {
            emailET.setError(R.string.enter_email_error + "");
            dataValidated = false;
        }
        if (TextUtils.isEmpty(password)) {
            passwordET.setError(R.string.enter_password_error + " ");
            dataValidated = false;
        } else if (password.length() >= 8 && password.length() <= 16) {
            passwordET.setError(R.string.error_password_length_incorrect + "");
        } else if (TextUtils.isEmpty(conPassword)) {
            conPasswordET.setError(R.string.enter_confirm_password_error + "");
            dataValidated = false;
        } else if (!password.equals(conPassword)) {
            conPasswordET.setError(R.string.error_password_did_not_match + "");
        }
        if (TextUtils.isEmpty(birthDateStr)) {
            birthdayET.setError(R.string.enter_BirthDate_error + "");
            dataValidated = false;
        }
        if (TextUtils.isEmpty(sex)) {
            Toast.makeText(this, R.string.selct_sex, Toast.LENGTH_SHORT).show();
            dataValidated = false;
        }

        return dataValidated;


    }
}