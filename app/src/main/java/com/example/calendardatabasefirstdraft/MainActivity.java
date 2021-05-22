package com.example.calendardatabasefirstdraft;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static String selectedDate;
    DatabaseHelper databaseHelper;
    Button eventButton;
    Button noteButton;
    Button dayGraph;
    Button weekGraph;
    CalendarView calendarView;

    public static String getSelectedDate() {
        return selectedDate;
    }

    public static void setSelectedDate(String selectedDate) {
        MainActivity.selectedDate = selectedDate;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();
        
        Tag tag1 = new Tag("Studying");
        Tag tag2 = new Tag("Work Out");



        databaseHelper = new DatabaseHelper(MainActivity.this);

        databaseHelper.addTag(tag1.toString());
        databaseHelper.addTag(tag2.toString());

        eventButton = findViewById(R.id.eventButton);
        noteButton = findViewById(R.id.noteButton);
        dayGraph = findViewById(R.id.dayGraph);
        weekGraph = findViewById(R.id.weekGraph);

        calendarView = findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                setSelectedDate(dayOfMonth + "/" + (month+1) + "/" + year);
            }
        });

        eventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), EventPage.class));
            }
        });

        noteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NotePage.class));
            }
        });

        dayGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), dayGraph.class));
            }
        });

        weekGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), weekGraph.class));
            }
        });

    }

    private void createNotificationChannel() {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ) {
            CharSequence name = "LesBrosReminderChannel";
            String description = "Channel for LesBros Reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("lesBros", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
    }


}