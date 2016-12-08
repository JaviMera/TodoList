package todo.javier.mera.todolist.adapters;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import todo.javier.mera.todolist.fragments.FragmentRecycler;
import todo.javier.mera.todolist.fragments.FragmentTasks;
import todo.javier.mera.todolist.fragments.TodoListListener;
import todo.javier.mera.todolist.model.ItemBase;

/**
 * Created by javie on 11/29/2016.
 */

public abstract class RecyclerAdapter<T extends ItemBase, H extends ViewHolderBase<T>>
    extends RecyclerView.Adapter<H>
    implements ItemTouchHelperAdapter
    {

    protected final Fragment mFragment;
    private final Class<H> mHolderType;

    protected List<T> mItems;

    protected abstract void removeItem(int position);
    protected abstract int getLayout();

    public T getItem(int position) {

        return mItems.get(position);
    }

    public void addItem(T item) {

        mItems.add(item);
        notifyItemInserted(mItems.size() - 1);
    }

    public void setRemovable(int itemLongClicked) {

        T item = mItems.get(itemLongClicked);

        // This will work as a toggle for can or can't be removed
        // The item will start as non removable, so the first click will make it removable, the next
        // will set it back to non removable and so on.
        boolean isRemovable = !item.getCanRemove();

        item.setCanRemove(isRemovable);
        notifyItemChanged(itemLongClicked);
    }

    public void clearRemovableItems() {

        for(int i = 0 ; i < mItems.size() ; i++) {

            mItems.get(i).setCanRemove(false);
        }

        notifyItemRangeChanged(0, mItems.size());
    }

    public RecyclerAdapter(Fragment fragment, Class<H> holderType) {

        mFragment = fragment;
        mHolderType = holderType;
        mItems = new LinkedList<>();
    }

    @Override
    public H onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater
            .from(mFragment.getActivity())
            .inflate(getLayout(), parent, false);

        if(mHolderType.equals(TodolistViewHolder.class)) {

            return (H) new TodolistViewHolder((FragmentRecycler) mFragment, view);
        }
        else if(mHolderType.equals(TodoListTaskViewHolder.class)) {

            return (H) new TodoListTaskViewHolder(view, (FragmentRecycler) mFragment);
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

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {

        if(fromPosition < toPosition) {

            for(int i = fromPosition ; i < toPosition ; i++) {

                Collections.swap(mItems, i, i + 1);
            }
        }
        else {

            for(int i = fromPosition ; i > toPosition ; i--) {

                Collections.swap(mItems, i, i - 1);
            }
        }

        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {

        mItems.remove(position);
        notifyItemRemoved(position);
    }

    public int getRemovableCount() {

    int count = 0;

    for(T item : mItems) {

        if(item.getCanRemove()) {

            count++;
        }
    }

    return count;
}

    public List getRemovableItems() {

        List<T> items = new LinkedList<>();

        for(T item : mItems) {

            if(item.getCanRemove()) {

                items.add(item);
            }
        }

        return items;
    }

    public void removeItems(List<T> itemsToRemove) {

        for(T item : itemsToRemove) {

            int position = mItems.indexOf(item);
            if(position != -1) {

                mItems.remove(position);
                notifyItemRemoved(position);
            }
        }
    }

    public void addItems(List<T> items) {

        mItems.addAll(items);
        notifyItemRangeInserted(0, mItems.size());
    }
}