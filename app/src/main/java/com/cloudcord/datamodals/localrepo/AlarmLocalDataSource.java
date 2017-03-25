/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cloudcord.datamodals.localrepo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.cloudcord.datamodals.modals.Alarms;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Concrete implementation of a data source as a db.
 */
public class AlarmLocalDataSource implements AlarmDataSource {

    private AlarmDbHelper mDbHelper;

    public AlarmLocalDataSource(@NonNull Context context) {
        if(context != null)
            mDbHelper = new AlarmDbHelper(context);
    }

    @Override
    public void getAlarms(@NonNull LoadAlarmsCallback callback) {
        List<Alarms> alarms = new ArrayList<Alarms>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                AlarmPersistenceContract.AlarmEntry.COLUMN_NAME_ENTRY_ID,
                AlarmPersistenceContract.AlarmEntry.COLUMN_NAME_TITLE,
                AlarmPersistenceContract.AlarmEntry.COLUMN_NAME_DATE,
                AlarmPersistenceContract.AlarmEntry.COLUMN_NAME_TIME,
                AlarmPersistenceContract.AlarmEntry.COLUMN_NAME_REPETITION,
                AlarmPersistenceContract.AlarmEntry.COLUMN_NAME_SOUNDPATH
        };

        Cursor c = db.query(
                AlarmPersistenceContract.AlarmEntry.TABLE_NAME, projection, null, null, null, null, null);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                int id = c.getInt(c.getColumnIndexOrThrow(AlarmPersistenceContract.AlarmEntry.COLUMN_NAME_ENTRY_ID));
                String title = c.getString(c.getColumnIndexOrThrow(AlarmPersistenceContract.AlarmEntry.COLUMN_NAME_TITLE));
                String date =
                        c.getString(c.getColumnIndexOrThrow(AlarmPersistenceContract.AlarmEntry.COLUMN_NAME_DATE));
                String time = c.getString(c.getColumnIndexOrThrow(AlarmPersistenceContract.AlarmEntry.COLUMN_NAME_TIME));
                String repetition = c.getString(c.getColumnIndexOrThrow(AlarmPersistenceContract.AlarmEntry.COLUMN_NAME_REPETITION));
                String soundPath = c.getString(c.getColumnIndexOrThrow(AlarmPersistenceContract.AlarmEntry.COLUMN_NAME_SOUNDPATH));

