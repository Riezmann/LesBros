package com.example.calendardatabasefirstdraft;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NotePage extends AppCompatActivity {

    EditText noteName;
    EditText noteContent;
    EditText date_in;
    Button viewAll;
    Button add;
    ListView lv_notes;
    DatabaseHelper databaseHelper;
    ArrayAdapter<Note> noteArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_page);
        databaseHelper = new DatabaseHelper(getApplicationContext());

        //Initialize EditText

        noteContent = findViewById(R.id.noteContent);
        date_in = findViewById(R.id.date_in);
        date_in.setText(MainActivity.getSelectedDate());


        //Initialize Button
        viewAll = findViewById(R.id.viewAll);
        add = findViewById(R.id.add);

        //Initialize List View
        lv_notes = findViewById(R.id.lv_notes);


        ShowNoteOnListView(databaseHelper);



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note note = new Note(date_in.toString(), noteContent.getText().toString());
                databaseHelper.addNote(date_in.getText().toString(), noteContent.getText().toString());
                Toast.makeText(NotePage.this, "Added Note Successfully", Toast.LENGTH_SHORT).show();
                ShowNoteOnListView(databaseHelper);

            }
        });

        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //extract the task list whenever click add view
                ShowNoteOnListView(databaseHelper);
            }
        });




    }

    private void ShowNoteOnListView(DatabaseHelper databaseHelper) {
        noteArrayAdapter = new ArrayAdapter<Note>(NotePage.this, android.R.layout.simple_list_item_1, databaseHelper.getAllNoteByDate(MainActivity.getSelectedDate()));
        lv_notes.setAdapter(noteArrayAdapter);
    }
}