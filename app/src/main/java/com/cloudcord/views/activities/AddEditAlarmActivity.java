package com.cloudcord.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.cloudcord.CloudCordApplication;
import com.cloudcord.R;
import com.cloudcord.datamodals.localrepo.AlarmDataSource;
import com.cloudcord.datamodals.localrepo.AlarmLocalDataSource;
import com.cloudcord.presenters.AddEditAlarmContract;
import com.cloudcord.presenters.AddEditAlarmPresenter;
import com.cloudcord.utils.DateTimePickers;
import com.cloudcord.utils.Interactor;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class AddEditAlarmActivity extends AppCompatActivity implements AddEditAlarmContract.View, Interactor {


    AddEditAlarmContract.Presenter mPresenter;
    String mTime;
    String mDate;
    EditText date, time, sound;
    Spinner mSpRepitition;

    AlarmDataSource alarmDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_alarm);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");*/

        alarmDataSource = CloudCordApplication.getDatabaseInstance();
        mPresenter = new AddEditAlarmPresenter(this, this, alarmDataSource);


        final EditText title = (EditText) findViewById(R.id.ettitle);

        sound = (EditText) findViewById(R.id.etsound);
        sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 22/3/17 open file explorer for music
                mPresenter.pickMediaFile();
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

        mSpRepitition = (Spinner) findViewById(R.id.sp_repetition);
        mSpRepitition.setAdapter(mPresenter.setDataToSpinner());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                String selectedRepitition = (String) mSpRepitition.getSelectedItem();
                mPresenter.saveAlarm( 0, title.getText().toString(), date.getText().toString(), time.getText().toString(), selectedRepitition, sound.getText().toString());

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
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
        if (presenter != null)
            mPresenter = presenter;
    }

    @Override
    public void getTime(int hour, int minute) {
        if (!(hour == 0 && minute == 0)) {
            this.mTime = hour + ":" + minute;
            time.setText(prettyTime(mTime));
        } else time.setText("");
    }


    @Override
    public void getDate(int year, int month, int day) {
        if (!(year == 0 && month == 0 && day == 0)) {
            this.mDate = year + "-" + month + "-" + day;
            date.setText(prettyDate(mDate));
        } else date.setText("");
    }

    public String prettyDate(String date) {
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date input = null;
        try {
            input = inputFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, ''yy");
        return dateFormat.format(input);
    }


    public String prettyTime(String time){
        DateFormat inputFormat = new SimpleDateFormat("hh:mm");
        Date input = null;
        try {
            input = inputFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(input);
    }


    @Override
    public void showEmptyAlarmError() {
        Snackbar.make(mSpRepitition, "Cannot set an empty alarm", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showAlarmsList() {
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == AddEditAlarmPresenter.PICKFILE_REQUEST_CODE && resultCode == RESULT_OK)
            sound.setText(data.getDataString());
    }
}
