package com.cloudcord.presenters;

import android.content.Intent;
import android.widget.ArrayAdapter;

import com.cloudcord.R;
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
        if (!repetition.equalsIgnoreCase("Custom"))
            // TODO: 23/3/17 Save to db and set reminder
            saveToDb(id, title, date, time, repetition, soundPath);
        //else
            // TODO: 23/3/17 open custom repetition dialog and overload saveAlarm with repetitions
            //openCustomRepetitionDialog();


    }

    @Override
    public void pickMediaFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("file/*");
        mActivity.startActivityForResult(intent, PICKFILE_REQUEST_CODE);
    }

    private void saveToDb(int id, String title, String date, String time, String repetition, String soundPath) {
        Alarms newAlarm = new Alarms(id, title, date, time, repetition, soundPath);
        System.out.println("alarm details : " + newAlarm.getmTitle());
       /* if (newAlarm.isEmpty()) {
            mAddEditAlarmView.showEmptyAlarmError();
        } else {*/

            mAlarmDataSource.saveAlarms(newAlarm);

            mAddEditAlarmView.showAlarmsList();

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
