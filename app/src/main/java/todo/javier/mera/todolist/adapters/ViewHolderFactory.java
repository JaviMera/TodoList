package todo.javier.mera.todolist.adapters;

import android.support.v4.app.Fragment;
import android.view.View;

import todo.javier.mera.todolist.fragments.FragmentRecycler;

/**
 * Created by javie on 12/16/2016.
 */

public class ViewHolderFactory {

    private FragmentRecycler mFragment;
    private View mView;

    public ViewHolderFactory(FragmentRecycler fragment, View view) {

        mFragment = fragment;
        mView = view;
    }

    public ViewHolderBase getViewHolder(Class holderType) {

        if(holderType.equals(TodolistViewHolder.class)) {

            return new TodolistViewHolder(mFragment, mView);
        }
        else if(holderType.equals(TodoListTaskViewHolder.class)) {

            return new TodoListTaskViewHolder(mFragment, mView);
        }

        return null;
    }
}
