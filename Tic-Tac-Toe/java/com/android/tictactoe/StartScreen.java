package com.android.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class StartScreen extends AppCompatActivity {
    EditText player1;
    EditText player2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        player1 = findViewById(R.id.player1);
        player2 = findViewById(R.id.player2);
    }

    public void pressedPlay(View view) {
        if (player1.getText().length() == 0 || player2.getText().length() == 0) {
            Toast.makeText(this, "Name can't be empty", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("player1", player1.getText().toString());
            intent.putExtra("player2", player2.getText().toString());
            finish();
            startActivity(intent);
        }
    }
}