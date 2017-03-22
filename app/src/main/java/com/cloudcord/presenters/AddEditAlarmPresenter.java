package com.cloudcord.presenters;

import com.cloudcord.views.activities.AddEditAlarmActivity;

/**
 * Created by root on 22/3/17.
 */

public class AddEditAlarmPresenter implements AddEditAlarmContract.Presenter {

    AddEditAlarmContract.View mAddEditAlarmView;
    AddEditAlarmActivity mActivity;

    public AddEditAlarmPresenter(AddEditAlarmActivity addEditAlarmActivity, AddEditAlarmContract.View addEditAlarmView) {
        this.mAddEditAlarmView = addEditAlarmView;
        this.mActivity = addEditAlarmActivity;
    }

    @Override
    public void start() {

    }

    @Override
    public void saveAlarm(String title, String date, String time) {
        System.out.println("alarm details : " + title + time + date);
    }
}
