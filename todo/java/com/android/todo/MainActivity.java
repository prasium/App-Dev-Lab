package com.android.todo;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "Main Activity";


    FloatingActionButton floatingActionButton;
    ArrayList<Model> list;
    HashMap<String,String> listMap;
    RecyclerView rvList;
   ListAdapter adapter;
    TextView header;
    File dir;
    File myFile;
    FileOutputStream fos;
    FileInputStream fis;
    ObjectOutputStream oos;
    ObjectInputStream ois;

    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actionBar = getActionBar();

        header = findViewById(R.id.header);
        floatingActionButton = findViewById(R.id.add_task);
        listMap = new HashMap<>();
        list = new ArrayList<>();
        rvList = findViewById(R.id.rvList);

        floatingActionButton.setOnClickListener(v -> showBottomSheetDialog());

        dir = ContextCompat.getDataDir(MainActivity.this);
        myFile = new File(dir,"file.txt");

        try {
            myFile.createNewFile();
            fis = new FileInputStream(myFile);
            Log.d(TAG, String.valueOf(fis.available()));
           while(true) {
               Log.d(TAG, String.valueOf(fis.available()));
               ois = new ObjectInputStream(fis);
               Model m = (Model) ois.readObject();
               if(m==null)
                   break;
               list.add(m);
               Log.d(TAG, m.toString());
               listMap.put(m.title, m.description);
           }
           ois.close();
           fis.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        Log.d(TAG,"creating adapter");
        adapter = new ListAdapter(list, this);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        Log.d(TAG,"setting adapter");
        rvList.setAdapter(adapter);
        Log.d(TAG,"adapter set");
 }

    private void showBottomSheetDialog() {
        EditText etTask;
        EditText etDetail;
        Button btnAdd;
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.new_task);
        btnAdd = bottomSheetDialog.findViewById(R.id.btnAdd);
        etTask = bottomSheetDialog.findViewById(R.id.etTask);
        etDetail = bottomSheetDialog.findViewById(R.id.etDetail);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    add(etTask, etDetail);
                    bottomSheetDialog.dismiss();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        bottomSheetDialog.show();
    }


    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {

        getMenuInflater().inflate(R.menu.action_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // methods to control the operations that will
    // happen when user clicks on the action buttons
    @Override
    public boolean onOptionsItemSelected( @NonNull MenuItem item ) {

        if (item.getItemId() == R.id.action_delete) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Clear all tasks?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    clearAll(getCurrentFocus());
                }
            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    return ;
                }
            });
            AlertDialog alert = builder.create();
            alert.show();

        }
        return super.onOptionsItemSelected(item);
    }

    public boolean add (EditText etTask, EditText etDetail) throws FileNotFoundException {
        fos=new FileOutputStream(myFile,true);
        String task = etTask.getText().toString();
        String taskDetail = etDetail.getText().toString();
        Model model = new Model(task,taskDetail);
        if(task.length()==0){
            Toast.makeText(this, "Empty title", Toast.LENGTH_SHORT).show();
            return false;
        }
        list.add(model);
        listMap.put(model.title,model.description);
        try{
            oos = new ObjectOutputStream(fos);
            oos.writeObject(model);
            oos.flush();
            Log.d(TAG,"writing to file");
            oos.close();
        }catch (IOException ioe){
            Toast.makeText(this, "Cannot Write", Toast.LENGTH_SHORT).show();
        }
        etTask.setText("");
        etDetail.setText("");
        Log.d(TAG,"data added: "+list.get(list.size()-1));
        adapter.notifyDataSetChanged();

        return true;
    }

    public void clearAll(View view){
        myFile.delete();
        list.clear();
        listMap.clear();
        Toast.makeText(this, "All Tasks deleted!!", Toast.LENGTH_SHORT).show();
        adapter.notifyDataSetChanged();
    }

    public void deleteWithId(int id){
        Model t = list.get(id);
        deleteFromFile(t);
    }

      void deleteFromFile(Model t){
        listMap.remove(t.title);
        try{
            File tFile = new File(dir,"temp.txt");
            FileOutputStream fos2 = new FileOutputStream(tFile,true);
            FileInputStream fis2 = new FileInputStream(myFile);
            ObjectInputStream ois2 = null;
            ObjectOutputStream oos2 = new ObjectOutputStream(fos2);
            while(fis2.available()!=0) {
                ois2 = new ObjectInputStream(fis2);
                Model m = (Model) ois2.readObject();
                if(m==t)
                    continue;
                oos2.writeObject(m);
                oos2.flush();
            }
            ois2.close();
            fis2.close();
            myFile.delete();
            tFile.renameTo(myFile);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

      }


}



