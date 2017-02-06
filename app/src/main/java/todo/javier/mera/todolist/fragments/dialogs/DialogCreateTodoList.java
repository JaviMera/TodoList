package todo.javier.mera.todolist.fragments.dialogs;

import android.content.Context;

/**
 * Created by javie on 2/1/2017.
 */

public class DialogCreateTodoList extends DialogEditTodoList {

    private DialogTodoListListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mListener = (DialogTodoListListener) context;
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

        return "create";
    }
}
