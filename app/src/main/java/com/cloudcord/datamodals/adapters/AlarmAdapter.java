package com.cloudcord.datamodals.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.cloudcord.R;
import com.cloudcord.datamodals.modals.Alarms;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 22/3/17.
 */

public class AlarmAdapter extends RecyclerView.Adapter {

    private List<Alarms> mAlarms;
    private AlarmItemListener mItemListener;

    public AlarmAdapter(ArrayList<Alarms> alarms, AlarmItemListener mItemListener) {
        this.mAlarms = alarms;
        this.mItemListener = mItemListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = parent;
        if (rowView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            rowView = inflater.inflate(R.layout.alarm_item, parent, false);
        }
        return null;
    }


    public void updateListData(List<Alarms> alarms) {
        if(alarms!=null) {
            mAlarms = alarms;
            notifyDataSetChanged();
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mAlarms.size();
    }

    public interface AlarmItemListener {

        void onAlarmClick(Alarms clickedAlarm);

       /* void onCompleteAlarmClick(Alarms completedAlarm);

        void onActivateAlarmClick(Alarms activatedAlarm);*/
    }
}
