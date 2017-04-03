package com.cloudcord.datamodals.alarmservice;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.text.format.Time;
import android.util.Log;

import com.cloudcord.datamodals.modals.Alarms;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Broadcast Receiver class to send password change reminders and to send
 * notifications on a new snapshot available instance.
 */
public class AlarmReceiver extends WakefulBroadcastReceiver {

	private AlarmManager mAlarmManager;
	private PendingIntent alarmIntent;

	@Override
	public void onReceive(Context context, Intent intent) {


		Alarms alarms = intent.getExtras().getParcelable("alarm");

		Intent service = new Intent(context, SchedulingService.class);
		service.putExtra("alarm", alarms);
		startWakefulService(context, service);
	}

	public void setAlarm(Context context, Alarms alarm) throws ParseException {
		Log.i("got", "call to alarm receiver");
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, ''yy");
		Date date = dateFormat.parse(alarm.getmDate());
		System.out.println("date" +date.getYear()+ date.getMonth()+ date.getDate());
		SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
		Date time = timeFormat.parse(alarm.getmTime());
		/*int hour = Integer.parseInt(alarm.getmTime().split(":")[0]);
		int minute = Integer.parseInt(alarm.getmTime().split(":")[1].substring(0,2));
		*/System.out.println("time " +time.getHours()+ " " +time.getMinutes());
		Calendar calendar = Calendar.getInstance();
		calendar.set(date.getYear(), date.getMonth(), date.getDate(), time.getHours(), time.getMinutes() );

		mAlarmManager = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, AlarmReceiver.class);
		intent.putExtra("alarm", alarm);
		final int _id = (int) System.currentTimeMillis();
		alarmIntent = PendingIntent.getBroadcast(context, _id,
				intent, 0);

		mAlarmManager.set(AlarmManager.RTC_WAKEUP,
				calendar.getTimeInMillis(), alarmIntent);

		ComponentName receiver = new ComponentName(context,
				BootReceiver.class);
		PackageManager pm = context.getPackageManager();

		pm.setComponentEnabledSetting(receiver,
				PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
				PackageManager.DONT_KILL_APP);

		/*if (notificationType.equalsIgnoreCase("passwordChangeReminder")) {

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
			Intent intent = new Intent(context, AlarmReceiver.class);
			intent.putExtra("alertType", "PasswordReminder");
			alarmIntentPassword = PendingIntent.getBroadcast(context, 0,
					intent, 0);

			alarmMgrPassword.set(AlarmManager.RTC_WAKEUP,
					calendar.getTimeInMillis(), alarmIntentPassword);

			ComponentName receiver = new ComponentName(context,
					BootReceiver.class);
			PackageManager pm = context.getPackageManager();

			pm.setComponentEnabledSetting(receiver,
					PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
					PackageManager.DONT_KILL_APP);
		} else {
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(System.currentTimeMillis());

			alarmMgrPhotoLog = (AlarmManager) context
					.getSystemService(Context.ALARM_SERVICE);
			Intent intent = new Intent(context, AlarmReceiver.class);
			intent.putExtra("alertType", "PhotoLog");
			alarmIntentPhotoLog = PendingIntent.getBroadcast(context, 0,
					intent, 0);

			alarmMgrPhotoLog.set(AlarmManager.RTC_WAKEUP,
					calendar.getTimeInMillis(), alarmIntentPhotoLog);

			ComponentName receiver = new ComponentName(context,
					BootReceiver.class);
			PackageManager pm = context.getPackageManager();

			pm.setComponentEnabledSetting(receiver,
					PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
					PackageManager.DONT_KILL_APP);
		}*/
	}

	public void cancelAlarm(Context context, String notificationType) {
		// If the alarm has been set, cancel it.
		/*if (notificationType.equalsIgnoreCase("passwordChangeReminder")) {
			if (alarmMgrPassword != null) {
				alarmMgrPassword.cancel(alarmIntentPassword);
			}
			ComponentName receiver = new ComponentName(context,
					BootReceiver.class);
			PackageManager pm = context.getPackageManager();

			pm.setComponentEnabledSetting(receiver,
					PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
					PackageManager.DONT_KILL_APP);
		} else {
			if (alarmMgrPhotoLog != null) {
				alarmMgrPhotoLog.cancel(alarmIntentPhotoLog);
			}
			ComponentName receiver = new ComponentName(context,
					BootReceiver.class);
			PackageManager pm = context.getPackageManager();

			pm.setComponentEnabledSetting(receiver,
					PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
					PackageManager.DONT_KILL_APP);
		}*/

	}

}
