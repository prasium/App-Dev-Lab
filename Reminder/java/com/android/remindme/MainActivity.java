package com.android.remindme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton mCreateRem;
    TextView tV;
    RecyclerView mRecyclerview;
    ArrayList<TaskModel> dataHolder = new ArrayList<TaskModel>();                                               //Array list to add reminders and display in recyclerview
    MyAdapter adapter;
    ItemTouchHelper itemTouchHelper;

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerview = findViewById(R.id.recyclerView);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mCreateRem = findViewById(R.id.create_reminder);
        tV = findViewById(R.id.empty_text);
        mCreateRem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Reminder.class);
                startActivity(i);
            }
        });

        Cursor cursor = new DBHelper(getApplicationContext()).readAllReminders();
        while (cursor.moveToNext()){
            TaskModel taskModel = new TaskModel(cursor.getString(1),
                    cursor.getString(2), cursor.getString(3));
            dataHolder.add(taskModel);
        }
        if(dataHolder.isEmpty())
        {
            tV.setText("No Tasks scheduled.");
            tV.setVisibility(View.VISIBLE);
        }
        else{
            tV.setVisibility(View.GONE);
        }
        adapter = new MyAdapter(dataHolder);
        mRecyclerview.setAdapter(adapter);
            ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(
                    0, ItemTouchHelper.RIGHT ) {

                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                   return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    //Remove swiped item from list and notify the RecyclerView
                    int position = viewHolder.getAdapterPosition();
                    DBHelper dbHelper = new DBHelper(getApplicationContext());
                    dbHelper.deleteReminder(dataHolder.get(position).time);
                    dataHolder.remove(position);
                    Toast.makeText(getApplicationContext(), "Task Deleted", Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                }};
                itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
                itemTouchHelper.attachToRecyclerView(mRecyclerview);
      }
}