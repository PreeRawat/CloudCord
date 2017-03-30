package com.cloudcord.views.fragments;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cloudcord.R;
import com.cloudcord.datamodals.adapters.AlarmAdapter;
import com.cloudcord.datamodals.modals.Alarms;
import com.cloudcord.presenters.AlarmContract;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class AlarmActivityFragment extends Fragment implements AlarmContract.View {

    private AlarmContract.Presenter mPresenter;

    private RecyclerView recyclerViewAlarms;
    private TextView textViewEmptyState;

    private AlarmAdapter mListAdapter;

    public static AlarmActivityFragment newInstance() {
        return new AlarmActivityFragment();
    }

    public AlarmActivityFragment() {
    }


    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter != null)
            mPresenter.start();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListAdapter = new AlarmAdapter(new ArrayList<Alarms>(0), mItemListener);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_alarm, container, false);

        recyclerViewAlarms = (RecyclerView) view.findViewById(R.id.rv_alarms);
        recyclerViewAlarms.setAdapter(mListAdapter);
        recyclerViewAlarms.setLayoutManager(new LinearLayoutManager(getActivity()));

        textViewEmptyState = (TextView) view.findViewById(R.id.empty_state);

        mPresenter.getAlarmsList();

        return view;
    }

    @Override
    public void setPresenter(AlarmContract.Presenter presenter) {
        if (presenter != null)
            this.mPresenter = presenter;
    }

    AlarmAdapter.AlarmItemListener mItemListener = new AlarmAdapter.AlarmItemListener() {
        @Override
        public void onAlarmClick(Alarms clickedAlarm) {
            mPresenter.openAlarmDetails(clickedAlarm);
        }
    };

    @Override
    public void showEmptyState(boolean show) {
        if (show) {
            textViewEmptyState.setVisibility(View.VISIBLE);
            recyclerViewAlarms.setVisibility(View.GONE);
        } else {
            textViewEmptyState.setVisibility(View.GONE);
            recyclerViewAlarms.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setList(List<Alarms> dataList) {
        setListDataOnView(dataList);

    }

    private void setListDataOnView(List<Alarms> dataList) {
        if (dataList != null) {
            mListAdapter.updateListData(dataList);
            recyclerViewAlarms.setVisibility(View.VISIBLE);
            showEmptyState(false);
        }
    }
}
