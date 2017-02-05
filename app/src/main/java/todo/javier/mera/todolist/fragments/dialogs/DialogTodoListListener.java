package todo.javier.mera.todolist.fragments.dialogs;

import java.util.Date;

import todo.javier.mera.todolist.model.Priority;

/**
 * Created by javie on 12/9/2016.
 */
public interface DialogTodoListListener {

    void onCreateTodoList(String name, Date dueDate, Priority priority);
    void onNavigateClick(int position);
}
