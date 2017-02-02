package todo.javier.mera.todolist.adapters;

import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.fragments.FragmentRecycler;
import todo.javier.mera.todolist.model.TodoList;

/**
 * Created by javie on 11/30/2016.
 */

public class TodolistAdapter extends RecyclerAdapter<TodoList, TodolistViewHolder> {

    public TodolistAdapter(FragmentRecycler context) {

        super(context, TodolistViewHolder.class);
    }

    @Override
    protected int getLayout() {

        return R.layout.todo_list_item;
    }
}
