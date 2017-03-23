package com.cloudcord.datamodals.alarmservice;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

/**
 * Service Class to get the broadcast and send the local notifications 
 */
public class SampleSchedulingService extends IntentService {
	public SampleSchedulingService() {
		super("SchedulingService");
	}

	public static final int NOTIFICATION_ID = 1;

	NotificationCompat.Builder builder;

	@Override
	protected void onHandleIntent(Intent intent) {

		String alertType = intent.getStringExtra("alertType");
		System.out.println(alertType + " in service ");
		sendAlert(alertType);
		SampleAlarmReceiver.completeWakefulIntent(intent);
	}

	private void sendAlert(String alertType) {
		// TODO Auto-generated method stub

		if (alertType.equalsIgnoreCase("PhotoLog")) {

			Intent intent = new Intent(SampleSchedulingService.this,
					MainActivity.class);
			PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
					intent, 0);

			NotificationCompat.Builder builder = new NotificationCompat.Builder(
					this);
			builder.setSmallIcon(R.drawable.logo_eyes);

			builder.setContentIntent(pendingIntent);

			builder.setAutoCancel(true);

			builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),
					R.drawable.ic_launcher));

			builder.setContentTitle("CatchLook");
			builder.setContentText(getString(R.string.newSnapshot));

			NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			notificationManager.notify(NOTIFICATION_ID, builder.build());
		} else {

			Intent intent = new Intent();
			intent.setComponent(new ComponentName("com.android.settings", "android.settings.SECURITY_SETTINGS"));
			PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
					intent, 0);

			NotificationCompat.Builder builder = new NotificationCompat.Builder(
					this);
			builder.setSmallIcon(R.drawable.logo_eyes);

			builder.setContentIntent(pendingIntent);

			builder.setAutoCancel(true);

			builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),
					R.drawable.ic_launcher));

			builder.setContentTitle("CatchLook");
			builder.setContentText(getString(R.string.recommendText));

			NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			notificationManager.notify(10, builder.build());
		}
	}

}
