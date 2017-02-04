package todo.javier.mera.todolist.fragments.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import todo.javier.mera.todolist.model.PriorityUtil;
import todo.javier.mera.todolist.model.Task;

/**
 * Created by javie on 2/1/2017.
 */
public class DialogModifyTask extends DialogEditTask {

    private static final String TASK_SELECTED = "item";
    public static final long EMPTY_REMINDER = 0L;
    private DialogModifyListener mListener;
    private Task mTask;

    public static DialogModifyTask newInstance(Task item) {

        DialogModifyTask dialogModifyTask = new DialogModifyTask();
        Bundle bundle = new Bundle();
        bundle.putParcelable(TASK_SELECTED, item);
        dialogModifyTask.setArguments(bundle);

        return dialogModifyTask;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mListener = (DialogModifyListener)getTargetFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        mTask = getArguments().getParcelable(TASK_SELECTED);

        if(savedInstanceState == null) {

            // Set the description of the task selected
            setDescriptionText(mTask.getDescription());

            Date dueDate = new Date();
            dueDate.setTime(mTask.getDueDate());

            // Set the due date of the task selected
            onDueDateSelected(dueDate);

            // Set the reminder of the task selected, if there is any
            if(mTask.getReminderDate() != EMPTY_REMINDER) {

                setReminderText(mFormatter.format(mTask.getReminderDate()));
            }

            // Set the priority of the task selected
            mPriority = mTask.getPriority();
            setPriorityText(mTask.getPriority().ordinal());
        }

        return dialog;
    }

    @Override
    protected void saveItem() {

        mTask.setDescription(mEditText.getText().toString());
        mTask.setDueDate(mDueDate.getTime());

        if(mReminderTime != -1) {

            mTask.setReminder(getReminderDate(mReminderTime).getTime());
        }
        else {

            mTask.setReminder(EMPTY_REMINDER);
        }

        mTask.setPriority(mPriority);

        mListener.onModifyItem(mTask);
    }

    @Override
    protected String getSaveText() {

        return "Update";
    }
}
