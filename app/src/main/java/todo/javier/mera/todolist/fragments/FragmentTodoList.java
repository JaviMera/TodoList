package todo.javier.mera.todolist.fragments;

import android.content.ContentValues;
import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import jp.wasabeef.recyclerview.animators.FlipInTopXAnimator;
import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.adapters.RecyclerAdapter;
import todo.javier.mera.todolist.adapters.TodolistAdapter;
import todo.javier.mera.todolist.database.TodoListDataSource;
import todo.javier.mera.todolist.database.TodoListSQLiteHelper;
import todo.javier.mera.todolist.fragments.dialogs.DialogTodoList;
import todo.javier.mera.todolist.fragments.dialogs.DialogTodoListListener;
import todo.javier.mera.todolist.model.Task;
import todo.javier.mera.todolist.model.TodoList;

public class FragmentTodoList extends FragmentRecycler<TodoList>
    implements DialogTodoListListener {

    private Map<String, List<Task>> mRemovableTodoLists;

    public static FragmentTodoList newInstance() {

        return new FragmentTodoList();
    }

    @Override
    protected RecyclerAdapter createAdapter() {

        return new TodolistAdapter(this);
    }

    @Override
    protected String getTitle() {

        return "My Lists";
    }

    @Override
    protected int getDeleteTitle() {

        return R.string.menu_delete_todolist;
    }

    @Override
    protected int removeItems(List<TodoList> itemsToRemove) {

        TodoListDataSource source = new TodoListDataSource(mParent);
        mRemovableTodoLists = new LinkedHashMap();

        for(TodoList tl : itemsToRemove) {

            List<Task> tasks = source.readTodoListTasks(tl.getId());
            mRemovableTodoLists.put(tl.getId(), tasks);
        }

        TodoList[] items = itemsToRemove.toArray(new TodoList[itemsToRemove.size()]);
        int affectedRows = source.removeTodoLists(items);

        return affectedRows;
    }

    @Override
    protected void updateItemPositions(Map<String, Integer> items) {

        TodoListDataSource source = new TodoListDataSource(mParent);

        ContentValues values = new ContentValues();
        for(Map.Entry<String, Integer> item : items.entrySet()) {

            values.put(TodoListSQLiteHelper.COLUMN_TODO_LIST_POSITION, item.getValue());

            source.update(
                TodoListSQLiteHelper.TABLE_TODO_LISTS,
                TodoListSQLiteHelper.COLUMN_TODO_LIST_ID,
                item.getKey(),
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
    public void undoItemsDelete(Map<Integer, TodoList> items) {

        TodoListDataSource source = new TodoListDataSource(mParent);

        for(Map.Entry<Integer, TodoList> entry : items.entrySet()) {

            mAdapter.addItem(entry.getKey(), entry.getValue());
            source.createTodoList(entry.getValue(), entry.getKey());

            List<Task> tasks = mRemovableTodoLists.get(entry.getValue().getId());
            for(int position = 0 ; position < tasks.size() ; position++) {

                source.createTask(tasks.get(position), position);
            }
        }
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager(Context context) {

        int orientation = getOrientation(context);
        return new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
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

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

            scrollToLastPosition();

            TodoListDataSource source = new TodoListDataSource(mParent);

            setItemAnimator(new FlipInTopXAnimator());
            TodoList newList = new TodoList(
                id,
                name,
                creationDate
            );

            long rowId = source.createTodoList(
                newList,
                mAdapter.getItemCount()
            );

            if(rowId != -1 ){

                mAdapter.addItem(newList);
            }
            }
        }, 1000);

        // Display back the add button when the user is finished adding a to-do list
        mParent.showFabButton();
    }
}

