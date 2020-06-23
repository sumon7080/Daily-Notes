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

public class Edit extends AppCompatActivity {

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
        setContentView(R.layout.activity_edit);

        Intent i = getIntent();
        Long id = i.getLongExtra("ID", 0);
        databaseHelper = new DatabaseHelper(this);
        model = databaseHelper.getNote(id);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(model.getName());







        calendar = Calendar.getInstance();
        date = calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH);
        time = pad(calendar.get(Calendar.HOUR)) + "." + pad(calendar.get(Calendar.MINUTE));


        Log.d("calender", "Date and Time: " + date + " and " + time);


        edtName = findViewById(R.id.noteTitle);
        edtAddress = findViewById(R.id.noteDetails);
        edtName.setText(model.getName());
        edtAddress.setText(model.getDetails());

        edtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    getSupportActionBar().setTitle(s);


                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        model = (Model) getIntent().getSerializableExtra("key");
        if (model != null) {

            addData.setText("Update Data");
            edtName.setText(model.getName());

            edtAddress.setText(model.getDetails());


        }


    }

    private String pad(int i) {
        if (i < 10)
            return "0" + i;
        return String.valueOf(i);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu, menu);
        return true;
    }




    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.save) {
            //For insert Data

            String updateName = edtName.getText().toString();
            String updateDetails = edtAddress.getText().toString();

            Toast.makeText(this, "update name: "+updateName+ "update details: " +updateDetails, Toast.LENGTH_SHORT).show();


            Model model = new Model(updateName, updateDetails, date, time);
            long status = databaseHelper.updateNote(model);
            Toast.makeText(this, "Update Button CLicked", Toast.LENGTH_SHORT).show();
            goToMain();


        }
        if (item.getItemId() == R.id.delete) {

            databaseHelper.deleteNote(model.getId());
            Toast.makeText(getApplicationContext(), "Delete Successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));


        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

    void goToMain() {
        Intent i = new Intent(Edit.this, MainActivity.class);
        startActivity(i);


    }

}

