package com.cloudcord.datamodals.alarmservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.cloudcord.datamodals.modals.Alarms;

/**
 * This BroadcastReceiver automatically (re)starts the alarm when the device is
 * rebooted. This receiver is set to be disabled (android:enabled="false") in the
 * application's manifest file. When the user sets the alarm, the receiver is enabled.
 * When the user cancels the alarm, the receiver is disabled, so that rebooting the
 * device will not trigger this receiver.
 */
// BEGIN_INCLUDE(autostart)
public class BootReceiver extends BroadcastReceiver {
    AlarmReceiver alarm = new AlarmReceiver();
    //AlarmReceiver alarmNotification = new AlarmReceiver();
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
        {
            Alarms alarms = intent.getExtras().getParcelable("alarm");
            System.out.println(" boot receiver " + alarms.getmTitle());
            alarm.setAlarm(context, (Alarms) intent.getExtras().getParcelable("alarm"));
            //alarmNotification.setAlarm(context, "PhotoLog");
        }
    }
}
//END_INCLUDE(autostart)
