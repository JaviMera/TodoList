package todo.javier.mera.todolist.fragments.dialogs;

import todo.javier.mera.todolist.model.Priority;

/**
 * Created by javie on 2/1/2017.
 */
public interface DialogModifyTodoListListener {

    void onModifyTodoList(String id, String description, long dueDate, Priority priority);
}
