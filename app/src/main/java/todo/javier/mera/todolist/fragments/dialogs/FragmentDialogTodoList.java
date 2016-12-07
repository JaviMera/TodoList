package todo.javier.mera.todolist.fragments.dialogs;

import android.content.Context;

import todo.javier.mera.todolist.fragments.FragmentTasks;
import todo.javier.mera.todolist.fragments.FragmentTodoLists;

/**
 * Created by javie on 12/6/2016.
 */

public class FragmentDialogTodoList extends FragmentDialogBase {
    @Override
    protected String getTitle() {

        return "Create a new To-do list!";
    }

    @Override
    protected String getHint() {

        return "enter todo list";
    }

    @Override
    protected String getHintError() {

        return "To-do list name can't be blank";
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mListener = (FragmentTodoLists)getTargetFragment();
    }
}
