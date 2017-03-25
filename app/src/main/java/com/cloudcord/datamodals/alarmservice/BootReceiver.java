package com.cloudcord.datamodals.alarmservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.cloudcord.CloudCordApplication;
import com.cloudcord.datamodals.localrepo.AlarmDataSource;
import com.cloudcord.datamodals.modals.Alarms;

import java.util.List;

/**
 * This BroadcastReceiver automatically (re)starts the alarm when the device is
 * rebooted. This receiver is set to be disabled (android:enabled="false") in the
 * application's manifest file. When the user sets the alarm, the receiver is enabled.
 * When the user cancels the alarm, the receiver is disabled, so that rebooting the
 * device will not trigger this receiver.
 */
// BEGIN_INCLUDE(autostart)
public class BootReceiver extends BroadcastReceiver implements AlarmDataSource.LoadAlarmsCallback{
    AlarmReceiver alarm = new AlarmReceiver();
    AlarmDataSource alarmDataSource;
    AlarmDataSource.LoadAlarmsCallback loadAlarmsCallback;
    Context context;
    //AlarmReceiver alarmNotification = new AlarmReceiver();
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
        {
            this.context = context;
            loadAlarmsCallback = (AlarmDataSource.LoadAlarmsCallback) context;
            alarmDataSource = CloudCordApplication.getDatabaseInstance();
            alarmDataSource.getRepetitiveAlarms(loadAlarmsCallback);
            /*Alarms alarms = intent.getExtras().getParcelable("alarm");
            System.out.println(" boot receiver " + alarms.getmTitle());*/

            //alarmNotification.setAlarm(context, "PhotoLog");
        }
    }

    @Override
    public void onAlarmsLoaded(List<Alarms> alarms) {
        for (Alarms futureAlarm: alarms) {
            alarm.setAlarm(context, futureAlarm);
        }

    }

    @Override
    public void onDataNotAvailable() {

    }
}
//END_INCLUDE(autostart)
