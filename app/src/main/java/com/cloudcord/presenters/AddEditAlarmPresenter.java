package com.cloudcord.presenters;

import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.cloudcord.R;
import com.cloudcord.datamodals.alarmservice.AlarmReceiver;
import com.cloudcord.datamodals.localrepo.AlarmDataSource;
import com.cloudcord.datamodals.modals.Alarms;
import com.cloudcord.views.activities.AddEditAlarmActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 22/3/17.
 */

public class AddEditAlarmPresenter implements AddEditAlarmContract.Presenter {

    public static final int PICKFILE_REQUEST_CODE = 10;
    AddEditAlarmContract.View mAddEditAlarmView;
    AddEditAlarmActivity mActivity;

    AlarmDataSource mAlarmDataSource;
    AlarmReceiver mAlarmReceiver;

    ArrayAdapter<String> adapter;
    List<String> list;


    public AddEditAlarmPresenter(AddEditAlarmActivity addEditAlarmActivity, AddEditAlarmContract.View addEditAlarmView, AlarmDataSource alarmDataSource) {
        this.mAddEditAlarmView = addEditAlarmView;
        this.mActivity = addEditAlarmActivity;
        this.mAlarmDataSource = alarmDataSource;
    }

    @Override
    public void start() {


    }

    @Override
    public void saveAlarm(int id, String title, String date, String time, String repetition, String soundPath) {
        System.out.println("alarm details : " + title + time + date + repetition);
        Alarms newAlarm = new Alarms(id, title, date, time, repetition, soundPath);
        if (!repetition.equalsIgnoreCase("Custom")) {
            // TODO: 23/3/17 Save to db and set reminder
            int mId = (int) saveToDb(newAlarm);
            System.out.println("id "+mId);
            if (mId!=-1) {
                newAlarm.setmId(mId);
                setAlarmNotification(newAlarm);
            } else {
                Toast.makeText(mActivity, "Alarm could not be set.", Toast.LENGTH_SHORT).show();
            }
            mAddEditAlarmView.showAlarmsList();
        }
        //else
            // TODO: 23/3/17 open custom repetition dialog and overload saveAlarm with repetitions
            //openCustomRepetitionDialog();


    }

    private void setAlarmNotification(Alarms alarm) {
        mAlarmReceiver = new AlarmReceiver();
        mAlarmReceiver.setAlarm(mActivity, alarm);
    }

    @Override
    public void pickMediaFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("audio/*");
        mActivity.startActivityForResult(intent, PICKFILE_REQUEST_CODE);
    }

    private long saveToDb(Alarms alarm) {
        //Alarms newAlarm = new Alarms(id, title, date, time, repetition, soundPath);
        System.out.println("alarm details : " + alarm.getmTitle());
       /* if (newAlarm.isEmpty()) {
            mAddEditAlarmView.showEmptyAlarmError();
        } else {*/

            return mAlarmDataSource.saveAlarms(alarm);

           //

        //}
    }

    @Override
    public ArrayAdapter setDataToSpinner() {
        list = new ArrayList<>();
        list.add("Does not repeat");
        list.add("Daily");
        list.add("Weekly");
        list.add("Monthly");
        list.add("Yearly");
        list.add("Custom");
        adapter = new ArrayAdapter<>(mActivity,
                R.layout.frequency_spinner_item, list);
        adapter.setDropDownViewResource(R.layout.frequency_spinner_item);
        return adapter;
    }

}
