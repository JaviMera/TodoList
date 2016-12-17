package todo.javier.mera.todolist.ui;

import android.view.View;

import todo.javier.mera.todolist.model.TodoList;

/**
 * Created by javie on 12/2/2016.
 */
public interface MainActivityView {

    void setToolbarTitle(String text);
    void showFragmentTodoList(TodoList todoList);
    void updateToolbarBackground(int color);
    void hideViews();
    void showAddButton();
    void toggleBackButton(boolean canDisplay);
    void setToolbar();
    void setFabVisibility(int visibility);
}
