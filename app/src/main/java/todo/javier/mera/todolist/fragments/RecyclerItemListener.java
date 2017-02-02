package todo.javier.mera.todolist.fragments;

import java.util.Map;

import todo.javier.mera.todolist.model.ItemBase;

/**
 * Created by javie on 12/8/2016.
 */

interface RecyclerItemListener<T extends ItemBase> {

    void onClick(int position);
    void onNavigateClick(int position);
    void onLongClick(int position);
    void onItemsUpdate(Map<String, Integer> items);
}
