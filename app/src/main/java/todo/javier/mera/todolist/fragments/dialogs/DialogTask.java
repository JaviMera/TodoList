package todo.javier.mera.todolist.fragments.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.fragments.FragmentTask;

/**
 * Created by javie on 12/6/2016.
 */

public class DialogTask extends DialogCreate
    implements
    ReminderListener
    {

    private int mReminderTime;

    private static Map<Integer, Integer> reminderOptions = new LinkedHashMap<Integer, Integer>() {
        {
            put(R.id.fiveBeforeButton, -5);
            put(R.id.tenBeforeButton, -10);
            put(R.id.twentyBeforeButton, -20);
            put(R.id.thirtyBeforeButton, -30);
            put(R.id.hourBeforeButton, -60);
        }
    };

    private DialogTaskListener mListener;

    @BindView(R.id.reminderTextView)
    TextView mReminderTextView;

    @Override
    protected String getTitle() {

        return "Create new task!";
    }

    @Override
    protected View getLayout() {

        View view = LayoutInflater.from(mParent).inflate(R.layout.task_dialog, null);
        return view;
    }

    @Override
    protected void createItem() {

        Date reminderDate = null;
        if(mReminderTime != -1) {

            Calendar c = Calendar.getInstance();
            c.setTime(mDueDate);
            int reminderMinutes = reminderOptions.get(mReminderTime);
            c.add(Calendar.MINUTE, reminderMinutes);
            reminderDate = c.getTime();
        }

        mListener.onCreatedTask(
            mEditText.getText().toString(),
            mDueDate,
            mDueTime,
            reminderDate,
            mPriority
        );
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mListener = (FragmentTask)getTargetFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mReminderTime = -1;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog = super.onCreateDialog(savedInstanceState);

        mReminderTextView.setEnabled(false);

        return dialog;
    }

    @Override
    public void onDueDateSelected(Date date, long time) {
        super.onDueDateSelected(date, time);

        mReminderTextView.setEnabled(true);
        if(mReminderTime != -1) {

            Date reminder = getReminderDate(mReminderTime);
            SimpleDateFormat format = new SimpleDateFormat("LLL, EEE dd  HH:mm");

            mReminderTextView.setText(format.format(reminder));
        }
    }

    @OnClick(R.id.reminderTextView)
    public void onReminderClick(View view) {

        DialogReminder dialogReminder = DialogReminder.newInstance();
        dialogReminder.setTargetFragment(this, 1);
        dialogReminder.show(mParent.getSupportFragmentManager(), "reminder_dialog");
    }

    @Override
    public void onReminderSelected(int buttonId) {

        mReminderTime = buttonId;
        Date reminder = getReminderDate(mReminderTime);
        SimpleDateFormat format = new SimpleDateFormat("LLL, EEE dd  HH:mm");
        mReminderTextView.setText(format.format(reminder));
    }

    private Date getReminderDate(int buttonId) {

        Calendar c = Calendar.getInstance();
        c.setTime(mDueDate);
        int reminderMinutes = reminderOptions.get(buttonId);
        c.add(Calendar.MINUTE, reminderMinutes);

        return c.getTime();
    }
}
