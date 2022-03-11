package com.android.remindme;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private static String dbName = "Reminder";

    public DBHelper(@Nullable Context context) {
        super(context, dbName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    // Insert data
        String query = "CREATE TABLE tbl_rem(id integer primary key autoincrement," +
                "title text, date text, time text)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    String query = "DROP TABLE IF EXISTS tbl_rem";
    // query to chek if table with same name exist or not
    db.execSQL(query);
    onCreate(db);
    }

    public String addReminder(String title, String date, String time)
    {
        SQLiteDatabase database = this.getReadableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);                                                          //Inserts data into sqllite database
        contentValues.put("date", date);
        contentValues.put("time", time);

        float result = database.insert("tbl_rem", null, contentValues);    //returns -1 if data insert fails into database

        if (result == -1) {
            return "Failed";
        } else {
            return "Successfully inserted";
        }
    }

    public Cursor readAllReminders()
    {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "select * from tbl_rem order by id desc";                               //Sql query to  retrieve  data from the database
        Cursor cursor = database.rawQuery(query, null);
        return cursor;
    }

   public void deleteReminder(String get_ID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Delete from tbl_rem where time='"+get_ID+"'";
        db.execSQL(query);
    }


}
