package todo.javier.mera.todolist.fragments.dialogs;

import todo.javier.mera.todolist.model.ItemBase;
import todo.javier.mera.todolist.model.Priority;
import todo.javier.mera.todolist.model.TodoList;

/**
 * Created by javie on 2/1/2017.
 */
public interface DialogModifyListener<T extends ItemBase> {

    void onModifyItem(T item);
    void onNavigateClick(int position);
}
