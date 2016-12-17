package todo.javier.mera.todolist.fragments;

import android.content.ContentValues;
import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import jp.wasabeef.recyclerview.animators.FlipInTopXAnimator;
import todo.javier.mera.todolist.adapters.RecyclerAdapter;
import todo.javier.mera.todolist.database.TodoListDataSource;
import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.adapters.TodolistAdapter;
import todo.javier.mera.todolist.database.TodoListSQLiteHelper;
import todo.javier.mera.todolist.fragments.dialogs.DialogTodoList;
import todo.javier.mera.todolist.fragments.dialogs.DialogTodoListListener;
import todo.javier.mera.todolist.model.TodoList;

public class FragmentTodoList extends FragmentRecycler<TodoList>
    implements DialogTodoListListener {

    public static FragmentTodoList newInstance() {

        return new FragmentTodoList();
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

        TodoList[] items = itemsToRemove.toArray(new TodoList[itemsToRemove.size()]);
        int affectedRows = source.removeTodoLists(items);

        return affectedRows;
    }

    @Override
    protected void onUpdatePosition(List<TodoList> items) {

        TodoListDataSource source = new TodoListDataSource(mParent);

        ContentValues values = new ContentValues();
        for(TodoList todoList : items) {

            values.put(TodoListSQLiteHelper.COLUMN_TODO_LIST_POSITION, todoList.getPosition());

            source.update(
                TodoListSQLiteHelper.TABLE_TODO_LISTS,
                TodoListSQLiteHelper.COLUMN_TODO_LIST_ID,
                todoList.getId(),
                values
            );

            values.clear();
        }
    }

    @Override
    public void showAddDialog() {

        DialogTodoList dialogTodoList = new DialogTodoList();
        dialogTodoList.setTargetFragment(this, 1);
        dialogTodoList.show(mParent.getSupportFragmentManager(), "dialog_todolists");
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager(Context context) {

        int orientation = getOrientation(context);
        return new LinearLayoutManager(context, orientation, false);
    }

    @Override
    protected List<TodoList> getAllItems() {

        TodoListDataSource source = new TodoListDataSource(mParent);
        List<TodoList> todoLists = source.readTodoLists();

        return todoLists;
    }

    @Override
    protected void showItem(TodoList item) {

        mParent.showFragmentTodoList(item);
    }

    @Override
    public void onCreateTodoList(final String name) {

        scrollToLastPosition();
        final String id = UUID.randomUUID().toString();
        final long creationDate = new Date().getTime();
        final RecyclerAdapter adapter = (RecyclerAdapter) mRecyclerView.getAdapter();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

            scrollToLastPosition();

            TodoListDataSource source = new TodoListDataSource(mParent);

            setItemAnimator(new FlipInTopXAnimator());
            TodoList newList = new TodoList(
                id,
                name,
                creationDate,
                adapter.getItemCount()
            );

            long rowId = source.createTodoList(newList);

            if(rowId != -1 ){

                adapter.addItem(newList);
            }
            }
        }, 1000);

        // Display back the add button when the user is finished adding a to-do list
        mParent.showAddButton();
    }
}

