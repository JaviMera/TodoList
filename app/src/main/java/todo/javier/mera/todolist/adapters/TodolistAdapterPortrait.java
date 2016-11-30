package todo.javier.mera.todolist.adapters;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

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
    protected void setAnimation(View itemView, int position, int i) {

        if(position == i) {

            ScaleAnimation animation = new ScaleAnimation(
                0.0f,
                1.0f,
                0.0f,
                1.0f,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);

            animation.setDuration(500);
            itemView.startAnimation(animation);
        }
    }

    @Override
    public void addItem(TodoList item) {

        mItems.add(item);
        notifyItemInserted(getItemCount() - 1);
    }
}
