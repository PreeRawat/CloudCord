package com.cloudcord.presenters;

import com.cloudcord.BasePresenter;
import com.cloudcord.BaseView;

/**
 * Created by root on 22/3/17.
 */

public interface AddEditAlarmContract {

    interface View extends BaseView<Presenter>{

    }

    interface Presenter extends BasePresenter{
        void saveAlarm(String title, String date, String time);
    }

}
