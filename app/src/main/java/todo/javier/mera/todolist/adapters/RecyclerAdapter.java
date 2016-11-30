package todo.javier.mera.todolist.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.model.TodoList;

/**
 * Created by javie on 11/29/2016.
 */

public abstract class RecyclerAdapter<T, H extends ViewHolderBase<T>> extends RecyclerView.Adapter<H> {

    private final Context mContext;
    private final Class<H> mHolderType;

    protected List<T> mItems;

    protected abstract T getItem(int position);
    public abstract int  addItem(T item);

    public RecyclerAdapter(Context context, Class<H> holderType) {

        mContext = context;
        mHolderType = holderType;
        mItems = new LinkedList<>();
    }

    @Override
    public H onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.todo_list_item, parent, false);

        if(mHolderType.equals(TodolistViewHolderPortrait.class)) {

            return (H) new TodolistViewHolderPortrait(view);
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