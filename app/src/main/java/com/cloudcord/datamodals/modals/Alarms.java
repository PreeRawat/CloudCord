package com.cloudcord.datamodals.modals;

import java.util.Date;

/**
 * Created by root on 22/3/17.
 */

public class Alarms {

    private String mId;
    private int mStartHour;
    private int mStartMinute;
    private Date mStartDate;
    private boolean mIsRepetitive;
    private String mTitle;
    private String mDescription;
    private String[] mRepetitiveDays;

    public Alarms(int mStartHour, int mStartMinute, Date mStartDate, boolean mIsRepetitive, String mTitle, String mDescription, String[] mRepetitiveDays) {

    }

    public Alarms(String title, String description, String itemId, boolean completed) {
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public int getmStartHour() {
        return mStartHour;
    }

    public void setmStartHour(int mStartHour) {
        this.mStartHour = mStartHour;
    }

    public int getmStartMinute() {
        return mStartMinute;
    }

    public void setmStartMinute(int mStartMinute) {
        this.mStartMinute = mStartMinute;
    }

    public Date getmStartDate() {
        return mStartDate;
    }

    public void setmStartDate(Date mStartDate) {
        this.mStartDate = mStartDate;
    }

    public boolean ismIsRepetitive() {
        return mIsRepetitive;
    }

    public void setmIsRepetitive(boolean mIsRepetitive) {
        this.mIsRepetitive = mIsRepetitive;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String[] getmRepetitiveDays() {
        return mRepetitiveDays;
    }

    public void setmRepetitiveDays(String[] mRepetitiveDays) {
        this.mRepetitiveDays = mRepetitiveDays;
    }




}
