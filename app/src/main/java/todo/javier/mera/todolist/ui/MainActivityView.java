package todo.javier.mera.todolist.ui;

import java.util.List;
import java.util.Map;

import todo.javier.mera.todolist.model.ItemBase;
import todo.javier.mera.todolist.model.TodoList;

/**
 * Created by javie on 12/2/2016.
 */
public interface MainActivityView {

    void setToolbarTitle(String text);
    void showFragmentTodoList(TodoList todoList);
    void updateToolbarBackground(int color);
    void hideFabButton();
    void showFabButton();
    void toggleBackButton(boolean canDisplay);
    void setToolbar();
    void setFabVisibility(int visibility);
    void showSnackBar(String message, String action, Map<Integer, ItemBase> items);
}
