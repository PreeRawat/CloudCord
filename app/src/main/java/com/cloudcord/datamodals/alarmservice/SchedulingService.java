package com.cloudcord.datamodals.alarmservice;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.cloudcord.R;
import com.cloudcord.datamodals.modals.Alarms;
import com.cloudcord.views.activities.AlarmActivity;

/**
 * Service Class to get the broadcast and send the local notifications 
 */
public class SchedulingService extends IntentService {
	public SchedulingService() {
		super("SchedulingService");
	}

	public static int NOTIFICATION_ID;

	NotificationCompat.Builder builder;
	private static final String ACTION_PLAY = "com.cloudcord.action.PLAY";

	@Override
	protected void onHandleIntent(Intent intent) {

		Bundle alert = intent.getExtras();
		Alarms alarm = alert.getParcelable("alarm");
		System.out.println(alarm.getmTitle() + " in service ");
		//alarm = new Alarms(10,"Dfgh","54","14","544","54hjk54");
		sendAlert(alarm);
		AlarmReceiver.completeWakefulIntent(intent);
	}

	private void sendAlert(Alarms alert) {

		Intent startPlayback= new Intent(getApplicationContext(), MediaPlayerService.class).setAction(ACTION_PLAY);
		startPlayback.putExtra("alert", alert);
		getApplicationContext().startService(startPlayback);

		/*Intent intent = new Intent(SchedulingService.this,
				AlarmActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
				intent, 0);

		RemoteViews notificationView = new RemoteViews(getPackageName(),
				R.layout.remote_notification);

		Intent snoozeIntent = new Intent(this, NotificationButton.class);
        Bundle bundle =new Bundle();
        bundle.putParcelable("alarm",alert);
        snoozeIntent.putExtras(bundle);
        snoozeIntent.putExtra("button","snooze");
		PendingIntent pendingSnoozeIntent = PendingIntent.getBroadcast(this, 0, snoozeIntent, 0);

        Intent stopIntent = new Intent(getApplicationContext(), NotificationButton.class);
        stopIntent.putExtra("button","stop");
		Bundle bundle1 = new Bundle();
		bundle1.putParcelable("alarm",alert);
		stopIntent.putExtras(bundle1);
        stopIntent.setAction(ACTION_STOP);
        PendingIntent pendingStopIntent = PendingIntent.getService(this, 0, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT);



		notificationView.setOnClickPendingIntent(R.id.snooze, pendingSnoozeIntent);
		notificationView.setOnClickPendingIntent(R.id.stop, pendingStopIntent);

		Notification notification = new Notification(android.R.drawable.ic_btn_speak_now, null,
				System.currentTimeMillis());
		NOTIFICATION_ID = alert.getmId();

		notification.contentView = notificationView;
		notification.contentIntent = pendingIntent;
		notification.flags |= Notification.FLAG_AUTO_CANCEL;


		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		notificationManager.notify(NOTIFICATION_ID, notification);*/


		/*if (alertType.equalsIgnoreCase("PhotoLog")) {

			Intent intent = new Intent(SchedulingService.this,
					AlarmActivity.class);
			PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
					intent, 0);

			NotificationCompat.Builder builder = new NotificationCompat.Builder(
					this);
			//builder.setSmallIcon(R.drawable.logo_eyes);

			builder.setContentIntent(pendingIntent);

			builder.setAutoCancel(true);

			builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),
					android.R.drawable.arrow_up_float));

			builder.setContentTitle("CloudCord");
			builder.setContentText("");

			NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			notificationManager.notify(NOTIFICATION_ID, builder.build());
		} else {

			Intent intent = new Intent();
			intent.setComponent(new ComponentName("com.android.settings", "android.settings.SECURITY_SETTINGS"));
			PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
					intent, 0);

			NotificationCompat.Builder builder = new NotificationCompat.Builder(
					this);
			builder.setSmallIcon(android.R.drawable.arrow_up_float);

			builder.setContentIntent(pendingIntent);

			builder.setAutoCancel(true);

			builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),
					android.R.drawable.arrow_up_float));

			builder.setContentTitle("CloudCord");
			builder.setContentText("");

			NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			notificationManager.notify(10, builder.build());
		}*/

	}

}
