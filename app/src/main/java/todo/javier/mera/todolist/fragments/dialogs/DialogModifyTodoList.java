package todo.javier.mera.todolist.fragments.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import java.util.Date;

import todo.javier.mera.todolist.model.PriorityUtil;
import todo.javier.mera.todolist.model.TodoList;

/**
 * Created by javie on 2/1/2017.
 */

public class DialogModifyTodoList extends DialogEditTodoList {

    private TodoList mTodoList;
    private DialogModifyTodoListListener mListener;

    public static DialogModifyTodoList newInstance(TodoList todoList) {

        DialogModifyTodoList dialogModifyTodoList = new DialogModifyTodoList();
        Bundle bundle = new Bundle();
        bundle.putParcelable("item", todoList);
        dialogModifyTodoList.setArguments(bundle);

        return dialogModifyTodoList;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mListener = (DialogModifyTodoListListener) getTargetFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog = super.onCreateDialog(savedInstanceState);

        mTodoList = getArguments().getParcelable("item");
        mEditText.setText(mTodoList.getDescription());

        Date dueDate = new Date();
        dueDate.setTime(mTodoList.getDueDate());
        onDueDateSelected(dueDate);

        mPriorityTextView.setText(PriorityUtil.getName(mTodoList.getPriority().ordinal()));
        return dialog;
    }

    @Override
    protected void saveItem() {

        mTodoList.setDescription(mEditText.getText().toString());
        mTodoList.setDueDate(mDueDate.getTime());
        mTodoList.setPriority(mPriority);
        mListener.onModifyTodoList(mTodoList);
    }
}
