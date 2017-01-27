package todo.javier.mera.todolist.fragments.dialogs;

import java.util.Date;

import todo.javier.mera.todolist.model.TaskPriority;

/**
 * Created by javie on 12/6/2016.
 */
public interface DialogTaskListener {

    void onCreatedTask(String title, Date dueDate, long dueTime, TaskPriority priority);
}
