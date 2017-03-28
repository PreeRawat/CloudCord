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

import android.support.annotation.NonNull;

import com.cloudcord.datamodals.modals.Alarms;

import java.util.List;

/**
 * Main entry point for accessing alarms data.
 * <p>
 * For simplicity, only getAlarmss() and getAlarms() have callbacks. Consider adding callbacks to other
 * methods to inform the user of network/database errors or successful operations.
 * For example, when a new alarm is created, it's synchronously stored in cache but usually every
 * operation on database or network should be executed in a different thread.
 */
public interface AlarmDataSource {

    interface LoadAlarmsCallback {

        void onAlarmsLoaded(List<Alarms> alarms);

        void onDataNotAvailable();
    }

    interface GetAlarmsCallback {

        void onAlarmsLoaded(Alarms alarm);

        void onDataNotAvailable();
    }

    void getAlarms(@NonNull LoadAlarmsCallback callback);

    void getAlarms(@NonNull String alarmId, @NonNull GetAlarmsCallback callback);

    long saveAlarms(@NonNull Alarms alarm);

    /*void completeAlarms(@NonNull Alarms alarm);

    void completeAlarms(@NonNull String alarmId);

    void activateAlarms(@NonNull Alarms alarm);

    void activateAlarms(@NonNull String alarmId);

    void clearCompletedAlarms();*/

    void getRepetitiveAlarms(@NonNull LoadAlarmsCallback callback);

    void refreshAlarms();

    void deleteAllAlarms();

    void deleteAlarms(@NonNull String alarmId);
}