                Alarms alarm = new Alarms(id, title, date, time, repetition, soundPath);
                alarms.add(alarm);
            }
        }
        if (c != null) {
            c.close();
        }

        db.close();

        if (alarms.isEmpty()) {
            // This will be called if the table is new or just empty.
            callback.onDataNotAvailable();
        } else {
            callback.onAlarmsLoaded(alarms);
        }

    }

    @Override
    public void getAlarms(@NonNull String taskId, @NonNull GetAlarmsCallback callback) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                AlarmPersistenceContract.AlarmEntry.COLUMN_NAME_ENTRY_ID,
                AlarmPersistenceContract.AlarmEntry.COLUMN_NAME_TITLE,
                AlarmPersistenceContract.AlarmEntry.COLUMN_NAME_DATE,
                AlarmPersistenceContract.AlarmEntry.COLUMN_NAME_TIME,
                AlarmPersistenceContract.AlarmEntry.COLUMN_NAME_REPETITION,
                AlarmPersistenceContract.AlarmEntry.COLUMN_NAME_SOUNDPATH
        };

        String selection = AlarmPersistenceContract.AlarmEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
        String[] selectionArgs = { taskId };

        Cursor c = db.query(
        AlarmPersistenceContract.AlarmEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        Alarms alarm = null;

        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            int id = c.getInt(c.getColumnIndexOrThrow(AlarmPersistenceContract.AlarmEntry.COLUMN_NAME_ENTRY_ID));
            String title = c.getString(c.getColumnIndexOrThrow(AlarmPersistenceContract.AlarmEntry.COLUMN_NAME_TITLE));
            String date =
                    c.getString(c.getColumnIndexOrThrow(AlarmPersistenceContract.AlarmEntry.COLUMN_NAME_DATE));
            String time = c.getString(c.getColumnIndexOrThrow(AlarmPersistenceContract.AlarmEntry.COLUMN_NAME_TIME));
            String repetition = c.getString(c.getColumnIndexOrThrow(AlarmPersistenceContract.AlarmEntry.COLUMN_NAME_REPETITION));
            String soundPath = c.getString(c.getColumnIndexOrThrow(AlarmPersistenceContract.AlarmEntry.COLUMN_NAME_SOUNDPATH));

            alarm = new Alarms(id, title, date, time, repetition, soundPath);
        }
        if (c != null) {
            c.close();
        }

        db.close();

        if (alarm != null) {
            callback.onAlarmsLoaded(alarm);
        } else {
            callback.onDataNotAvailable();
        }
    }

    @Override
    public void saveAlarms(@NonNull Alarms alarm) {
        if(alarm != null) {
            SQLiteDatabase db = mDbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            if(alarm.getmId()!=0)
                values.put(AlarmPersistenceContract.AlarmEntry.COLUMN_NAME_ENTRY_ID, alarm.getmId());

            values.put(AlarmPersistenceContract.AlarmEntry.COLUMN_NAME_TITLE, alarm.getmTitle());
            values.put(AlarmPersistenceContract.AlarmEntry.COLUMN_NAME_DATE, alarm.getmDate());
            values.put(AlarmPersistenceContract.AlarmEntry.COLUMN_NAME_TIME, alarm.getmTime());
            values.put(AlarmPersistenceContract.AlarmEntry.COLUMN_NAME_REPETITION, alarm.getmRepetition());
            values.put(AlarmPersistenceContract.AlarmEntry.COLUMN_NAME_SOUNDPATH, alarm.getmSoundPath());

            db.insert(AlarmPersistenceContract.AlarmEntry.TABLE_NAME, null, values);

            db.close();
        }
    }

    @Override
    public void getRepetitiveAlarms(@NonNull LoadAlarmsCallback callback) {
        List<Alarms> alarms = new ArrayList<Alarms>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                AlarmPersistenceContract.AlarmEntry.COLUMN_NAME_ENTRY_ID,
                AlarmPersistenceContract.AlarmEntry.COLUMN_NAME_TITLE,
                AlarmPersistenceContract.AlarmEntry.COLUMN_NAME_DATE,
                AlarmPersistenceContract.AlarmEntry.COLUMN_NAME_TIME,
                AlarmPersistenceContract.AlarmEntry.COLUMN_NAME_REPETITION,
                AlarmPersistenceContract.AlarmEntry.COLUMN_NAME_SOUNDPATH
        };

        Cursor c = db.query(
                AlarmPersistenceContract.AlarmEntry.TABLE_NAME, projection, null, null, null, null, null);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                int id = c.getInt(c.getColumnIndexOrThrow(AlarmPersistenceContract.AlarmEntry.COLUMN_NAME_ENTRY_ID));
                String title = c.getString(c.getColumnIndexOrThrow(AlarmPersistenceContract.AlarmEntry.COLUMN_NAME_TITLE));
                String date =
                        c.getString(c.getColumnIndexOrThrow(AlarmPersistenceContract.AlarmEntry.COLUMN_NAME_DATE));
                String time = c.getString(c.getColumnIndexOrThrow(AlarmPersistenceContract.AlarmEntry.COLUMN_NAME_TIME));
                String repetition = c.getString(c.getColumnIndexOrThrow(AlarmPersistenceContract.AlarmEntry.COLUMN_NAME_REPETITION));
                String soundPath = c.getString(c.getColumnIndexOrThrow(AlarmPersistenceContract.AlarmEntry.COLUMN_NAME_SOUNDPATH));

                Date parsedDate = new Date(Integer.parseInt(date.split("-")[0]), Integer.parseInt(date.split("-")[1]), Integer.parseInt(date.split("-")[2]));
                int hour = Integer.parseInt(time.split(":")[0]);
                int minute = Integer.parseInt(time.split(":")[1]);
                Calendar parsedAlarm = Calendar.getInstance();
                parsedAlarm.set(parsedDate.getYear(), parsedDate.getMonth(), parsedDate.getDate(), hour, minute );

                Calendar currentTime = Calendar.getInstance();
                Alarms alarm = new Alarms(id, title, date, time, repetition, soundPath);

                if(repetition.equalsIgnoreCase("does not repeat") && currentTime.before(parsedAlarm))
                    alarms.add(alarm);
                else if(!repetition.equalsIgnoreCase("does not repeat"))
                    alarms.add(alarm);
            }
        }
        if (c != null) {
            c.close();
        }

        db.close();

        if (alarms.isEmpty()) {
            // This will be called if the table is new or just empty.
            callback.onDataNotAvailable();
        } else {
            callback.onAlarmsLoaded(alarms);
        }

    }

/*    @Override
    public void completeAlarms(@NonNull Alarms alarm) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(AlarmPersistenceContract.AlarmEntry.COLUMN_NAME_COMPLETED, true);

        String selection = AlarmPersistenceContract.AlarmEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
        String[] selectionArgs = { alarm.getmId() };

        db.update(AlarmPersistenceContract.AlarmEntry.TABLE_NAME, values, selection, selectionArgs);

        db.close();
    }

    @Override
    public void completeAlarms(@NonNull String taskId) {
        // Not required for the local data source because the {@link TasksRepository} handles
        // converting from a {@code taskId} to a {@link task} using its cached data.
    }

    @Override
    public void activateAlarms(@NonNull Alarms alarm) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(AlarmPersistenceContract.AlarmEntry.COLUMN_NAME_COMPLETED, false);

        String selection = AlarmPersistenceContract.AlarmEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
        String[] selectionArgs = { alarm.getmId() };

        db.update(AlarmPersistenceContract.AlarmEntry.TABLE_NAME, values, selection, selectionArgs);

        db.close();
    }*/

/*    @Override
    public void activateAlarms(@NonNull String taskId) {
        // Not required for the local data source because the {@link TasksRepository} handles
        // converting from a {@code taskId} to a {@link task} using its cached data.
    }

    @Override
    public void clearCompletedAlarms() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        String selection = AlarmPersistenceContract.AlarmEntry.COLUMN_NAME_COMPLETED + " LIKE ?";
        String[] selectionArgs = { "1" };

        db.delete(AlarmPersistenceContract.AlarmEntry.TABLE_NAME, selection, selectionArgs);

        db.close();
    }*/

    @Override
    public void refreshAlarms() {
        // Not required because the {@link TasksRepository} handles the logic of refreshing the
        // tasks from all the available data sources.
    }

    @Override
    public void deleteAllAlarms() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        db.delete(AlarmPersistenceContract.AlarmEntry.TABLE_NAME, null, null);

        db.close();
    }

    @Override
    public void deleteAlarms(@NonNull String taskId) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        String selection = AlarmPersistenceContract.AlarmEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
        String[] selectionArgs = { taskId };

        db.delete(AlarmPersistenceContract.AlarmEntry.TABLE_NAME, selection, selectionArgs);

        db.close();
    }
}
