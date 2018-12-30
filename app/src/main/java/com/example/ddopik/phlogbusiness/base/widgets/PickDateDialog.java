package com.example.ddopik.phlogbusiness.base.widgets;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import com.example.ddopik.phlogbusiness.R;

import java.util.Calendar;

/**
 * Created by Ayman Abouzeid on 11/16/17.
 */

public class PickDateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private OnDateSet onDateSet;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), R.style.DatePickerTheme, this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        onDateSet.onDateSet(year, month, day);
    }

    public void setOnDateSet(OnDateSet onDateSet) {
        this.onDateSet = onDateSet;
    }

    public interface OnDateSet {
        void onDateSet(int year, int month, int day);
    }
}