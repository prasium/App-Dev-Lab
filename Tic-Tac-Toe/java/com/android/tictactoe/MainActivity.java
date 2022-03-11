package com.android.tictactoe;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button[][] buttons = new Button[3][3];
    boolean player1Turn = true;
    int roundCount=0;
    int player1Points;
    int player2Points;
    TextView titleBoard;
    String player1, player2;
    TextView textViewPlayer1;
    TextView textViewPlayer2;
    TextView turn;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        player1 = intent.getStringExtra("player1");
        player2 = intent.getStringExtra("player2");

        titleBoard = findViewById(R.id.titleBoard);
        textViewPlayer1 = findViewById(R.id.text_view_p1);
        textViewPlayer2 = findViewById(R.id.text_view_p2);
        turn = findViewById(R.id.playerTurn);

        turn.setText(player1+"'s Turn");

        textViewPlayer1.setText(player1+": 0");
        textViewPlayer2.setText(player2+": 0");
        titleBoard.setText(player1+" V/S "+player2);
        builder = new AlertDialog.Builder(this);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }
        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(),"Scores Reset!", Toast.LENGTH_SHORT).show();
                resetBoard();
                textViewPlayer1.setText(player1+": 0");
                textViewPlayer2.setText(player2+": 0");
                player1Points=0;
                player2Points=0;
            }
        });
    }
    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }
        if (player1Turn) {
            turn.setText(player2+"'s Turn");
           ((Button) v).setText("X");
        } else {
            turn.setText(player1+"'s Turn");
            ((Button) v).setText("O");
        }
        roundCount++;
        if (checkForWin()) {
            if (player1Turn) {
                player1Wins();
            } else {
                player2Wins();
            }
        } else if (roundCount == 9) {
            draw();
        } else {
            player1Turn = !player1Turn;
        }
    }
    private boolean checkForWin() {
        String[][] field = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }
        for (int i = 0; i < 3; i++) { // For Row
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }
        for (int i = 0; i < 3; i++) { // For Column
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }
        if (field[0][0].equals(field[1][1])  // For Diagonal
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }
        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }
        return false;
    }


    private void player1Wins() {
        builder.setTitle(player1+" wins!");
        builder.setCancelable(false);
        builder.setPositiveButton("New Game", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                resetBoard();
                return ;
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        player1Points++;
        updatePointsText();
    }
    private void player2Wins() {
        builder.setTitle(player2+" wins!");
        builder.setCancelable(false);
        builder.setPositiveButton("New Game", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                resetBoard();
                return ;
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        player2Points++;
        updatePointsText();
    }
    private void draw() {
        builder.setTitle("It's a Draw!");
        builder.setCancelable(false);
        builder.setPositiveButton("New Game", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                resetBoard();
                return ;
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();

    }
    private void updatePointsText() {
        textViewPlayer1.setText(player1+": " + player1Points);
        textViewPlayer2.setText(player2+": " + player2Points);
    }
    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        turn.setText(player1+"'s Turn");
        roundCount = 0;
        player1Turn = true;
    }

    public void goHome(View view) {
        Intent i = new Intent(this, StartScreen.class);
        finish();
        startActivity(i);
    }
}