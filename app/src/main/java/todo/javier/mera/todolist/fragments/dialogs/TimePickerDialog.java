package todo.javier.mera.todolist.fragments.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import todo.javier.mera.todolist.R;

/**
 * Created by javie on 1/27/2017.
 */

public class TimePickerDialog extends DialogBase {

    private TimePickerListener mListener;

    @BindView(R.id.timePickerView)
    TimePicker mTimePicker;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mListener = (TimePickerListener) getTargetFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.time_picker_dialog, null);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setView(view);

        ButterKnife.bind(this, view);

        return createDialog(dialogBuilder.create());
    }

    @OnClick(R.id.addTimeView)
    public void onTimeSelected(View view) {

        Calendar currentTime = Calendar.getInstance();
        int hour;
        int minute;

        if(Build.VERSION.SDK_INT >= 23) {

            hour = mTimePicker.getHour();
            minute = mTimePicker.getMinute();
        }
        else {

            hour = mTimePicker.getCurrentHour();
            minute = mTimePicker.getCurrentMinute();
        }
        currentTime.set(Calendar.HOUR_OF_DAY, hour);
        currentTime.set(Calendar.MINUTE, minute);

        // The first call to getTime gets us the date
        // The second call to getTime gets us the date as a long type along with the time
        mListener.onTimePicked(
            currentTime
                .getTime()
                .getTime()
        );

        dismiss();
    }
}
