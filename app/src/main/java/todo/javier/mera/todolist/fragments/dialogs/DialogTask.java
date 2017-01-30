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

public class DialogTask extends DialogCreate
    implements
    ReminderListener
    {

    private Date mReminderDate;

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

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mListener = (FragmentTask)getTargetFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mReminderDate = null;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog = super.onCreateDialog(savedInstanceState);

        mReminderTextView.setEnabled(false);

        return dialog;
    }

    @OnClick(R.id.createItemView)
    public void onAddClick() {

        // If edit text contains text, then add it and close the dialog
        mListener.onCreatedTask(
            mEditText.getText().toString(),
            mDueDate,
            mDueTime,
            mReminderDate,
            mPriority
        );

        dismiss();
    }

    @Override
    public void onDueDateSelected(Date date, long time) {
        super.onDueDateSelected(date, time);

        mReminderTextView.setEnabled(true);
    }

    @OnClick(R.id.reminderTextView)
    public void onReminderClick(View view) {

        DialogReminder dialogReminder = DialogReminder.newInstance(mDueDate);
        dialogReminder.setTargetFragment(this, 1);
        dialogReminder.show(mParent.getSupportFragmentManager(), "reminder_dialog");
    }

    @Override
    public void onReminderSelected(Date date) {

        mReminderDate = new Date();
        mReminderDate.setTime(date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("LLL, EEE dd  HH:mm");
        mReminderTextView.setText(format.format(date));
    }
}
