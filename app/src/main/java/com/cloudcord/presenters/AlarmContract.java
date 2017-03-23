package com.cloudcord.presenters;

import android.app.Dialog;
import android.os.Bundle;

import com.cloudcord.BasePresenter;
import com.cloudcord.BaseView;

/**
 * Created by root on 21/3/17.
 */

public interface AlarmContract {

    interface View extends BaseView<Presenter>{



    }
    interface Presenter extends BasePresenter {

        void refreshList();

        void navigateToAddEditAlarm();

        void performSoundCloudLogin();

        Dialog openDialog(int id, Bundle data);
    }
}
