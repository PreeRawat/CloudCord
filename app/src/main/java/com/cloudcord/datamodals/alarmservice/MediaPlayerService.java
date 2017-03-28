package com.cloudcord.datamodals.alarmservice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.cloudcord.datamodals.modals.Alarms;
import com.cloudcord.views.activities.AlarmActivity;

/**
 * Created by root on 24/3/17.
 */

public class MediaPlayerService extends Service implements MediaPlayer.OnPreparedListener {

    private static final String ACTION_PLAY = "com.cloudcord.action.PLAY";
    private static final String ACTION_SNOOZE = "com.cloudcord.action.SNOOZE";
    private static final String ACTION_DISMISS = "com.cloudcord.action.DISMISS";

        MediaPlayer mMediaPlayer = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        NotificationManager notificationManager = (NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        Alarms alarm = intent.getParcelableExtra("alert");
        if (intent.getAction().equals(ACTION_PLAY)) {
            System.out.println("preparing media");
            //Alarms alarm = (Alarms)intent.getParcelableExtra("alert");
            showNotification(alarm);
            mMediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.parse(alarm.getmSoundPath()));
            mMediaPlayer.setOnPreparedListener(this);

        } else  {
            Log.d("TAG", "test start in 5 mins"); // TODO: 24/3/17 set alarm after 5 mins
            if(intent.getAction().equals(ACTION_SNOOZE))
                restartInSometime(getApplicationContext(), alarm);
            stopForeground(true);
            stopSelf(alarm.getmId());
        } /*else if (intent.getAction().equals(ACTION_DISMISS)){
            stopForeground(true);
            stopSelf(alarm.getmId());
        }*/
        return START_STICKY;
    }
    private void restartInSometime(Context context, Alarms alarms) {
        AlarmReceiver alarmReceiver = new AlarmReceiver();

        // alarmReceiver.setAlarm(context, alarms);
    }

    private void showNotification(Alarms alarm) {
        Intent notificationIntent = new Intent(this, AlarmActivity.class);
        //notificationIntent.setAction(Constants.ACTION.MAIN_ACTION);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        Intent snoozeIntent = new Intent(this, MediaPlayerService.class);
        snoozeIntent.putExtra("alert",alarm);
        snoozeIntent.setAction(ACTION_SNOOZE);
        PendingIntent psnoozeIntent = PendingIntent.getService(this, 0,
                snoozeIntent, 0);

        Intent stopIntent = new Intent(this, MediaPlayerService.class);
        stopIntent.putExtra("alert",alarm);
        stopIntent.setAction(ACTION_DISMISS);
        PendingIntent pdismissIntent = PendingIntent.getService(this, 0,
                stopIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("TutorialsFace Music Player")
                .setTicker("TutorialsFace Music Player")
                .setContentText("My song")
                .setSmallIcon(android.R.drawable.ic_btn_speak_now)
                //.setLargeIcon(android.R.drawable.sym_def_app_icon)
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .addAction(android.R.drawable.ic_media_previous, "Snooze",
                        psnoozeIntent)
                .addAction(android.R.drawable.ic_media_play, "Dismiss",
                        pdismissIntent).build();
        System.out.println("id "+alarm.getmId());
        startForeground(alarm.getmId(),notification);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /** Called when MediaPlayer is ready */
    public void onPrepared(MediaPlayer player) {
            player.start();
        }
}

