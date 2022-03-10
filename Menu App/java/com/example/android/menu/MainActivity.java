package com.example.android.menu;

import android.app.UiModeManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class MainActivity extends AppCompatActivity {

    private  boolean isChecked =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home,menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem checkable = menu.findItem(R.id.theme);
        checkable.setChecked(isChecked);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Toast.makeText(this, "Selected "+item.getTitle(),Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.settings:
            {
                Intent i = new Intent(this, SettingsActivity.class);
                startActivity(i);
            }
            break;
            case R.id.theme:
            {
                if(item.isChecked())
                {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                isChecked = !isChecked;
                item.setChecked(isChecked);
            }
            break;
            case R.id.share:{
                Toast.makeText(this, "Selected "+item.getTitle(),Toast.LENGTH_SHORT).show();
            }
            break;
            default:   return super.onOptionsItemSelected(item);
        }
        return true;
    }
}