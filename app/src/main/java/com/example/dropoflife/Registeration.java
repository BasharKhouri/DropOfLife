package com.example.dropoflife;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
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
import java.util.Calendar;
import java.util.Date;

/**
 * author Bashar Khouri
 */
public class Registeration extends AppCompatActivity  implements DatePickerDialog.OnDateSetListener{
    private EditText fullNameET ,emailET , passwordET , conPasswordET , birthdayET ;
    private  Date birthDate ;
    private  RadioGroup radioGroup ;
    private RadioButton radioButton ;
    private Spinner bloodSpinner;
    private FirebaseAuth mAuth ;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);

        mAuth =FirebaseAuth.getInstance().getInstance();
        radioGroup = (RadioGroup)findViewById(R.id.genderRadioButton);
        birthdayET = (EditText) findViewById(R.id.birthdayText);
        fullNameET = (EditText)findViewById(R.id.fullName);
        emailET = (EditText) findViewById(R.id.emailAddress);
        passwordET=(EditText)findViewById(R.id.password);
        conPasswordET = (EditText)findViewById(R.id.confirmPassword);
        bloodSpinner =(Spinner)findViewById(R.id.bloodTypeSpinner);
        ArrayAdapter<String> spinnerArrayAdapter  = new ArrayAdapter<String>(this , android.R.layout.simple_spinner_dropdown_item,  BloodType.bloodTypes);
        bloodSpinner.setAdapter(spinnerArrayAdapter);
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
             birthDate = new SimpleDateFormat("dd/mm/yy").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void register(View view) throws BloodType.IncorrectBloodIDException {
        final String fullName = fullNameET.getText().toString();
        if(TextUtils.isEmpty(fullName)){
            fullNameET.setError(R.string.enter_full_name+"");
            return ;
        }
        final String email = emailET.getText().toString();
        if(TextUtils.isEmpty(email)){
            emailET.setError(R.string.enter_email+"");
            return ;
        }
        final String password = passwordET.getText().toString();
        if(TextUtils.isEmpty(password)){
            passwordET.setError(R.string.enter_password+"");
            return ;
        }
        final String conPassword = conPasswordET.getText().toString();
        if(TextUtils.isEmpty(email)){
            conPasswordET.setError(R.string.enter_confirm_password+"");
            return ;
        }
        final String BirthDate = birthdayET.getText().toString();
        if(TextUtils.isEmpty(email)){
            emailET.setError(R.string.enter_BirthDate+"");
            return ;
        }
        int radioID =radioGroup.getCheckedRadioButtonId();
        radioButton=findViewById(radioID);
       final String  sex =radioButton.getText().toString();
        if(TextUtils.isEmpty(sex)){
            Toast.makeText(this, R.string.selct_sex, Toast.LENGTH_SHORT).show();
            return ;
        }
        final BloodType blood = new BloodType(bloodSpinner.getId());
             if(TextUtils.isEmpty(blood.getBloodType())){
                    Toast.makeText(this,R.string.select_blood, Toast.LENGTH_SHORT).show();
                    return;
                }

        if(conPassword.equals(password)){
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        //Need to  add UI changes now
                        Toast.makeText(getApplicationContext(), R.string.sign_up_successfully,Toast.LENGTH_SHORT).show();
                        User user = new User(mAuth.getUid(),fullName,birthDate,sex,blood);
                        myRef.setValue(user);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), R.string.sign_up_successfully,Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}