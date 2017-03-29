package com.cloudcord.presenters;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import com.cloudcord.datamodals.localrepo.AlarmDataSource;
import com.cloudcord.datamodals.modals.Alarms;
import com.cloudcord.views.activities.AddEditAlarmActivity;
import com.cloudcord.views.activities.AlarmActivity;
import com.cloudcord.views.fragments.AlarmActivityFragment;

import java.util.List;

/**
 * Created by root on 21/3/17.
 */

public class AlarmPresenter implements AlarmContract.Presenter {

    AlarmActivity mAlarmActivity;
    AlarmContract.View mAlarmActivityFragment;
    AlarmDataSource mAlarmDataSource;

    public static final int RESULT_CODE_ADDEDIT = 20;


    public AlarmPresenter(AlarmActivity alarmActivity, AlarmActivityFragment alarmActivityFragment, AlarmDataSource mAlarmDataSource) {
        this.mAlarmActivity = alarmActivity;
        this.mAlarmActivityFragment = alarmActivityFragment;
        this.mAlarmDataSource = mAlarmDataSource;
    }

    @Override
    public void start() {

    }

    @Override
    public void refreshList() {
        // TODO: 3/23/2017 get all alarms and set to the list 
    }

    @Override
    public void navigateToAddEditAlarm() {
        mAlarmActivity.startActivityForResult(new Intent(mAlarmActivity, AddEditAlarmActivity.class), RESULT_CODE_ADDEDIT);
    }

    @Override
    public void openAlarmDetails(Alarms clickedItem) {

    }

    @Override
    public void getAlarmsList() {
        mAlarmDataSource.getAlarms(new AlarmDataSource.LoadAlarmsCallback() {
            @Override
            public void onAlarmsLoaded(List<Alarms> alarms) {
                setListData(alarms);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    private void setListData(List<Alarms> alarms) {
        if(alarms.size()>0)
            mAlarmActivityFragment.setList(alarms);
        else
            mAlarmActivityFragment.showEmptyState(true);
    }

    @Override
    public void performSoundCloudLogin() {

    }

    @Override
    public Dialog openDialog(int id, Bundle data) {
        return null;
    }
}
