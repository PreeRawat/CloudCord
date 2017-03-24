package com.cloudcord.datamodals.alarmservice;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
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
	public Intent startPlayback;

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
		// TODO Auto-generated method stub

		startPlayback= new Intent(getApplicationContext(), MediaPlayerService.class);
		startPlayback.putExtra("sound", alert.getmSoundPath());
		getApplicationContext().startService(startPlayback);

		Intent intent = new Intent(SchedulingService.this,
				AlarmActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
				intent, 0);

		RemoteViews notificationView = new RemoteViews(getPackageName(),
				R.layout.remote_notification);

		Intent snoozeIntent = new Intent(this, NotificationButton.class);
		PendingIntent pendingSnoozeIntent = PendingIntent.getBroadcast(this, 0, snoozeIntent, 0);


		notificationView.setOnClickPendingIntent(R.id.snooze, pendingSnoozeIntent);
		notificationView.setOnClickPendingIntent(R.id.stop, pendingIntent);

		Notification notification = new Notification(android.R.drawable.ic_btn_speak_now, null,
				System.currentTimeMillis());
		NOTIFICATION_ID = alert.getmId();

		notification.contentView = notificationView;
		notification.contentIntent = pendingIntent;
		notification.flags |= Notification.FLAG_NO_CLEAR;

		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		notificationManager.notify(NOTIFICATION_ID, notification);

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

	public static class NotificationButton extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			
			if(intent.getStringExtra("button").equalsIgnoreCase("snooze")) {
				Log.d("TAG", "test"); // TODO: 24/3/17 set alarm after 5 mins
				restartInSometime();
			} else {
				// TODO: 24/3/17 stop the alarm
				//context.getApplicationContext().stopService();
			}
		}

		private void restartInSometime() {
		}

	}

}
