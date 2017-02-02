package todo.javier.mera.todolist.fragments.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import java.util.Date;

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

        mListener.onUpdateItem(
            mTask.getTodoListId(),
            mTask.getId(),
            mEditText.getText().toString()
        );
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

        mPriorityTextView.setText(PriorityUtil.getName(mTask.getPriority().ordinal()));
        return dialog;
    }
}
