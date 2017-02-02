package todo.javier.mera.todolist.fragments.dialogs;

import java.util.Date;

import todo.javier.mera.todolist.model.Priority;

/**
 * Created by javie on 12/6/2016.
 */
public interface DialogCreateTaskListener {

    void onCreatedTask(
        String title,
        Date dueDate,
        long dueTime,
        long reminderDate,
        Priority priority);
}
