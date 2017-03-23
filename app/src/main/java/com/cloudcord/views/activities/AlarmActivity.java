package com.cloudcord.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.cloudcord.R;
import com.cloudcord.presenters.AlarmContract;
import com.cloudcord.presenters.AlarmPresenter;
import com.cloudcord.utils.ActivityUtils;
import com.cloudcord.views.fragments.AlarmActivityFragment;

public class AlarmActivity extends AppCompatActivity {

    public AlarmContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AlarmActivityFragment alarmActivityFragment =
                (AlarmActivityFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (alarmActivityFragment == null) {
            // Create the fragment
            alarmActivityFragment = AlarmActivityFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), alarmActivityFragment, R.id.contentFrame);
        }

        mPresenter = new AlarmPresenter(this, alarmActivityFragment);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                mPresenter.navigateToAddEditAlarm();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alarm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == AlarmPresenter.RESULT_CODE_ADDEDIT && resultCode == RESULT_OK){

        }

    }
}
