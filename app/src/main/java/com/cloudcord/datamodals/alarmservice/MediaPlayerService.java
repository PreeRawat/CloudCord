package com.cloudcord.datamodals.alarmservice;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by root on 24/3/17.
 */

public class MediaPlayerService extends Service implements MediaPlayer.OnPreparedListener {

    private static final String ACTION_PLAY = "com.example.action.PLAY";
        MediaPlayer mMediaPlayer = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        System.out.println("preparing media");

        if (intent.getAction().equals(ACTION_PLAY)) {
            System.out.println("preparing media");
            mMediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.parse(intent.getStringExtra("sound")));
            mMediaPlayer.setOnPreparedListener(this);
        } else {
            stopSelf();
        }
        return START_STICKY;
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

