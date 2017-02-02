package todo.javier.mera.todolist.fragments.dialogs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.fragments.FragmentTodoList;

/**
 * Created by javie on 12/6/2016.
 */

public abstract class DialogEditTodoList extends DialogEdit {


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
}
