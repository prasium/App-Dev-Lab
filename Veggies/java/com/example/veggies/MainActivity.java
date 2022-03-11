package com.example.veggies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ImageView img;
    TextView titleImg;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = findViewById(R.id.img_view);
        titleImg = findViewById(R.id.title_veggie);
        // Create a Spinner object
        spinner = findViewById(R.id.veggies_spinner);
        // Create an array adapter using the string array and spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.veggies_array, R.layout.spinner_item);
        // Specify the layout to use when the list of choice appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        titleImg.setText(item);
        String imgId = item.toLowerCase(Locale.ROOT).replace(" ","_");
        int resID = getResources().getIdentifier(imgId,"drawable",getPackageName());
        img.setImageResource(resID);
        Toast.makeText(this,item+" is selected.",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
            // When nothing is selected call back
    }
}