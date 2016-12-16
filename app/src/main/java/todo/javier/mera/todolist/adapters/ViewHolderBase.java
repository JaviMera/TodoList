package todo.javier.mera.todolist.adapters;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.fragments.FragmentRecycler;

/**
 * Created by javie on 11/30/2016.
 */
public abstract class ViewHolderBase<T> extends RecyclerView.ViewHolder{

    FragmentRecycler mParent;

    public abstract void bind(T item);
    protected abstract void setViews();

    ViewHolderBase(FragmentRecycler fragment, View itemView) {
        super(itemView);

        mParent = fragment;
        setViews();
    }
}
