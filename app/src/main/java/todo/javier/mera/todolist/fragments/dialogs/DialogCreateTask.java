package todo.javier.mera.todolist.fragments.dialogs;

import android.content.Context;

import java.util.Calendar;
import java.util.Date;

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
}
