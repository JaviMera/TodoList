package todo.javier.mera.todolist.fragments.dialogs;

import android.content.Context;

import todo.javier.mera.todolist.fragments.FragmentTodoList;

/**
 * Created by javie on 2/1/2017.
 */

public class DialogCreateTodoList extends DialogEditTodoList {

    private DialogTodoListListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mListener = (FragmentTodoList) getTargetFragment();
    }

    @Override
    protected void saveItem() {

        mListener.onCreateTodoList(
            mEditText.getText().toString(),
            mDueDate,
            mPriority);
    }
}
