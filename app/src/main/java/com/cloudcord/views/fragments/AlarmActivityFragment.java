package com.cloudcord.views.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cloudcord.R;
import com.cloudcord.datamodals.adapters.AlarmAdapter;
import com.cloudcord.presenters.AlarmContract;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_alarm, container, false);

        recyclerViewAlarms = (RecyclerView) view.findViewById(R.id.rv_alarms);
        textViewEmptyState = (TextView) view.findViewById(R.id.empty_state);


        return view;
    }

    @Override
    public void setPresenter(AlarmContract.Presenter presenter) {
        if (presenter != null)
            this.mPresenter = presenter;
    }
}
