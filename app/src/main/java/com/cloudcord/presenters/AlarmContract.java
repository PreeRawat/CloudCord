package com.cloudcord.presenters;

import android.app.Dialog;
import android.os.Bundle;

import com.cloudcord.BasePresenter;
import com.cloudcord.BaseView;
import com.cloudcord.datamodals.modals.Alarms;

import java.util.List;

/**
 * Created by root on 21/3/17.
 */

public interface AlarmContract {

    interface View extends BaseView<Presenter>{

        void showEmptyState(boolean show);

        void setList(List<Alarms> dataList);

    }
    interface Presenter extends BasePresenter {

        void refreshList();

        void navigateToAddEditAlarm();

        void openAlarmDetails(Alarms clickedItem);

        void getAlarmsList();

        void performSoundCloudLogin();

        Dialog openDialog(int id, Bundle data);
    }
}
