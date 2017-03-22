package com.cloudcord.datamodals.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.cloudcord.R;
import com.cloudcord.datamodals.modals.Alarms;

import java.util.List;

/**
 * Created by root on 22/3/17.
 */

public class AlarmAdapter extends BaseAdapter {

    private List<Alarms> mAlarms;

    @Override
    public int getCount() {
        return mAlarms.size();
    }

    @Override
    public Alarms getItem(int position) {
        return mAlarms.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            rowView = inflater.inflate(R.layout.alarm_item, parent, false);
        }
        return null;
    }

    public interface AlarmItemListener {

        void onAlarmClick(Alarms clickedAlarm);

        void onCompleteAlarmClick(Alarms completedAlarm);

        void onActivateAlarmClick(Alarms activatedAlarm);
    }
}
