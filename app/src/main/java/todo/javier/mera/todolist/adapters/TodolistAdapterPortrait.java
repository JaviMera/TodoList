package todo.javier.mera.todolist.adapters;

import android.content.Context;

import todo.javier.mera.todolist.model.TodoList;

/**
 * Created by javie on 11/30/2016.
 */

public class TodolistAdapterPortrait extends RecyclerAdapter<TodoList, TodolistViewHolderPortrait> {

    public TodolistAdapterPortrait(Context context) {
        super(context, TodolistViewHolderPortrait.class);
    }

    @Override
    public TodoList getItem(int position) {

        return mItems.get(position);
    }

    @Override
    public void addItem(TodoList item) {

        int currentSize = mItems.size();
        mItems.add(item);
        notifyItemInserted(currentSize);
    }
}
