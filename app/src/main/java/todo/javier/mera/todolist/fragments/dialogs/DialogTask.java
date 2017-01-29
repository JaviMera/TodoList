package todo.javier.mera.todolist.fragments.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.fragments.FragmentTask;
import todo.javier.mera.todolist.model.PriorityUtil;
import todo.javier.mera.todolist.model.TaskPriority;

/**
 * Created by javie on 12/6/2016.
 */

public class DialogTask extends DialogBase
    implements DatePickerListener, TimePickerListener, PriorityListener, ReminderListener {

    private static final String DIALOG_TITLE = "Create new task!";
    private static final long EMPTY_DUE_TIME = 0L;

    private Date mDueDate;
    private long mDueTime;
    private DialogTaskListener mListener;
    private TaskPriority mPriority;
    private Date mReminderDate;
    private long mRemindertime;

    @BindView(R.id.dialogTitleView)
    TextView mTitleView;

    @BindView(R.id.addTaskView)
    ImageView mCheckImageView;

    @BindView(R.id.taskEditTextView)
    EditText mDescriptionEditText;

    @BindView(R.id.datePickerTextView)
    TextView mDateTextView;

    @BindView(R.id.timePickerTextView)
    TextView mTimeTextView;

    @BindView(R.id.priorityTextView)
    TextView mPriorityTextView;

    @BindView(R.id.reminderTextView)
    TextView mReminderTextView;

    @BindView(R.id.priorityMessageTextView)
    TextView mPriorityMessage;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mListener = (FragmentTask)getTargetFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPriority = TaskPriority.None;
        mDueTime = 0L;
        mReminderDate = null;
        mRemindertime = 0L;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = LayoutInflater
            .from(mParent)
            .inflate(R.layout.task_dialog, null
        );

        ButterKnife.bind(this, view);

        mTitleView.setText(DIALOG_TITLE);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mParent);
        dialogBuilder.setView(view);

        return createDialog(dialogBuilder);
    }

    @OnClick(R.id.addTaskView)
    public void onAddClick() {

        if(mDescriptionEditText.getText().toString().isEmpty()) {

            showToast("Task description cannot be blank.");
            mDescriptionEditText.startAnimation(mShakeAnimation);
            return;
        }

        if(mDueDate == null) {

            showToast("Task due date cannot be blank.");
            mDateTextView.startAnimation(mShakeAnimation);
            return;
        }

        if(mDueTime == EMPTY_DUE_TIME) {

            showToast("Task due time cannot be blank");
            mTimeTextView.startAnimation(mShakeAnimation);
            return;
        }

        // If edit text contains text, then add it and close the dialog
        mListener.onCreatedTask(
            mDescriptionEditText.getText().toString(),
            mDueDate,
            mDueTime,
            mReminderDate,
            mRemindertime,
            mPriority
        );

        dismiss();
    }

    @OnClick(R.id.datePickerTextView)
    public void onDateButtonClick(View view) {

        DatePickerDialog dialog = new DatePickerDialog();
        dialog.setTargetFragment(this, 1);
        dialog.show(mParent.getSupportFragmentManager(), "date_dialog");
    }

    @OnClick(R.id.timePickerTextView)
    public void onTimeClick(View view) {

        TimePickerDialog dialog = new TimePickerDialog();
        dialog.setTargetFragment(this, 1);
        dialog.show(mParent.getSupportFragmentManager(), "time_dialog");
    }

    @OnClick(R.id.priorityTextView)
    public void onPriorityClick(View view) {

        DialogPriority dialog = new DialogPriority();
        dialog.setTargetFragment(this, 1);
        dialog.show(mParent.getSupportFragmentManager(), "priority_dialog");
    }

    @OnClick(R.id.reminderTextView)
    public void onReminderClick(View view) {

        DialogReminder dialog = new DialogReminder();
        dialog.setTargetFragment(this, 1);
        dialog.show(mParent.getSupportFragmentManager(), "reminder_dialog");
    }

    @Override
    public void onDatePicked(Date date) {

        mDueDate = date;
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        String dateAsString = format.format(mDueDate);
        mDateTextView.setText(dateAsString);
    }

    @Override
    public void onPrioritySelected(int position) {

        mPriority = TaskPriority.values()[position];
        mPriorityTextView.setText(PriorityUtil.getName(mPriority.ordinal()));
        mPriorityMessage.setVisibility(View.GONE);
    }

    @Override
    public void onTimePicked(long time) {

        mDueTime = time;
        SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        String timeAsString = format.format(time);
        mTimeTextView.setText(timeAsString);
    }

    @Override
    public void onReminderSet(Date date, long time) {

        mReminderDate = date;
        mRemindertime = time;
        SimpleDateFormat format = new SimpleDateFormat("LLL, EEE dd  HH:mm");
        date.setTime(time);
        mReminderTextView.setText(format.format(date));
    }
}
