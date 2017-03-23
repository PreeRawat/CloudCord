package com.cloudcord.datamodals.alarmservice;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import java.util.Calendar;

/**
 * Broadcast Receiver class to send password change reminders and to send
 * notifications on a new snapshot available instance.
 */
public class SampleAlarmReceiver extends WakefulBroadcastReceiver {

	private AlarmManager alarmMgrPassword, alarmMgrPhotoLog;
	private PendingIntent alarmIntentPassword, alarmIntentPhotoLog;

	@Override
	public void onReceive(Context context, Intent intent) {


		String type = intent.getStringExtra("alertType");

		Intent service = new Intent(context, SampleSchedulingService.class);
		service.putExtra("alertType", type);
		startWakefulService(context, service);
	}

	public void setAlarm(Context context, String notificationType) {
		Log.i("got", "call to sample alarm receiver");

		if (notificationType.equalsIgnoreCase("passwordChangeReminder")) {

			SharedPreferences sharedPref = context.getSharedPreferences(
					"catchlook", Context.MODE_PRIVATE);

			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(System.currentTimeMillis());

			calendar.add(Calendar.SECOND, 30);

			String reminderType = sharedPref.getString("reminder_type",
					"nothing");

			if (reminderType.equalsIgnoreCase("Bi-Monthly")) {
				System.out.println("bi monthly");
				calendar.add(Calendar.DAY_OF_MONTH, 15);
			} else if (reminderType.equalsIgnoreCase("Monthly")) {
				System.out.println("monthly");
				calendar.add(Calendar.MONTH, 1);
			} else if (reminderType.equalsIgnoreCase("Quaterly")) {
				System.out.println("quaterly");
				calendar.add(Calendar.MONTH, 3);
			} else if (reminderType.equalsIgnoreCase("Semi-Annually")) {
				System.out.println("semi annually");
				calendar.add(Calendar.MONTH, 6);
			} else if (reminderType.equalsIgnoreCase("Annually")) {
				System.out.println("annually");
				calendar.add(Calendar.YEAR, 1);
			}

			alarmMgrPassword = (AlarmManager) context
					.getSystemService(Context.ALARM_SERVICE);
			Intent intent = new Intent(context, SampleAlarmReceiver.class);
			intent.putExtra("alertType", "PasswordReminder");
			alarmIntentPassword = PendingIntent.getBroadcast(context, 0,
					intent, 0);

			alarmMgrPassword.set(AlarmManager.RTC_WAKEUP,
					calendar.getTimeInMillis(), alarmIntentPassword);

			ComponentName receiver = new ComponentName(context,
					SampleBootReceiver.class);
			PackageManager pm = context.getPackageManager();

			pm.setComponentEnabledSetting(receiver,
					PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
					PackageManager.DONT_KILL_APP);
		} else {
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(System.currentTimeMillis());

			alarmMgrPhotoLog = (AlarmManager) context
					.getSystemService(Context.ALARM_SERVICE);
			Intent intent = new Intent(context, SampleAlarmReceiver.class);
			intent.putExtra("alertType", "PhotoLog");
			alarmIntentPhotoLog = PendingIntent.getBroadcast(context, 0,
					intent, 0);

			alarmMgrPhotoLog.set(AlarmManager.RTC_WAKEUP,
					calendar.getTimeInMillis(), alarmIntentPhotoLog);

			ComponentName receiver = new ComponentName(context,
					SampleBootReceiver.class);
			PackageManager pm = context.getPackageManager();

			pm.setComponentEnabledSetting(receiver,
					PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
					PackageManager.DONT_KILL_APP);
		}
	}

	public void cancelAlarm(Context context, String notificationType) {
		// If the alarm has been set, cancel it.
		if (notificationType.equalsIgnoreCase("passwordChangeReminder")) {
			if (alarmMgrPassword != null) {
				alarmMgrPassword.cancel(alarmIntentPassword);
			}
			ComponentName receiver = new ComponentName(context,
					SampleBootReceiver.class);
			PackageManager pm = context.getPackageManager();

			pm.setComponentEnabledSetting(receiver,
					PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
					PackageManager.DONT_KILL_APP);
		} else {
			if (alarmMgrPhotoLog != null) {
				alarmMgrPhotoLog.cancel(alarmIntentPhotoLog);
			}
			ComponentName receiver = new ComponentName(context,
					SampleBootReceiver.class);
			PackageManager pm = context.getPackageManager();

			pm.setComponentEnabledSetting(receiver,
					PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
					PackageManager.DONT_KILL_APP);
		}

	}

}
