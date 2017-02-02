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

    private DialogModifyTaskListener mListener;
    private Task mTask;

    public static DialogModifyTask newInstance(Task item) {

        DialogModifyTask dialogModifyTask = new DialogModifyTask();
        Bundle bundle = new Bundle();
        bundle.putParcelable("item", item);
        dialogModifyTask.setArguments(bundle);

        return dialogModifyTask;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mListener = (DialogModifyTaskListener)getTargetFragment();
    }

    @Override
    protected void saveItem() {

        mTask.setDescription(mEditText.getText().toString());
        mTask.setDueDate(mDueDate.getTime());

        if(mReminderTime != -1) {

            mTask.setReminder(getReminderDate(mReminderTime).getTime());
        }

        mTask.setPriority(mPriority);

        mListener.onUpdateItem(mTask);
    }

    @Override
    protected String getSaveText() {

        return "Update";
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        mTask = getArguments().getParcelable("item");
        mEditText.setText(mTask.getDescription());

        Date dueDate = new Date();
        dueDate.setTime(mTask.getDueDate());
        onDueDateSelected(dueDate);

        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.ENGLISH);

        if(mTask.getReminderDate() != 0L) {

            mReminderTextView.setText(format.format(mTask.getReminderDate()));
        }

        mPriorityTextView.setText(PriorityUtil.getName(mTask.getPriority().ordinal()));

        return dialog;
    }
}
