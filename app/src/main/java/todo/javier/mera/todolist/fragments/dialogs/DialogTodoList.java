package todo.javier.mera.todolist.fragments.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.fragments.FragmentTodoList;
import todo.javier.mera.todolist.model.Priority;
import todo.javier.mera.todolist.model.PriorityUtil;

/**
 * Created by javie on 12/6/2016.
 */

public class DialogTodoList extends DialogCreate {

    private DialogTodoListListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mListener = (FragmentTodoList) getTargetFragment();
    }

    @Override
    protected String getTitle() {

        return "Create a new list!";
    }

    @Override
    protected View getLayout() {

        View view = LayoutInflater
            .from(mParent)
            .inflate(R.layout.todo_list_dialog, null
            );

        return view;
    }

    @Override
    protected void createItem() {

        mListener.onCreateTodoList(
            mEditText.getText().toString(),
            mDueDate,
            mPriority);
    }
}
