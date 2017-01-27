package todo.javier.mera.todolist.adapters;

import android.view.View;

import todo.javier.mera.todolist.fragments.FragmentRecycler;

/**
 * Created by javie on 12/16/2016.
 */

class ViewHolderFactory {

    private FragmentRecycler mFragment;
    private View mView;

    ViewHolderFactory(FragmentRecycler fragment, View view) {

        mFragment = fragment;
        mView = view;
    }

    ViewHolderBase getViewHolder(Class holderType) {

        if(holderType.equals(TodolistViewHolder.class)) {

            return new TodolistViewHolder(mFragment, mView);
        }
        else if(holderType.equals(TaskViewHolder.class)) {

            return new TaskViewHolder(mFragment, mView);
        }

        return null;
    }
}
