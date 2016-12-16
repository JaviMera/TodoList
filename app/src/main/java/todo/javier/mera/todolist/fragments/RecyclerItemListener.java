package todo.javier.mera.todolist.fragments;

import java.util.List;

import todo.javier.mera.todolist.model.ItemBase;

/**
 * Created by javie on 12/8/2016.
 */

public interface RecyclerItemListener<T extends ItemBase> {

    void onClick(int position);
    void onLongClick(int position);
    void onItemsUpdate(List<T> items);
}
