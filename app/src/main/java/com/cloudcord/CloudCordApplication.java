package com.cloudcord;

import android.app.Application;

import com.cloudcord.datamodals.localrepo.AlarmDataSource;
import com.cloudcord.datamodals.localrepo.AlarmLocalDataSource;

/**
 * Created by Pree on 3/21/2017.
 */

public class CloudCordApplication extends Application {
    private static AlarmDataSource alarmDataSource =null;

    @Override
    public void onCreate() {
        super.onCreate();

        if(alarmDataSource==null)
            alarmDataSource  = new AlarmLocalDataSource(getApplicationContext());
    }

    public static AlarmDataSource getDatabaseInstance (){
        return alarmDataSource;
    }
}


