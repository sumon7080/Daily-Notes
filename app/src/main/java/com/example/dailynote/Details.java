package com.example.dailynote;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Details extends AppCompatActivity {

    TextView textView;
    DatabaseHelper databaseHelper;
    Model note;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textView = findViewById(R.id.details);



        Intent i = getIntent();
        Long id = i.getLongExtra("ID", 0);
        databaseHelper = new DatabaseHelper(this);
        note = databaseHelper.getNote(id);
        getSupportActionBar().setTitle(note.getName());
        textView.setText(note.getDetails());

        textView.setMovementMethod(new ScrollingMovementMethod());







        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                databaseHelper.deleteNote(note.getId());
                Toast.makeText(getApplicationContext(), "Delete Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.editNote)
        {
            //Send user to edit activity

            Toast.makeText(this, "CLicked Edit note", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Details.this, Edit.class);
            i.putExtra("ID", note.getId());
            startActivity(i);




        }


        return super.onOptionsItemSelected(item);
    }
}