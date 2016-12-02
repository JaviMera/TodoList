package todo.javier.mera.todolist.adapters;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.fragments.FragmentHome;
import todo.javier.mera.todolist.fragments.TodoListListener;

/**
 * Created by javie on 11/29/2016.
 */

public abstract class RecyclerAdapter<T, H extends ViewHolderBase<T>> extends RecyclerView.Adapter<H> {

    protected final Fragment mFragment;
    private final Class<H> mHolderType;

    protected List<T> mItems;

    protected abstract T getItem(int position);
    protected abstract void removeItem(int position);

    public abstract void addItem(T item);


    public RecyclerAdapter(Fragment fragment, Class<H> holderType) {

        mFragment = fragment;
        mHolderType = holderType;
        mItems = new LinkedList<>();
    }

    @Override
    public H onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mFragment.getActivity()).inflate(R.layout.todo_list_item, parent, false);

        if(mHolderType.equals(TodolistViewHolder.class)) {

            return (H) new TodolistViewHolder((TodoListListener) mFragment, view);
        }

        return null;
    }

    @Override
    public int getItemCount() {

        return mItems.size();
    }

    @Override
    public void onBindViewHolder(H holder, int position) {

        holder.bind(mItems.get(position));
    }
}