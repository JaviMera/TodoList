package todo.javier.mera.todolist.adapters;

import android.support.v4.app.Fragment;

import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.model.TodoList;

/**
 * Created by javie on 11/30/2016.
 */

public class TodolistAdapter extends RecyclerAdapter<TodoList, TodolistViewHolder> {

    public TodolistAdapter(Fragment context) {

        super(context, TodolistViewHolder.class);
    }

    @Override
    protected void removeItem(int position) {

        mItems.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    protected int getLayout() {

        return R.layout.todo_list_item;
    }

    @Override
    public void addItem(TodoList item) {

        mItems.add(item);
        notifyItemInserted(getItemCount() - 1);
    }
}
