package com.example.calendardatabasefirstdraft;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.provider.Settings;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Date;

public class StartTimeReminderBroadcast extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        MediaPlayer mediaPlayer = MediaPlayer.create(context, Settings.System.DEFAULT_NOTIFICATION_URI);
        mediaPlayer.start();


        Intent intent1 = new Intent(context.getApplicationContext(), EventPage.class);

        Intent intent2 = new Intent(context.getApplicationContext(), MainActivity.class);

        PendingIntent actionIntent1 = PendingIntent.getActivity(context, 0, intent1, 0);

        PendingIntent actionIntent2 = PendingIntent.getActivity(context, 0, intent2, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "lesBros")
                .setSmallIcon(R.drawable.ic__add_alert_black_24dp)
                .setContentTitle("Remind lesBros Group")
                .setContentText("Remember what to do?")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setColor(Color.BLUE)
                .addAction(R.mipmap.ic_launcher, "What is that? ", actionIntent1)
                .addAction(R.mipmap.ic_launcher, "Yes, I am doing ", actionIntent2);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);

        int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);

        notificationManagerCompat.notify(m, builder.build());
    }
}