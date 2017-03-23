package com.cloudcord.presenters;

import android.widget.ArrayAdapter;

import com.cloudcord.BasePresenter;
import com.cloudcord.BaseView;

/**
 * Created by root on 22/3/17.
 */

public interface AddEditAlarmContract {

    interface View extends BaseView<Presenter> {
        void showEmptyAlarmError();
        void showAlarmsList();
    }

    interface Presenter extends BasePresenter {
        void saveAlarm(int id, String title, String date, String time, String repetition, String soundPath);

        void pickMediaFile();

        ArrayAdapter setDataToSpinner();
    }

}
