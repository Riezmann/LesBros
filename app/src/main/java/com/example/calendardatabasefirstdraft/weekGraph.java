package com.example.calendardatabasefirstdraft;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class weekGraph extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_graph);
        DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());

        List<DateCalendar> dateCalendars;

        Date date;

        String format = "dd/MM/yyyy";
        SimpleDateFormat df = new SimpleDateFormat(format);
        String selectedDate = MainActivity.getSelectedDate();

        int totalWorkSession = 0;
        int completedTask = 0;
        int onTimeTask = 0;
        int notOnTimeTask = 0;
        int notCompletedTasks = 0;

        try {
            date = df.parse(selectedDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int weekOrder = calendar.get(Calendar.WEEK_OF_YEAR);
            dateCalendars = databaseHelper.getAllDateByWeek(weekOrder);


            Set<String> currentDates = new HashSet<String>();

            for (DateCalendar dateCalendar : dateCalendars) {
                currentDates.add(dateCalendar.currentDate);
            }

            Toast.makeText(this, "Set Size is: " +  currentDates.size(), Toast.LENGTH_SHORT).show();

            for (String currentDate : currentDates){

                List<WorkingSession> workingSessionList = databaseHelper.getAllWorkingSessionsByDate(currentDate);
                List<WorkingSession> workingSessions = databaseHelper.getAllWorkSessionsByDateAndIsOnTime(1, currentDate);
                List<WorkingSession> workingSessions1 = databaseHelper.getAllWorkSessionsByDateAndStatus(1, currentDate);

                totalWorkSession += workingSessionList.size();
                completedTask += workingSessions1.size();
                onTimeTask += workingSessions.size();
            }


            notOnTimeTask = completedTask - onTimeTask;
            notCompletedTasks = totalWorkSession - completedTask;

            PieChart pieChart = findViewById(R.id.pieChart);

            ArrayList<PieEntry> weekData = new ArrayList<>();

            if (onTimeTask != 0) {
                weekData.add(new PieEntry(onTimeTask, "On Time"));
            }
            if (notOnTimeTask != 0) {
                weekData.add(new PieEntry(notOnTimeTask, "Not On Time"));
            }
            if (notCompletedTasks != 0) {
                weekData.add(new PieEntry(notCompletedTasks, "Not Completed"));
            }

            PieDataSet pieDataSet = new PieDataSet(weekData, "Productivity");
            pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
            pieDataSet.setValueTextColor(Color.BLACK);
            pieDataSet.setValueTextSize(12f);

            PieData pieData = new PieData(pieDataSet);

            pieChart.setData(pieData);
            pieChart.getDescription().setEnabled(false);
            pieChart.setCenterText("Yourself This Week");
            pieChart.animate();


        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}