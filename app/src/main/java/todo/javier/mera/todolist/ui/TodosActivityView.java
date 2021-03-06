package todo.javier.mera.todolist.ui;

import android.view.View;

import java.util.Map;

import todo.javier.mera.todolist.model.ItemBase;
import todo.javier.mera.todolist.model.Task;
import todo.javier.mera.todolist.model.TodoList;

/**
 * Created by javie on 12/2/2016.
 */
public interface TodosActivityView {

    void setToolbarTitle(String text);
    void showFragmentTodoList(TodoList todoList);
    void updateToolbarBackground(int color);
    void toggleBackButton(boolean canDisplay);
    void setToolbar();
    void showSnackBar(String message, String action, Map<Integer, ItemBase> items);
    void setIndicator(int resourceId);
    void cancelReminder(Task task);
    void createReminder(Task task);
}
