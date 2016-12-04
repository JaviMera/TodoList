package todo.javier.mera.todolist.ui;

import android.view.View;

import todo.javier.mera.todolist.model.TodoList;

/**
 * Created by javie on 12/2/2016.
 */
public interface ParentView {

    void hideKeyboard();
    void setToolbarTitle(String text);
    void showFragmentTodoList(TodoList todoList);
}