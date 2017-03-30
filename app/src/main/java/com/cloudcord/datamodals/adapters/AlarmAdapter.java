package com.cloudcord.datamodals.adapters;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.cloudcord.R;
import com.cloudcord.datamodals.modals.Alarms;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 22/3/17.
 */

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.ViewHolder> {

    private List<Alarms> mAlarms;
    private AlarmItemListener mItemListener;

    public AlarmAdapter(ArrayList<Alarms> alarms, AlarmItemListener mItemListener) {

        this.mAlarms = alarms;
        this.mItemListener = mItemListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = parent;
        if (rowView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            rowView = inflater.inflate(R.layout.alarm_item, parent, false);
        }

        ViewHolder viewHolder = new ViewHolder(rowView);
        return viewHolder;
        /*View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.alarm_item, parent, false);
        return new ViewHolder(v);*/

    }


    public void updateListData(List<Alarms> alarms) {
        //if(alarms!=null) {
            mAlarms = alarms;
        System.out.println(" asdfsdf: "+ mAlarms.size());
            notifyDataSetChanged();
        //}
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Alarms item = mAlarms.get(position);
        holder.timeTextView.setText(item.getmTime());
        System.out.println(" binding: "+item.getmTime());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mAlarms.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView timeTextView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            timeTextView = (TextView) itemView.findViewById(R.id.time);
            //messageButton = (Button) itemView.findViewById(R.id.message_button);
        }
    }

    public interface AlarmItemListener {

        void onAlarmClick(Alarms clickedAlarm);

       /* void onCompleteAlarmClick(Alarms completedAlarm);

        void onActivateAlarmClick(Alarms activatedAlarm);*/
    }
}
