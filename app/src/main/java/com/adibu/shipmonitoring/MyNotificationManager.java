package com.adibu.shipmonitoring;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.adibu.shipmonitoring.view.ui.MainActivity;

import androidx.core.app.NotificationCompat;

public class MyNotificationManager {

    private static final String TAG = "MyNotificationMgr";
    private static final String WARNING_CHANNEL = "WARNING";

    private Context mContext;
    private NotificationManager mNotificationManager;

    public MyNotificationManager(Context mContext, NotificationManager mNotificationManager) {
        this.mContext = mContext;
        this.mNotificationManager = mNotificationManager;
        createNotificationChannel();
    }

    public void showWarningNotification(String title, String message) {

        Intent intent = new Intent(mContext, MainActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                mContext,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(mContext, WARNING_CHANNEL)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setChannelId(WARNING_CHANNEL)
                .setGroup(WARNING_CHANNEL)
                .build();

        //int id = mSessionManager.getNotificationId();
        //mNotificationManager.notify(id, notification);
        warningSummaryNotificatiton();
    }

    private void warningSummaryNotificatiton() {
        //use constant ID for notification used as group summary
        int SUMMARY_ID = 0;

        Notification summaryNotification =
                new NotificationCompat.Builder(mContext, WARNING_CHANNEL)
                        .setContentTitle("ABC")
                        //set content text to support devices running API level < 24
                        .setContentText("DEF")
                        .setSmallIcon(android.R.drawable.ic_dialog_alert)
                        //build summary info into InboxStyle template
                        .setStyle(new NotificationCompat.InboxStyle()
                                .setSummaryText("GHI"))
                        //specify which group this notification belongs to
                        .setGroup(WARNING_CHANNEL)
                        //set this notification as the summary for the group
                        .setGroupSummary(true)
                        .build();

        mNotificationManager.notify(SUMMARY_ID, summaryNotification);
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //Create channel
            NotificationChannel channel1 = new NotificationChannel(WARNING_CHANNEL, mContext.getString(R.string.warning), NotificationManager.IMPORTANCE_DEFAULT);
            channel1.enableLights(true);
            channel1.setVibrationPattern(new long[]{600, 250, 250});

            //Assign channel
            mNotificationManager.createNotificationChannel(channel1);
        }
    }


}
