package todo.javier.mera.todolist.fragments.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Calendar;

/**
 * Created by javie on 2/1/2017.
 */

public class DialogCreateTask extends DialogEditTask {


    private DialogCreateTaskListener mListener;

    public static DialogCreateTask newInstance() {

        DialogCreateTask dialogEditTask = new DialogCreateTask();
        return dialogEditTask;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("reminder", mReminderTime);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mListener = (DialogCreateTaskListener)getTargetFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    protected void saveItem() {

        long reminderDate = 0L;

        if(mReminderTime != -1) {

            Calendar c = Calendar.getInstance();
            c.setTime(mDueDate);
            int reminderMinutes = reminderOptions.get(mReminderTime);
            c.add(Calendar.MINUTE, reminderMinutes);
            reminderDate = c.getTime().getTime();
        }

        mListener.onCreatedTask(
            mEditText.getText().toString(),
            mDueDate,
            reminderDate,
            mPriority
        );
    }

    @Override
    protected String getSaveText() {

        return "create";
    }
}
