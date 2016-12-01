package todo.javier.mera.todolist.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import jp.wasabeef.recyclerview.animators.FlipInTopXAnimator;
import jp.wasabeef.recyclerview.animators.SlideInRightAnimator;
import todo.javier.mera.todolist.MainActivity;
import todo.javier.mera.todolist.fragments.FragmentHome;
import todo.javier.mera.todolist.model.TodoList;

/**
 * Created by javie on 11/30/2016.
 */

public class TodolistAdapterPortrait extends RecyclerAdapter<TodoList, TodolistViewHolderPortrait> {

    public TodolistAdapterPortrait(Fragment context) {
        super(context, TodolistViewHolderPortrait.class);
    }

    @Override
    public TodoList getItem(int position) {

        return mItems.get(position);
    }

    @Override
    protected void removeItem(int position) {

        mItems.remove(position);
        ((FragmentHome)mContext).setItemAnimator(new SlideInRightAnimator());

        notifyItemRemoved(position);
    }

    @Override
    public void addItem(TodoList item) {

        mItems.add(item);
        ((FragmentHome)mContext).setItemAnimator(new FlipInTopXAnimator());

        notifyItemInserted(getItemCount() - 1);
    }
}
