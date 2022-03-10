package com.example.android.login;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    EditText userName, password;
    TextView attempts;
    Button loginBtn;
    int attemptRemaining = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.pass);
        loginBtn = (Button) findViewById(R.id.btn);
        attempts = (TextView) findViewById(R.id.attempts);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check Validation
                if(!validate(userName.getText().toString(), password.getText().toString())) {
                    attempts.setText("Invalid username/password " + attemptRemaining + " Attempt(s) Remaining");
                }
            }
        });

    }
    private boolean validate(String username, String password){
        if ((username.equals("prasun")) &&  (password.equals("181210036"))){
            Intent i = new Intent(this, HomeActivity.class);
            startActivity(i);
        } else{
            attemptRemaining--;
            if(attemptRemaining==0)
            {
                AlertDialog.Builder alertDialog= new AlertDialog.Builder(this);
                alertDialog.setTitle("Maximum attempts reached");
                alertDialog.setMessage("Login has been disabled.")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                })
                .show();
                attempts.setVisibility(View.GONE);
                loginBtn.setEnabled(false);
            }
            return false;
        }
        return true;
    }
}
