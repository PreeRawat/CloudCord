package com.cloudcord.presenters;

import android.app.Dialog;
import android.icu.text.LocaleDisplayNames;
import android.os.Bundle;

import com.cloudcord.BasePresenter;
import com.cloudcord.BaseView;

/**
 * Created by root on 21/3/17.
 */

public interface LoginContract {
    interface View extends BaseView<Presenter> {

        void navigateToAlarms();

    }

    interface Presenter extends BasePresenter {

        void performSoundCloudLogin();

        Dialog openDialog(int id, Bundle data);
    }
}
