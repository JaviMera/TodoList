package todo.javier.mera.todolist.fragments;

import android.view.View;

import todo.javier.mera.todolist.model.TodoList;

/**
 * Created by javie on 12/2/2016.
 */

public interface TodoListListener {

    void onTodoListClick(TodoList todoList);
}
