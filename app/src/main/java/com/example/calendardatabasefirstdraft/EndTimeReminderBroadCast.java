package com.example.calendardatabasefirstdraft;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Date;

public class EndTimeReminderBroadCast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        MediaPlayer mediaPlayer = MediaPlayer.create(context, Settings.System.DEFAULT_NOTIFICATION_URI);
        mediaPlayer.start();


        int workingSessionID = intent.getIntExtra("ID", 0);

        Toast.makeText(context, "EndTime Working Session ID: " + workingSessionID, Toast.LENGTH_SHORT).show();

        Intent intent1 = new Intent(context, OntimeDatabaseChange.class);
        intent1.putExtra("ID", workingSessionID);

        int requestCode = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);

        PendingIntent actionIntent1 = PendingIntent.getActivity(context, requestCode, intent1, 0);

        Intent intent2 = new Intent(context, LateDatabaseChange.class);

        PendingIntent actionIntent2 = PendingIntent.getActivity(context, requestCode + 1, intent2, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "lesBros")
                .setSmallIcon(R.drawable.ic__add_alert_black_24dp)
                .setAutoCancel(true)
                .setContentTitle("Remind lesBros Group")
                .setContentText("Have you done yet")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setOnlyAlertOnce(true)
                .setColor(Color.BLUE)
                .addAction(R.mipmap.ic_launcher, "Yes, let's rock!", actionIntent1)
                .addAction(R.mipmap.ic_launcher, "No, I think I need more time :(", actionIntent2);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);

        int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);

        notificationManagerCompat.notify(m, builder.build());
    }
}
