package com.cloudcord.datamodals.adapters;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cloudcord.R;
import com.cloudcord.datamodals.modals.Alarms;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by root on 22/3/17.
 */

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.ViewHolder> {

    private List<Alarms> mAlarms;
    private AlarmItemListener mItemListener;
    private FragmentActivity activity;

    public AlarmAdapter(ArrayList<Alarms> alarms, AlarmItemListener mItemListener, FragmentActivity activity) {
        this.activity = activity;
        this.mAlarms = alarms;
        this.mItemListener = mItemListener;
    }

    @Override
    public AlarmAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.alarm_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AlarmAdapter.ViewHolder holder, int position) {
        holder.time.setText(mAlarms.get(position).getmTime());
        holder.date.setText(mAlarms.get(position).getmDate());
        holder.soundName.setText(prettySound(mAlarms.get(position).getmSoundPath()));
        holder.title.setText(mAlarms.get(position).getmTitle());

    }

    public void updateListData(List<Alarms> alarms) {
        if (alarms != null) {
            mAlarms = alarms;
            notifyDataSetChanged();
        }
    }

    public String prettySound(String soundPath){
        if(soundPath!=null && !soundPath.equals("")) {
            soundPath = soundPath.substring(soundPath.lastIndexOf("/")+1);
            soundPath = soundPath.replace("%20"," ");
            return soundPath;
        } else return "NA";
    }

    @Override
    public int getItemCount() {
        return mAlarms.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView time, date, soundName, title;

        public ViewHolder(View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.itemtime);
            date = (TextView) itemView.findViewById(R.id.date);
            soundName = (TextView) itemView.findViewById(R.id.sound_name);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }


    public interface AlarmItemListener {

        void onAlarmClick(Alarms clickedAlarm);

       /* void onCompleteAlarmClick(Alarms completedAlarm);

        void onActivateAlarmClick(Alarms activatedAlarm);*/
    }
}
