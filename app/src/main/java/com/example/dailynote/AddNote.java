package com.example.dailynote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class AddNote extends AppCompatActivity {



    Toolbar toolbar;

    EditText edtName, edtAddress;
    Button addData;


    Calendar calendar;
    String date;
    String time;




    DatabaseHelper databaseHelper;
    Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        getSupportActionBar().setTitle("New Note");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databaseHelper = new DatabaseHelper(this);



        calendar = Calendar.getInstance();
        date = calendar.get(Calendar.YEAR) + "/"+(calendar.get(Calendar.MONTH)+1)+ "/" +calendar.get(Calendar.DAY_OF_MONTH);
        time = pad(calendar.get(Calendar.HOUR))+ "."+ pad(calendar.get(Calendar.MINUTE));



        Log.d("calender", "Date and Time: " +date+ " and " +time);



        edtName = findViewById(R.id.noteTitle);
        edtAddress= findViewById(R.id.noteDetails);

        edtName.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {



            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if(s.length() != 0)
                {
                    getSupportActionBar().setTitle(s);


                }

            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });



        model = (Model) getIntent().getSerializableExtra("key");
        if (model != null){

            addData.setText("Update Data");
            edtName.setText(model.getName());

            edtAddress.setText(model.getDetails());



        }



    }

    private String pad(int i) {
        if(i<10)
            return  "0"+i;
        return String.valueOf(i);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu, menu);
        return true;
    }

    /*

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.save) {

            if (edtName.getText().length() != 0) {

                Model model = new Model(edtName.getText().toString(), edtAddress.getText().toString(), date, time);
                DatabaseHelper databaseHelper = new DatabaseHelper(this);

                long id = databaseHelper.addNote(model);
                Model check = databaseHelper.getNote(id);
                Log.d("inserted", "Note: " + id + "-> Title " + check.getName() + "Date: " + check.getDate());
                onBackPressed();

                Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show();


            } else {

                edtName.setError("Title can not be blanked");
            }
        }
        else if(item.getItemId() == R.id.delete)
        {
            Toast.makeText(this, "Delete button clicked", Toast.LENGTH_SHORT).show();

        }

        return super.onOptionsItemSelected(item);
    }

     */



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.save)
        {
            //For insert Data

            Model model = new Model(edtName.getText().toString(), edtAddress.getText().toString(), date, time);
            long status = databaseHelper.addNote(model);
            Toast.makeText(this, "save button clicked", Toast.LENGTH_SHORT).show();
            goToMain();


        }
        if(item.getItemId() == R.id.delete)
        {

            Toast.makeText(this, "Delete button clicked", Toast.LENGTH_SHORT).show();

        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onBackPressed()
    {

        super.onBackPressed();
    }

    void goToMain()
    {
        Intent i = new Intent(AddNote.this, MainActivity.class);
        startActivity(i);


    }
}