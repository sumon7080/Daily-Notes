package com.example.dailynote;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.Display;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {


    Context context;

    SQLiteDatabase sqLiteDatabase;

    public static final String DATABASE_NAME = "student.db";
    public static final int DATABASE_VERSION = 1;

    public static final String STUDENT_TABLE = "student_table";

    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_ADDRESS = "details";
    public static final String COL_DATE = "date";
    public static final String COL_TIME = "time";

    public static final String CREATE_TABLE = "create table " +STUDENT_TABLE+ "(" +COL_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_NAME+ " TEXT, " +
            COL_ADDRESS+ " TEXT, " +
            COL_DATE+ "TEXT, " +
            COL_TIME+ "TEXT " + ")";


    public DatabaseHelper(@Nullable Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if exists " +STUDENT_TABLE);
        this.onCreate(db);
    }

    public void open(){

        sqLiteDatabase = this.getWritableDatabase();
    }

    public void close()
    {
        this.close();
    }




    public Model getNote(long id)
    {
        //select *from database table where id =1


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(STUDENT_TABLE, new String[]{COL_ID, COL_NAME, COL_ADDRESS,COL_NAME, COL_NAME},
                COL_ID+ "=?", new String[]{String.valueOf(id)}, null, null, null);
        if(cursor != null)
            cursor.moveToFirst();
        return new Model(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                cursor.getString(3), cursor.getString(4));



    }

    public List<Model> getNotes()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Model> allNotes = new ArrayList<>();

        String query = "SELECT * FROM "+STUDENT_TABLE+" ORDER BY "+ COL_ID+" DESC";

        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst())
        {
           do{
               Model note = new Model();
               note.setId(cursor.getLong(0));
               note.setName(cursor.getString(1));
               note.setDetails(cursor.getString(2));
               note.setDate(cursor.getString(3));
               note.setTime(cursor.getString(4));


               allNotes.add(note);


           }
           while (cursor.moveToNext());
        }
        return allNotes;



    }





    public long addNote(Model model){

        this.open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_NAME, model.getName());
        contentValues.put(DatabaseHelper.COL_ADDRESS, model.getDetails());

        long insertedRow = sqLiteDatabase.insert(DatabaseHelper.STUDENT_TABLE, null, contentValues);
        Log.d("Insert", "ID->" +insertedRow);


        return insertedRow;
    }

    public long updateNote(Model model){
        this.open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME, model.getName());
        contentValues.put(COL_ADDRESS, model.getDetails());


        long updatedRow = sqLiteDatabase.update(STUDENT_TABLE, contentValues,COL_ID+ "=?", new String[]{String.valueOf(model.getId())});
        Log.d("Update", "ID->" +updatedRow);


        return updatedRow;
    }

    public int editNote(Model model)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_NAME, model.getName());
        contentValues.put(DatabaseHelper.COL_ADDRESS, model.getDetails());

        return db.update(STUDENT_TABLE, contentValues, COL_ID+"=?", new String[]{String.valueOf(model.getId())});




    }
    public void deleteNote(long id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(STUDENT_TABLE, COL_ID+"=?", new String[]{String.valueOf(id)});
        db.close();


    }





}
