package com.cloudcord.views.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.cloudcord.R;
import com.cloudcord.presenters.AddEditAlarmContract;
import com.cloudcord.presenters.AddEditAlarmPresenter;
import com.cloudcord.utils.DateTimePickers;
import com.cloudcord.utils.Interactor;


public class AddEditAlarmActivity extends AppCompatActivity implements AddEditAlarmContract.View, Interactor{

    Interactor mInteractor;
    AddEditAlarmContract.Presenter mPresenter;
    String mTime;
    String mDate;
    EditText date, time, sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_alarm);

        mPresenter = new AddEditAlarmPresenter(this, this);


        final EditText title = (EditText) findViewById(R.id.ettitle);

        sound = (EditText) findViewById(R.id.etsound);
        sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 22/3/17 open file explorer for music
            }
        });

        date = (EditText) findViewById(R.id.etdate);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });

        time = (EditText) findViewById(R.id.ettime);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                mPresenter.saveAlarm(title.getText().toString(), mDate, mTime);
            }
        });
    }

    public void showTimePickerDialog() {
        DialogFragment newFragment = new DateTimePickers.TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DateTimePickers.DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void setPresenter(AddEditAlarmContract.Presenter presenter) {
        if(presenter != null)
            mPresenter = presenter;
    }

    @Override
    public void getTime(int hour, int minute) {
        if(!(hour==0 && minute==0)) {
            this.mTime = hour + ":" + minute;
            time.setText(mTime);
        } else time.setText("");
    }


    @Override
    public void getDate(int year, int month, int day) {
        if(!(year==0 && month==0 && day==0)) {
            this.mDate = year + "-" + month + "-" + day;
            date.setText(mDate);
        } else date.setText("");
    }


}
