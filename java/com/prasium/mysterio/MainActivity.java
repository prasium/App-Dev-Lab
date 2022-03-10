package com.prasium.mysterio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button greet,change,delete;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        greet = findViewById(R.id.greet);
        change = findViewById(R.id.change);
        delete = findViewById(R.id.delete);
        textView = findViewById(R.id.textView);

        greet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
                String greetText = "";
                if(timeOfDay>=0&&timeOfDay<12)
                {
                    greetText = "Morning";
                }
                else if(timeOfDay>=12&&timeOfDay<16)
                {
                    greetText= "Afternoon";
                }
                else if(timeOfDay>=16&&timeOfDay<21)
                {
                    greetText= "Evening";
                }
                else if(timeOfDay>=21&&timeOfDay<24){
                    greetText = "Night";
                }
                textView.setText("Good "+greetText);
            }
        });
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("Text Changed");
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("Hello World!");
            }
        });

    }
}