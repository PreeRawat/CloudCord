package com.cloudcord.utils;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by root on 22/3/17.
 */

public class DateTimePickers {


    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        public Interactor mInteractor;

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            try {
                this.mInteractor = (Interactor) context;
            } catch (final ClassCastException e) {
                throw new ClassCastException(context.toString() + " must implement Interactor");
            }
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            mInteractor.getTime(hourOfDay, minute);
        }

        @Override
        public void onCancel(DialogInterface dialog) {
            super.onCancel(dialog);
            mInteractor.getTime(0, 0);
        }
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        public Interactor mInteractor;

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            try {
                this.mInteractor = (Interactor) context;
            } catch (final ClassCastException e) {
                throw new ClassCastException(context.toString() + " must implement Interactor");
            }
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            mInteractor.getDate(year, month, day);
        }

        @Override
        public void onCancel(DialogInterface dialog) {
            super.onCancel(dialog);
            mInteractor.getDate(0, 0, 0);
        }
    }

    /*public static class CustomRepetitionDialog extends DialogFragment {

        public Interactor mInteractor;

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            try {
                this.mInteractor = (Interactor) context;
            } catch (final ClassCastException e) {
                throw new ClassCastException(context.toString() + " must implement Interactor");
            }
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            // Get the layout inflater
            LayoutInflater inflater = getActivity().getLayoutInflater();

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(inflater.inflate(R.layout.dialog_signin, null))
                    // Add action buttons
                    .setPositiveButton(R.string.signin, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            // sign in the user ...
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            LoginDialogFragment.this.getDialog().cancel();
                        }
                    });
            return builder.create();
        }


        @Override
        public void onCancel(DialogInterface dialog) {
            super.onCancel(dialog);
            mInteractor.getDate(0, 0, 0);
        }
    }*/
}
