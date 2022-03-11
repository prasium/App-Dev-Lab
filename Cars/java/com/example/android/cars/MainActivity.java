package com.example.android.cars;

import static com.example.android.cars.data.cars;
import static com.example.android.cars.data.imgUrls;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.list_item_view, cars);

        ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getApplicationContext(), "Launching " + cars[position], Toast.LENGTH_SHORT).show();
        AlertDialog.Builder alertdialog = new AlertDialog.Builder(parent.getContext());
        LayoutInflater inflaterr = (LayoutInflater) parent.getContext().getSystemService(parent.getContext().LAYOUT_INFLATER_SERVICE);
        View viewtemplelayout = inflaterr.inflate(R.layout.image_view, null);
        String imageUri = imgUrls[position];

        ImageView i = (ImageView) viewtemplelayout.findViewById(R.id.img);//and set image to image view
        Picasso.with(parent.getContext()).load(imageUri).resize(500, 500).into(i);

        alertdialog.setView(viewtemplelayout);//add your view to alert dilaog
        alertdialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertdialog.show();
    }


}