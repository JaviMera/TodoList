package todo.javier.mera.todolist.fragments.dialogs.create;

import android.content.Context;

import java.util.Calendar;

import todo.javier.mera.todolist.fragments.dialogs.DialogEditTask;

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
    public void onAttach(Context context) {
        super.onAttach(context);

        mListener = (DialogCreateTaskListener)getTargetFragment();
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

        return "Create";
    }
}
