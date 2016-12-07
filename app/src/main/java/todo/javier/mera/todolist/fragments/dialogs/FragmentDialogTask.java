package todo.javier.mera.todolist.fragments.dialogs;

import android.content.Context;

import todo.javier.mera.todolist.fragments.FragmentTasks;

/**
 * Created by javie on 12/6/2016.
 */

public class FragmentDialogTask extends FragmentDialogBase {

    @Override
    protected String getTitle() {

        return "Create a new Task!";
    }

    @Override
    protected String getHint() {

        return "enter task";
    }

    @Override
    protected String getHintError() {

        return "task description can't be blank";
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (FragmentTasks)getTargetFragment();
    }
}
