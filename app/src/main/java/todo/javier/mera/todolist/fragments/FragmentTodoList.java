package todo.javier.mera.todolist.fragments;

import android.content.ContentValues;
import android.content.Context;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jp.wasabeef.recyclerview.animators.FadeInDownAnimator;
import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.adapters.RecyclerAdapter;
import todo.javier.mera.todolist.adapters.TodolistAdapter;
import todo.javier.mera.todolist.database.TodoListDataSource;
import todo.javier.mera.todolist.database.TodoListSQLiteHelper;
import todo.javier.mera.todolist.fragments.dialogs.DialogCreateTodoList;
import todo.javier.mera.todolist.fragments.dialogs.DialogModifyTodoList;
import todo.javier.mera.todolist.fragments.dialogs.DialogModifyListener;
import todo.javier.mera.todolist.model.Task;
import todo.javier.mera.todolist.model.TodoList;

public class FragmentTodoList extends FragmentRecycler<TodoList>
    implements
    DialogModifyListener<TodoList>,
    TodoListNavigateListener,
    PopupMenu.OnMenuItemClickListener {

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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.fragment_todo_list_menu, menu);
        MenuItem deleteItem = menu.findItem(R.id.action_delete);
        MenuItem sortItem = menu.findItem(R.id.action_sort);

        deleteItem.setTitle(getDeleteTitle());

        if(isRemovingItems()) {

            deleteItem.setVisible(true);
            sortItem.setVisible(false);
        }
        else {

            deleteItem.setVisible(false);
            sortItem.setVisible(true);
        }

        super.onCreateOptionsMenu(menu, inflater);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {

            case R.id.action_sort:

                PopupMenu popupMenu = new PopupMenu(
                    mParent,
                    mParent.findViewById(R.id.action_sort)
                );

                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.popup_todo_list_sort, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(this);
                popupMenu.show();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    protected void showModifyDialog(TodoList item) {

        DialogModifyTodoList dialog = DialogModifyTodoList.newInstance(item);
        dialog.setTargetFragment(this, 1);
        dialog.show(mParent.getSupportFragmentManager(), "modify_dialog");
    }

    @Override
    protected int removeItems(List<TodoList> itemsToRemove) {

        TodoListDataSource source = new TodoListDataSource(mParent);
        mRemovableTodoLists = new LinkedHashMap();

        for(TodoList tl : itemsToRemove) {

            List<Task> tasks = source.readTask(tl.getId());
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

        DialogCreateTodoList dialogTodoList = new DialogCreateTodoList();
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

        return new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
    }

    @Override
    protected List<TodoList> getAllItems() {

        TodoListDataSource source = new TodoListDataSource(mParent);
        List<TodoList> todoLists = source.readTodoLists();

        return todoLists;
    }

    @Override
    public void onNavigateClick(int position) {

        TodoList item = (TodoList) mAdapter.getItem(position);
        mParent.showFragmentTodoList(item);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        String sortByColumn = "";
        String order = "";

        switch(item.getItemId()) {

            case R.id.sortByNone:
                sortByColumn = TodoListSQLiteHelper.COLUMN_ITEMS_POSITION;
                order = "ASC";
                break;

            case R.id.sortByDueDate:
                sortByColumn = TodoListSQLiteHelper.COLUMN_ITEMS_DUE_DATE;
                order = "ASC";
                break;

            case R.id.sortByPriority:
                sortByColumn = TodoListSQLiteHelper.COLUMN_ITEMS_PRIORITY;
                order = "DESC";
                break;
        }

        setItemAnimator(new FadeInDownAnimator(new LinearOutSlowInInterpolator()));
        TodoListDataSource dataSource = new TodoListDataSource(mParent);
        List<TodoList> todoLists = dataSource.readTodoLists(sortByColumn, order);

        mAdapter.removeAll();
        mAdapter.addItems(todoLists);

        return true;
    }

    @Override
    public void onModifyItem(TodoList updatedTodoList) {

        ContentValues values = new ContentValues();

        values.put(TodoListSQLiteHelper.COLUMN_TODO_LIST_DESCRIPTION, updatedTodoList.getDescription());
        values.put(TodoListSQLiteHelper.COLUMN_TODO_LIST_DUE_DATE, updatedTodoList.getDueDate());
        values.put(TodoListSQLiteHelper.COLUMN_TODO_LIST_PRIORITY, updatedTodoList.getPriority().ordinal());

        TodoListDataSource source = new TodoListDataSource(mParent);
        int affectedRows = source.update(
            TodoListSQLiteHelper.TABLE_TODO_LISTS,
            TodoListSQLiteHelper.COLUMN_TODO_LIST_ID,
            updatedTodoList.getId(),
            values
        );

        if(affectedRows != -1) {

            mAdapter.updateItem(updatedTodoList);
        }
    }
}

