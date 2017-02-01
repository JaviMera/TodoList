package todo.javier.mera.todolist.fragments.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.media.MediaDescriptionCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import todo.javier.mera.todolist.R;

/**
 * Created by javie on 1/28/2017.
 */

public class DialogDueDate extends DialogBase
    implements
    DatePickerListener,
    TimePickerListener{

    private Date mDate;

    private DueDateListener mListener;

    @BindView(R.id.dateTextView)
    TextView mDateTextView;

    @BindView(R.id.timeTextView)
    TextView mTimeTextView;

    @BindView(R.id.setDueDateTextView)
    TextView mSetTextView;
    private int mHour;
    private int mMinute;

    public static DialogDueDate newInstance() {

        DialogDueDate dialog = new DialogDueDate();
        return dialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mListener = (DueDateListener) getTargetFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = LayoutInflater.from(mParent).inflate(R.layout.due_date_layout, null);
        ButterKnife.bind(this, view);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setView(view);

        return dialogBuilder.create();
    }

    @OnClick(R.id.dateTextView)
    public void onDateClick(View view) {

        DatePickerDialog dialog = new DatePickerDialog();
        dialog.setTargetFragment(this, 1);
        dialog.show(mParent.getSupportFragmentManager(), "date_dialog");
    }

    @OnClick(R.id.timeTextView)
    public void onTimeClick(View view) {

        TimePickerDialog dialog = new TimePickerDialog();
        dialog.setTargetFragment(this, 1);
        dialog.show(mParent.getSupportFragmentManager(), "time_dialog");
    }

    @OnClick(R.id.setDueDateTextView)
    public void onAddReminderClick(View view) {

        Calendar c = Calendar.getInstance();
        c.setTime(mDate);
        c.set(Calendar.HOUR_OF_DAY, mHour);
        c.set(Calendar.MINUTE, mMinute);

        mListener.onDueDateSelected(c.getTime());
        dismiss();
    }

    @Override
    public void onDatePicked(Date date) {

        mDate = date;
        SimpleDateFormat format = new SimpleDateFormat("LLL, EEE dd", Locale.ENGLISH);
        mDateTextView.setText(format.format(date));
    }

    @Override
    public void onTimePicked(int hour, int minute) {

        mHour = hour;
        mMinute = minute;

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);

        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        mTimeTextView.setText(format.format(c.getTime()));
    }
}
