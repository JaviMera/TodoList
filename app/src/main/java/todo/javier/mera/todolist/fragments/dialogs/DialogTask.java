package todo.javier.mera.todolist.fragments.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
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
import todo.javier.mera.todolist.model.Priority;

/**
 * Created by javie on 12/6/2016.
 */

public class DialogTask extends DialogBase
    implements
    PriorityListener,
    DueDateListener,
    ReminderListener
    {

    private static final String DIALOG_TITLE = "Create new task!";
    private static final long EMPTY_DUE_TIME = 0L;

    private Date mDueDate;
    private long mDueTime;
    private Priority mPriority;
    private Date mReminderDate;
    private long mRemindertime;

    private DialogTaskListener mListener;

    @BindView(R.id.dialogTitleView)
    TextView mTitleView;

    @BindView(R.id.addTaskView)
    ImageView mCheckImageView;

    @BindView(R.id.taskEditTextView)
    EditText mDescriptionEditText;

    @BindView(R.id.dueDateTextView)
    TextView mDueDateTextView;

    @BindView(R.id.priorityTextView)
    TextView mPriorityTextView;

    @BindView(R.id.priorityMessageTextView)
    TextView mPriorityMessage;

    @BindView(R.id.reminderTextView)
    TextView mReminderTextView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mListener = (FragmentTask)getTargetFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPriority = Priority.None;
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

        mReminderTextView.setEnabled(false);

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
            mDueDateTextView.startAnimation(mShakeAnimation);
            return;
        }

        // If edit text contains text, then add it and close the dialog
        mListener.onCreatedTask(
            mDescriptionEditText.getText().toString(),
            mDueDate,
            mDueTime,
            mReminderDate,
            mPriority
        );

        dismiss();
    }

    @OnClick(R.id.dueDateTextView)
    public void onDateButtonClick(View view) {

        DialogDueDate dialog = DialogDueDate.newInstance();
        dialog.setTargetFragment(this, 1);
        dialog.show(mParent.getSupportFragmentManager(), "date_dialog");
    }

    @OnClick(R.id.reminderTextView)
    public void onReminderClick(View view) {

        DialogReminder dialogReminder = DialogReminder.newInstance(mDueDate);
        dialogReminder.setTargetFragment(this, 1);
        dialogReminder.show(mParent.getSupportFragmentManager(), "reminder_dialog");
    }

    @OnClick(R.id.priorityTextView)
    public void onPriorityClick(View view) {

        DialogPriority dialog = new DialogPriority();
        dialog.setTargetFragment(this, 1);
        dialog.show(mParent.getSupportFragmentManager(), "priority_dialog");
    }

    @Override
    public void onPrioritySelected(int position) {

        mPriority = Priority.values()[position];
        mPriorityTextView.setText(PriorityUtil.getName(mPriority.ordinal()));
        mPriorityMessage.setVisibility(View.GONE);
    }

    @Override
    public void onDueDateSelected(Date date, long time) {

        mDueDate = date;
        mDueTime = time;
        mDueDate.setTime(time);
        SimpleDateFormat format = new SimpleDateFormat("LLL, EEE dd  HH:mm");

        mDueDateTextView.setText(format.format(mDueDate));
        mReminderTextView.setEnabled(true);
    }

    @Override
    public void onReminderSelected(Date date) {

        mReminderDate = new Date();
        mReminderDate.setTime(date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("LLL, EEE dd  HH:mm");
        mReminderTextView.setText(format.format(date));
    }
}
