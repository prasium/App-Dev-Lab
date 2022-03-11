package com.example.android.sharedpref;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText name, age, gender, email, phone;
    Button save;

    public static final String MY_PREFERENCES = "UserDetails" ;
    public static final String NAME = "nameKey";
    public static final String EMAIL = "emailKey";
    public static final String AGE = "ageKey";
    public static final String GENDER = "genderKey";
    public static final String PHONE = "phoneKey";

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name= findViewById(R.id.name);
        age = findViewById(R.id.age);
        gender = findViewById(R.id.gender);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        save = findViewById(R.id.save);

        sharedpreferences = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName  = name.getText().toString();
                String Age  = age.getText().toString();
                String Gender  = gender.getText().toString();
                String Email = email.getText().toString();
                String phn = phone.getText().toString();

                SharedPreferences.Editor editor = sharedpreferences.edit();

                editor.putString(NAME, fullName);
                editor.putString(AGE, Age);
                editor.putString(GENDER, Gender);
                editor.putString(PHONE, phn);
                editor.putString(EMAIL, Email);
                editor.apply();
                Toast.makeText(MainActivity.this,"Details Saved!",Toast.LENGTH_LONG).show();
            }
        });


        // To retrieve saved data
        String savedName = sharedpreferences.getString(NAME, "");
        String savedPhone = sharedpreferences.getString(PHONE, "");
        String savedEmail = sharedpreferences.getString(EMAIL, "");
        String savedAge = sharedpreferences.getString(AGE, "");
        String savedGender = sharedpreferences.getString(GENDER,"");
        name.setText(savedName);
        phone.setText(savedPhone);
        email.setText(savedEmail);
        age.setText(savedAge);
        gender.setText(savedGender);
    }

}