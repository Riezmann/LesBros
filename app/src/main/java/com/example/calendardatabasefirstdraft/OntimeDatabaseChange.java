package com.example.calendardatabasefirstdraft;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class OntimeDatabaseChange extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, "This is created with " + getIntent().getIntExtra("ID",0), Toast.LENGTH_SHORT).show();

        //Change Database with everything we have
        //What is going on here?
        //We did it

        DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());

        WorkingSession workingSession = databaseHelper.getWorkingSession(getIntent().getIntExtra("ID", 0));

        workingSession.setStatus(true);
        workingSession.setOnTime(true);

        databaseHelper.updateWorkingSession(workingSession);
        databaseHelper.updateDWI(workingSession.getWorkingSessionID(), workingSession.isOnTime());

        finish();
    }

}