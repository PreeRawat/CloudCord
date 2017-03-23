package com.cloudcord.views.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.cloudcord.R;
import com.cloudcord.presenters.LoginContract;
import com.cloudcord.presenters.LoginPresenter;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    private Button btnLogin;
    private LoginContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mPresenter = new LoginPresenter(this, this);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.performSoundCloudLogin();
            }
        });

    }

    @Override
    protected Dialog onCreateDialog(int id, Bundle data) {
        return mPresenter.openDialog(id, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPresenter != null)
            mPresenter.start();
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        if (presenter != null)
            this.mPresenter = presenter;
    }

    @Override
    public void navigateToAlarms() {
        startActivity(new Intent(this, AddEditAlarmActivity.class));
    }

}
