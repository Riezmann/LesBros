package com.example.calendardatabasefirstdraft;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class dayGraph extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_graph);

        DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());

        List<WorkingSession> workingSessionList;
        List<WorkingSession> workingSessions;
        List<WorkingSession> workingSessions1;

        workingSessionList = databaseHelper.getAllWorkingSessionsByDate(MainActivity.getSelectedDate());
        workingSessions = databaseHelper.getAllWorkSessionsByDateAndIsOnTime(1, MainActivity.getSelectedDate());
        workingSessions1 = databaseHelper.getAllWorkSessionsByDateAndStatus(1, MainActivity.getSelectedDate());

        PieChart pieChart = findViewById(R.id.pieChart);

        ArrayList<PieEntry> dayData = new ArrayList<>();

        int totalWorkSession = workingSessionList.size();
        int completedTask = workingSessions1.size();
        int onTimeTask = workingSessions.size();
        int notOnTimeTask = completedTask - onTimeTask;
        int notCompletedTasks = totalWorkSession - completedTask;

        if (onTimeTask!= 0) {
            dayData.add(new PieEntry(onTimeTask, "On Time"));
        }
        if (notOnTimeTask!= 0) {
            dayData.add(new PieEntry(notOnTimeTask, "Not On Time"));
        }
        if (notCompletedTasks!= 0) {
            dayData.add(new PieEntry(notCompletedTasks, "Not Completed"));
        }


        PieDataSet pieDataSet = new PieDataSet(dayData, "Productivity");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(12f);

        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Yourself today");
        pieChart.animate();


    }
}