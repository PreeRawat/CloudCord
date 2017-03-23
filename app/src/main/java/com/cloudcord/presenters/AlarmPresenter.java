package com.cloudcord.presenters;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import com.cloudcord.views.activities.AddEditAlarmActivity;
import com.cloudcord.views.activities.AlarmActivity;
import com.cloudcord.views.fragments.AlarmActivityFragment;

/**
 * Created by root on 21/3/17.
 */

public class AlarmPresenter implements AlarmContract.Presenter {

    AlarmActivity mAlarmActivity;
    AlarmContract.View mAlarmActivityFragment;

    public static final int RESULT_CODE_ADDEDIT = 20;


    public AlarmPresenter(AlarmActivity alarmActivity, AlarmActivityFragment alarmActivityFragment) {
        this.mAlarmActivity = alarmActivity;
        this.mAlarmActivityFragment = alarmActivityFragment;
    }

    @Override
    public void start() {

    }

    @Override
    public void navigateToAddEditAlarm() {
        mAlarmActivity.startActivityForResult(new Intent(mAlarmActivity, AddEditAlarmActivity.class), RESULT_CODE_ADDEDIT);
    }

    @Override
    public void performSoundCloudLogin() {

    }

    @Override
    public Dialog openDialog(int id, Bundle data) {
        return null;
    }
}
