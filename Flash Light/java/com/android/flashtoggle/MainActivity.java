package com.android.flashtoggle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    ToggleButton toggleButton;

    //For setting flash light
    CameraManager cameraManager;
    String cameraID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.img);
        toggleButton = findViewById(R.id.toggleBtn);

        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try{
            cameraID = cameraManager.getCameraIdList()[0];
        }
        catch (CameraAccessException e){
            e.printStackTrace();
        }

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    imageView.setImageResource(R.drawable.on);
                    Toast.makeText(getApplicationContext(),"Turned On",Toast.LENGTH_SHORT).show();
                    try{
                        cameraManager.setTorchMode(cameraID,true);
                    } catch (CameraAccessException e) {
                        Toast.makeText(getApplicationContext(),"Emulator doesnt have camera unit",Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
                else{
                    imageView.setImageResource(R.drawable.off);
                    Toast.makeText(getApplicationContext(),"Turned Off",Toast.LENGTH_SHORT).show();
                    try{
                        cameraManager.setTorchMode(cameraID,false);
                    } catch (CameraAccessException e) {
                        Toast.makeText(getApplicationContext(),"Emulator doesnt have camera unit",Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }
        });
    }


}