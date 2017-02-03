package todo.javier.mera.todolist.fragments.dialogs.create;

import android.content.Context;

import todo.javier.mera.todolist.fragments.FragmentTodoList;
import todo.javier.mera.todolist.fragments.dialogs.DialogEditTodoList;

/**
 * Created by javie on 2/1/2017.
 */

public class DialogCreateTodoList extends DialogEditTodoList {

    private DialogTodoListListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mListener = (DialogTodoListListener) getTargetFragment();
    }

    @Override
    protected String getTitle() {

        return "Create a new list!";
    }

    @Override
    protected void saveItem() {

        mListener.onCreateTodoList(
            mEditText.getText().toString(),
            mDueDate,
            mPriority);
    }

    @Override
    protected String getSaveText() {

        return "Create";
    }
}
