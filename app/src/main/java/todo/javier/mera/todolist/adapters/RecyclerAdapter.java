package todo.javier.mera.todolist.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import todo.javier.mera.todolist.fragments.FragmentRecycler;
import todo.javier.mera.todolist.model.ItemBase;
import todo.javier.mera.todolist.model.TodoList;

/**
 * Created by javie on 11/29/2016.
 */

public abstract class RecyclerAdapter<T extends ItemBase, H extends ViewHolderBase<T>>
    extends RecyclerView.Adapter<H>
    implements ItemTouchHelperAdapter
    {

    private final FragmentRecycler mFragment;
    private final Class<H> mHolderType;
    protected List<T> mItems;

    protected abstract int getLayout();

    public T getItem(int position) {

        return mItems.get(position);
    }

    public void setRemovable(int position, boolean isRemovable) {

        T item = mItems.get(position);
        item.setCanRemove(isRemovable);
        notifyItemChanged(position);
    }

    public void resetItems() {

        for(int i = 0 ; i < mItems.size() ; i++) {

            mItems.get(i).setCanRemove(false);
            notifyItemChanged(i);
        }
    }

    RecyclerAdapter(FragmentRecycler fragment, Class<H> holderType) {

        mFragment = fragment;
        mHolderType = holderType;
        mItems = new LinkedList<>();
    }

    @Override
    public H onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater
            .from(mFragment.getActivity())
            .inflate(getLayout(), parent, false);

        ViewHolderFactory factory = new ViewHolderFactory(mFragment, view);

        return (H) factory.getViewHolder(mHolderType);
    }

    @Override
    public int getItemCount() {

        return mItems.size();
    }

    @Override
    public void onBindViewHolder(final H holder, int position) {

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

    @Override
    public void onItemDropped(int newPosition) {

        T item = getItem(newPosition);
        item.setMoving(false);
        notifyItemChanged(newPosition);

        Map<String, Integer> items = new LinkedHashMap<>();
        for(int position = 0 ; position < mItems.size() ; position++) {

            items.put(mItems.get(position).getId(), position);
        }

        mFragment.onItemsUpdate(items);
        notifyItemChanged(newPosition);
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

    public void removeItems() {

        for(int position = 0 ; position < mItems.size() ; position++){

            if(getItem(position).getCanRemove()) {

                mItems.remove(position);
                notifyItemRemoved(position);
                position--;
            }
        }
    }

    public void addItems(List<T> items) {

        mItems.addAll(items);
        notifyItemRangeInserted(0, mItems.size());
    }

    public void notifyItemMove(int position) {

        T item = getItem(position);
        item.setMoving(true);
        notifyItemChanged(position);
    }

    public void removeAll() {

        int size = mItems.size();
        mItems.clear();
        notifyItemRangeRemoved(0, size);
    }

    public void addItem(Integer position, T item) {

        mItems.add(position, item);
        item.setCanRemove(false);
        notifyItemInserted(position);
    }

    public List<T> getItems() {

        return mItems;
    }

    public boolean isEmpty() {

        return mItems.isEmpty();
    }

    public void updateItem(T item) {

        int position = mItems.indexOf(item);
        mItems.set(position, item);
        notifyItemChanged(position);
    }
}