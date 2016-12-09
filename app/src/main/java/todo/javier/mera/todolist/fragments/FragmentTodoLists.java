package todo.javier.mera.todolist.fragments;

import android.content.ContentValues;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.Date;
import java.util.List;

import butterknife.OnClick;
import jp.wasabeef.recyclerview.animators.FlipInTopXAnimator;
import todo.javier.mera.todolist.adapters.RecyclerAdapter;
import todo.javier.mera.todolist.database.TodoListDataSource;
import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.adapters.TodolistAdapter;
import todo.javier.mera.todolist.database.TodoListSQLiteHelper;
import todo.javier.mera.todolist.fragments.dialogs.FragmentDialogTodoList;
import todo.javier.mera.todolist.model.TodoList;
import todo.javier.mera.todolist.model.TodoListTask;

public class FragmentTodoLists extends FragmentRecycler<TodoList> {

    public static FragmentTodoLists newInstance() {

        return new FragmentTodoLists();
    }

    @OnClick(R.id.fab)
    public void onAddListButtonClick(View view) {

        FragmentDialogTodoList dialogTodoList = new FragmentDialogTodoList();
        dialogTodoList.setTargetFragment(this, 1);
        dialogTodoList.show(mParent.getSupportFragmentManager(), "dialog_todolists");
    }

    @Override
    protected RecyclerAdapter getAdapter() {

        return new TodolistAdapter(this);
    }

    @Override
    protected String getTitle() {

        return "Home";
    }

    @Override
    protected int getDeleteTitle() {

        return R.string.menu_delete_todolist;
    }

    @Override
    protected int removeItems(List<TodoList> itemsToRemove) {

        TodoListDataSource source = new TodoListDataSource(mParent);
        source.openWriteable();

        TodoList[] items = itemsToRemove.toArray(new TodoList[itemsToRemove.size()]);
        int affectedRows = source.removeTodoLists(items);

        source.close();
        return affectedRows;
    }

    @Override
    protected void updateItems(List<TodoList> items) {

        TodoListDataSource source = new TodoListDataSource(mParent);
        source.openWriteable();

        ContentValues values = new ContentValues();
        for(TodoList todoList : items) {

            values.put(TodoListSQLiteHelper.COLUMN_TODO_LIST_POSITION, todoList.getPosition());

            source.update(
                TodoListSQLiteHelper.TABLE_TODO_LISTS,
                todoList.getId(),
                values
            );

            values.clear();
        }

        source.close();
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager(Context context) {

        int orientation = getOrientation(context);
        return new LinearLayoutManager(context, orientation, false);
    }

    @Override
    protected TodoList createItem(TodoListDataSource source, String name, int position) {

        setItemAnimator(new FlipInTopXAnimator());

        long creationDate = new Date().getTime();

        return source.createTodoList(
            name,
            creationDate,
            creationDate,
            position
        );
    }

    @Override
    protected List<TodoList> getAllItems() {

        TodoListDataSource source = new TodoListDataSource(mParent);
        source.openReadable();
        List<TodoList> todoLists = source.readTodoLists();
        source.close();

        return todoLists;
    }

    @Override
    protected void showItem(TodoList item) {

        mParent.showFragmentTodoList(item);
    }
}

