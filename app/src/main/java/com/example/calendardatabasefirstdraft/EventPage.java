package com.example.calendardatabasefirstdraft;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EventPage extends AppCompatActivity {

    EditText date_in;
    EditText startTime;
    EditText endTime;
    EditText eventName;
    Button viewAll;
    Button add;
    ListView lv_events;
    DatabaseHelper databaseHelper;
    Spinner spinnerEvent;
    String tagName;
    ArrayAdapter<WorkingSession> workingSessionArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_page);
        databaseHelper = new DatabaseHelper(EventPage.this);

        ArrayList<String> arrayTag = new ArrayList<>();


        //Initialize Date In
        String selectedDate = MainActivity.getSelectedDate();


        //initialize spinner
        spinnerEvent = (Spinner) findViewById(R.id.spinnerTag);

        //Creating tags
        Tag tag1 = new Tag("Studying");
        Tag tag2 = new Tag("Work Out");


        //Insert Tag to Database
        try {
            arrayTag.add("Choose Category");
            arrayTag.add(tag1.toString());
            arrayTag.add(tag2.toString());

        } catch (Exception e) {
            // Temporarily Do nothing
        }


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(EventPage.this, android.R.layout.simple_spinner_item, arrayTag);

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerEvent.setAdapter(arrayAdapter);

        //initialize edittext

        startTime = findViewById(R.id.noteName);
        endTime = findViewById(R.id.endTime);
        eventName = findViewById(R.id.noteContent);
        date_in = findViewById(R.id.date_in);
        date_in.setText(selectedDate);

        //initialize buttons
        viewAll = findViewById(R.id.viewAll);
        add = findViewById(R.id.add);

        //initialize list view
        lv_events = findViewById(R.id.lv_notes);

        //extract the customer list whenever click add view
        ShowWorkingSessionOnListView(databaseHelper);

        spinnerEvent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (((String) parent.getItemAtPosition(position)).equals("Choose Category")) {
                    Toast.makeText(EventPage.this, "Please choose the right tag!", Toast.LENGTH_SHORT).show();
                }
                tagName = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WorkingSession workingSession;
                weekCalendar weekCalendar = new weekCalendar(); //OK
                DateCalendar dateCalendar;
                int tagID;
                Date date = new Date();

                // Week properties
                int weekOrder;
                long weekID;

                //Date properties
                String day_of_week;
                long dateID;
                String format = "dd/MM/yyyy";


                Toast toast;
                SimpleDateFormat df = new SimpleDateFormat(format);
                try {
                    //Creating Week Object
                    date = df.parse(selectedDate);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    weekOrder = calendar.get(Calendar.WEEK_OF_YEAR);
                    weekID = (int) databaseHelper.addWeek(weekOrder);
                    weekCalendar = databaseHelper.getWeek(weekID);



                    //Creating Date Object
                    SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy", Locale.UK);
                    date = format1.parse(selectedDate);
                    DateFormat format2 = new SimpleDateFormat("EEE", Locale.UK);
                    day_of_week = format2.format(date);
                    dateCalendar = new DateCalendar((int) weekID, weekOrder, day_of_week, selectedDate);
                    dateID = databaseHelper.addDate(dateCalendar);


                    //get tagID
                    tagID = arrayTag.indexOf(tagName) + 1;


                    //Initialize WorkSession Objects
                    workingSession = new WorkingSession(eventName.getText().toString(), (int) weekID, (int) dateID, tagID, startTime.getText().toString(), endTime.getText().toString(), false, false);
                    long workingSessionID = databaseHelper.addWorkingSession(workingSession);
                    workingSession = new WorkingSession((int) workingSessionID, workingSession.getName(), workingSession.weekID, workingSession.dateID, workingSession.tagID, workingSession.Start_time, workingSession.End_time, false, false);
                    //Add DWT objects to database
                    databaseHelper.addDWT((int) dateID, (int) workingSessionID, tagID);
                    //Add DWS objects to database
                    databaseHelper.addDWS((int) dateID, (int) workingSessionID, workingSession.isOnTime());
                    //Add DWI objects to database
                    databaseHelper.addDWI((int) dateID, (int) workingSessionID, workingSession.isStatus());


                    Toast.makeText(getApplicationContext(), "Add Working Session Successfully!", Toast.LENGTH_SHORT).show();

                    SimpleDateFormat format3 = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.UK);
                    String startTemp = selectedDate;

                    startTemp = startTemp + " " + startTime.getText();

                    date = format3.parse(startTemp);


                    //Reminder at Start Time
                    Intent intent = new Intent(getApplicationContext(), StartTimeReminderBroadcast.class);

                    int requestCode = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);

                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);


                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                    alarmManager.set(AlarmManager.RTC_WAKEUP, date.getTime(), pendingIntent);



                    //Reminder at end Time
                    String endTemp = selectedDate;

                    endTemp = endTemp + " " + endTime.getText();

                    SimpleDateFormat outsideFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.UK);

                    Date date2 = new Date();
                    try {
                        date2 = outsideFormat.parse(endTemp);
                        Toast.makeText(getApplicationContext(), date2.toString(), Toast.LENGTH_SHORT).show();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Intent intent1 = new Intent(getApplicationContext(), EndTimeReminderBroadCast.class);

                    intent1.putExtra("ID", workingSession.getWorkingSessionID());

                    Toast.makeText(EventPage.this, "Current Working Session ID: " + workingSession.getWorkingSessionID(), Toast.LENGTH_SHORT).show();

                    int requestCode2 = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);

                    PendingIntent pendingIntent1 = PendingIntent.getBroadcast(getApplicationContext(),
                            requestCode + 1,
                            intent1,
                            PendingIntent.FLAG_ONE_SHOT);

                    AlarmManager alarmManager2 = (AlarmManager) getSystemService(ALARM_SERVICE);

                    alarmManager2.set(AlarmManager.RTC_WAKEUP, date2.getTime(), pendingIntent1);



                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //extract the customer list whenever click add view
                ShowWorkingSessionOnListView(databaseHelper);


            }

        });


        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //extract the task list whenever click add view
                ShowWorkingSessionOnListView(databaseHelper);
            }
        });


        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog(startTime);
            }
        });


        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog(endTime);
            }
        });


    }

    private void ShowWorkingSessionOnListView(DatabaseHelper databaseHelper) {
        workingSessionArrayAdapter = new ArrayAdapter<WorkingSession>(EventPage.this, android.R.layout.simple_list_item_1, databaseHelper.getAllWorkingSessionsByDate(MainActivity.getSelectedDate()));
        lv_events.setAdapter(workingSessionArrayAdapter);
    }

    private void showTimeDialog(EditText start_OR_ENDTime) {
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                start_OR_ENDTime.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };

        new TimePickerDialog(EventPage.this, timeSetListener, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), false).show();
    }


}