package com.example.calendardatabasefirstdraft;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class LateDatabaseChange extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        //Change Database with everything we have
        //What is going on here?
        //We did it
        Toast.makeText(this, "Try to do it on time! Good Luck!", Toast.LENGTH_SHORT).show();
        DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());

        WorkingSession workingSession = databaseHelper.getWorkingSession(getIntent().getIntExtra("ID", 0));

        workingSession.setStatus(true);

        databaseHelper.updateWorkingSession(workingSession);
        databaseHelper.updateDWS(workingSession.getWorkingSessionID(), workingSession.isOnTime());

        finish();
    }
}
