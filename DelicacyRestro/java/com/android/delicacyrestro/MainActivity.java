package com.android.delicacyrestro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ImageView img;
    Spinner spinner;
    Button btn,inc,dec;
    TextView qty;
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = findViewById(R.id.menu_spinner);
        img = findViewById(R.id.food_img);
        btn = findViewById(R.id.order_btn);
        inc = findViewById(R.id.increase);
        dec = findViewById(R.id.decrease);
        qty = findViewById(R.id.count_qty);

        qty.setText(""+count);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.menu_array, R.layout.spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Order.class);
                startActivity(i);
                finish();
            }
        });

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position==0)
        {
            img.setVisibility(View.GONE);
            btn.setEnabled(false);
            inc.setEnabled(false);
            dec.setEnabled(false);
            return;
        }

        btn.setEnabled(true);
        inc.setEnabled(true);
        dec.setEnabled(true);

        inc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                qty.setText(""+count);
            }
        });

        dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count==0)
                {
                    Toast.makeText(getApplicationContext(), "Quantity Must be positive", Toast.LENGTH_SHORT).show();
                    return ;
                }
                count--;
                qty.setText(""+count);
            }
        });
        img.setVisibility(View.VISIBLE);
        String item = parent.getItemAtPosition(position).toString();
        String imgId = item.toLowerCase(Locale.ROOT).replace(" ","_");
        int resID = getResources().getIdentifier(imgId,"drawable",getPackageName());
        img.setImageResource(resID);
        Toast.makeText(this,item+" is selected.",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}