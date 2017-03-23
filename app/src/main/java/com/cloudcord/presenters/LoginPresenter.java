
package com.cloudcord.presenters;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.cloudcord.utils.Api;
import com.cloudcord.views.activities.LoginActivity;
import com.soundcloud.api.ApiWrapper;
import com.soundcloud.api.Env;
import com.soundcloud.api.Token;

import java.io.IOException;

/**
 * Created by root on 21/3/17.
 */


public class LoginPresenter implements LoginContract.Presenter {

    LoginContract.View mLoginView;
    LoginActivity mLoginActivity;

    private AccountManager mAccountManager;
    public static final String TAG = "soundcloudlogin";
    public static final String SC_ACCOUNT_TYPE = "com.soundcloud.android.account";
    public static final String ACCESS_TOKEN = "access_token";

    private static final Uri MARKET_URI = Uri.parse("market://details?id=com.soundcloud.android");
    private static final int DIALOG_NOT_INSTALLED = 0;

    public LoginPresenter(LoginContract.View mLoginView, LoginActivity loginActivity) {
        this.mLoginView = mLoginView;
        this.mLoginActivity = loginActivity;

    }

    private void getSCAccount() {
        mAccountManager = AccountManager.get(mLoginActivity);
        final Account account = getAccount();
        if (account != null) {
            new Thread(mGetToken).start();
        } else {
            addAccount();
        }
    }

    private final Runnable mGetToken = new Runnable() {
        @Override
        public void run() {
            final Token token = getToken(getAccount());
            if (token != null) {
                success(token);
            } else {
                notifyUser("Could not get token.");
            }
        }
    };

    // request a new SoundCloud account to be added
    private void addAccount() {
        notifyUser("No active SoundCloud Account, login to the app to continue.");
        mAccountManager.addAccount(SC_ACCOUNT_TYPE, ACCESS_TOKEN, null, null, mLoginActivity,
                new AccountManagerCallback<Bundle>() {
                    @Override
                    public void run(AccountManagerFuture<Bundle> future) {
                        try {
                            Bundle result = future.getResult();
                            String name = result.getString(AccountManager.KEY_ACCOUNT_NAME);
                            Log.d(TAG, "created account for " + name);

                            // should have an account by now
                            Account account = getAccount();
                            if (account != null) {
                                new Thread(mGetToken).start();
                            } else {
                                notifyUser("Could not create account.");
                            }
                        } catch (OperationCanceledException e) {
                            notifyUser("Operation Cancelled");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (AuthenticatorException e) {
                            // SoundCloud app is not installed
                            appNotInstalled();
                        }
                    }
                }, null);
    }


    private void appNotInstalled() {
        mLoginActivity.showDialog(DIALOG_NOT_INSTALLED);
    }


    private void success(Token token) {
        // create the API wrapper with the token
        Api.wrapper = new ApiWrapper(null, null, null, token, Env.SANDBOX);
        System.out.println(" Success : " + token.toString());
        // and launch our main activity
        //startActivity(new Intent(this, UploadFile.class));
    }

    private Account getAccount() {
        Account[] accounts = mAccountManager.getAccountsByType(SC_ACCOUNT_TYPE);
        if (accounts.length > 0) {
            return accounts[0];
        } else {
            return null;
        }
    }

    private Token getToken(Account account) {
        try {
            String access = mAccountManager.blockingGetAuthToken(account, ACCESS_TOKEN, false);
            return new Token(access, null, Token.SCOPE_NON_EXPIRING);
        } catch (OperationCanceledException e) {
            notifyUser("");
            return null;
        } catch (IOException e) {
            Log.w(TAG, "error", e);
            return null;
        } catch (AuthenticatorException e) {
            Log.w(TAG, "error", e);
            return null;
        }
    }

    public void notifyUser(final int id) {
        notifyUser(mLoginActivity.getResources().getString(id));
    }

    @Override
    public Dialog openDialog(int id, Bundle data) {
        if (DIALOG_NOT_INSTALLED == id) {
            return new AlertDialog.Builder(mLoginActivity)
                    .setTitle("SoundCloud not found!")
                    .setMessage("Please install SoundCloud app to continue.")
                    .setPositiveButton(android.R.string.yes, new Dialog.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intentPlayStore = new Intent(Intent.ACTION_VIEW, MARKET_URI);
                            if (intentPlayStore.resolveActivity(mLoginActivity.getPackageManager()) != null)
                                mLoginActivity.startActivity(intentPlayStore);
                            else {
                                notifyUser("Play Store not found");
                                mLoginActivity.finish();
                            }
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).create();
        } else {
            return null;
        }
    }

    private void notifyUser(final String text) {
        mLoginActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mLoginActivity, text, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void start() {

    }

    @Override
    public void performSoundCloudLogin() {
        //todo: Get SoundCloud Account and access token
        //getSCAccount();

        // TODO: 21/3/17 Open Alarms page as of now
        mLoginActivity.navigateToAlarms();

    }

}

