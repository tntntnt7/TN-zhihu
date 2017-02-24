package com.example.tntntnt.tn_zhihu.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import com.example.tntntnt.tn_zhihu.R;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by tntnt on 2017/2/24.
 */

public class DatePickerFragment extends DialogFragment {

    public static final String EXTRA_DATE = "com.example.tntntnt.tn_zhihu.ui.fragment.date";

    private DatePicker mDatePicker;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.am_date_picker, null);

        mDatePicker = (DatePicker)view.findViewById(R.id.date_picker);

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int year = mDatePicker.getYear();
                                int month = mDatePicker.getMonth();
                                int day = mDatePicker.getDayOfMonth();

                                Date date = new GregorianCalendar(year, month, day).getTime();
                                sendResuly(Activity.RESULT_OK, date);
                            }
                        })
                .create();
    }

    private void sendResuly(int resultCode, Date date){
        if (getTargetFragment() == null){
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, date);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
