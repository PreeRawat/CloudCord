package com.cloudcord.datamodals.modals;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by root on 22/3/17.
 */

public class Alarms implements Parcelable{

    public Alarms(Parcel in) {
        mId = in.readInt();
        mTitle = in.readString();
        mDate = in.readString();
        mTime = in.readString();
        mRepetition = in.readString();
        mSoundPath = in.readString();
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmTime() {
        return mTime;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmRepetition() {
        return mRepetition;
    }

    public void setmRepetition(String mRepetition) {
        this.mRepetition = mRepetition;
    }

    public String getmSoundPath() {
        return mSoundPath;
    }

    public void setmSoundPath(String mSoundPath) {
        this.mSoundPath = mSoundPath;
    }

    public Alarms(int mId, String mTitle, String mDate, String mTime, String mRepetition, String mSoundPath) {
        this.mId = mId;
        this.mTime = mTime;
        this.mDate = mDate;
        this.mTitle = mTitle;
        this.mRepetition = mRepetition;
        this.mSoundPath = mSoundPath;
    }

    private int mId;
    private String mTime;
    private String mDate;
    private String mTitle;
    private String mRepetition;
    private String mSoundPath;


    public boolean isEmpty(){
        if((mTitle == null || mTitle.equalsIgnoreCase("")) && (mTime == null || mTime.equalsIgnoreCase("")) && (mDate == null || mDate.equalsIgnoreCase("")) /*&& (mSoundPath == null || mSoundPath.equalsIgnoreCase(""))*/)
            return true;
        return false;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mTitle);
        dest.writeString(mDate);
        dest.writeString(mTime);
        dest.writeString(mRepetition);
        dest.writeString(mSoundPath);
    }

    public static final Parcelable.Creator CREATOR =
            new Parcelable.Creator() {
                public Alarms createFromParcel(Parcel in) {
                    return new Alarms(in);
                }

                public Alarms[] newArray(int size) {
                    return new Alarms[size];
                }
            };
}
