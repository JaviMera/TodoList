package todo.javier.mera.todolist.fragments;

import todo.javier.mera.todolist.model.TaskPriority;

/**
 * Created by javie on 12/10/2016.
 */
public interface ItemTaskListener {

    void onStatusUpdate(int position, boolean isCompleted);
}