package todo.javier.mera.todolist.fragments;

import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import jp.wasabeef.recyclerview.animators.FadeInDownAnimator;
import jp.wasabeef.recyclerview.animators.FadeInUpAnimator;
import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.adapters.RecyclerAdapter;
import todo.javier.mera.todolist.adapters.TaskAdapter;
import todo.javier.mera.todolist.comparators.Comparator;
import todo.javier.mera.todolist.comparators.ComparatorFactory;
import todo.javier.mera.todolist.database.TodoListDataSource;
import todo.javier.mera.todolist.database.TodoListSQLiteHelper;
import todo.javier.mera.todolist.fragments.dialogs.DialogTask;
import todo.javier.mera.todolist.fragments.dialogs.DialogTaskListener;
import todo.javier.mera.todolist.model.Reminder;
import todo.javier.mera.todolist.model.Task;
import todo.javier.mera.todolist.model.Priority;
import todo.javier.mera.todolist.model.TaskStatus;
import todo.javier.mera.todolist.model.TodoList;

/**
 * Created by javie on 12/2/2016.
 */

public class FragmentTask extends FragmentRecycler<Task>
    implements
    DialogTaskListener,
    ItemTaskListener,
    PopupMenu.OnMenuItemClickListener{

    public static final String TODO_LISt = "TODO_LISt";

    private TodoList mTodoList;
    private int mSortSelected;

    public static FragmentTask newInstance(TodoList todoList) {

        FragmentTask fragment = new FragmentTask();
        Bundle bundle = new Bundle();
        bundle.putParcelable(TODO_LISt, todoList);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTodoList = getArguments().getParcelable(TODO_LISt);
        mSortSelected = R.id.sortByNone;
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
                inflater.inflate(R.menu.popup_task_sort, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(this);
                popupMenu.show();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    public void undoItemsDelete(Map<Integer, Task> items) {

        TodoListDataSource source = new TodoListDataSource(mParent);

        RecyclerAdapter adapter = (RecyclerAdapter) mRecyclerView.getAdapter();
        for(Map.Entry<Integer, Task> entry : items.entrySet()) {

            adapter.addItem(entry.getKey(), entry.getValue());
            source.createTask(entry.getValue(), entry.getKey());
        }
    }

    @Override
    protected int getDeleteTitle() {

        return R.string.menu_delete_task;
    }

    @Override
    protected int removeItems(List<Task> itemsToRemove) {

        TodoListDataSource source = new TodoListDataSource(mParent);

        Task[] items = itemsToRemove.toArray(new Task[itemsToRemove.size()]);

        return source.removeTodoListTasks(items);
    }

    @Override
    protected RecyclerAdapter createAdapter() {

        return new TaskAdapter(this);
    }

    @Override
    protected String getTitle() {

        return mTodoList.getTitle();
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager(Context context) {

        return new LinearLayoutManager(context);
    }

    @Override
    protected List<Task> getAllItems() {

        TodoListDataSource dataSource = new TodoListDataSource(mParent);
        return dataSource.readTodoListTasks(mTodoList.getId());
    }

    @Override
    protected void showItem(Task item) {

        // Todo: add behavior to handle a regular task click
    }

    @Override
    protected void updateItemPositions(Map<String, Integer> items) {

        TodoListDataSource source = new TodoListDataSource(mParent);

        ContentValues values = new ContentValues();
        for(Map.Entry<String, Integer> item : items.entrySet()) {

            values.put(TodoListSQLiteHelper.COLUMN_ITEMS_POSITION, item.getValue());

            source.update(
                TodoListSQLiteHelper.TABLE_TODO_LIST_ITEMS,
                TodoListSQLiteHelper.COLUMN_ITEMS_ID,
                item.getKey(),
                values
            );

            values.clear();
        }
    }

    @Override
    public void showAddDialog() {

        DialogTask dialogFragment = new DialogTask();
        dialogFragment.setTargetFragment(this, 1);
        dialogFragment.show(mParent.getSupportFragmentManager(), "dialog_task");
    }

    @Override
    public void onCreatedTask(
        String taskDescription,
        Date taskDuedate,
        long dueTime,
        Date reminderDate,
        long reminderTime,
        Priority priority) {

        setItemAnimator(new FadeInUpAnimator());

        TodoListDataSource source = new TodoListDataSource(mParent);

        String taskId = UUID.randomUUID().toString();
        long taskCreationDate = new Date().getTime();
        TaskStatus taskStatus = TaskStatus.Created;

        Task newTask = new Task(
            taskId,
            mTodoList.getId(),
            taskDescription,
            taskStatus,
            taskCreationDate,
            taskDuedate.getTime(),
            dueTime,
            priority,
            reminderDate == null ? Reminder.OFF : Reminder.ON
        );

        long rowId = source.createTask(
            newTask,
            mAdapter.getItemCount()
        );

        if(rowId > -1) {

            Comparator comparator = new ComparatorFactory()
                .getComparator(mSortSelected);

            List<Task> tasks = mAdapter.getItems();
            int position = comparator.getPosition(newTask, tasks);
            mAdapter.addItem(position, newTask);

            if(reminderDate != null && reminderTime != 0L) {

                mParent.setReminder(
                    newTask.getDescription(),
                    newTask.hashCode(),
                    reminderDate,
                    reminderTime
                );
            }
        }
    }

    @Override
    public void onUpdateStatus(int position, boolean isCompleted) {

        Task item = (Task)mAdapter.getItem(position);
        item.setStatus(isCompleted ? TaskStatus.Completed : TaskStatus.Created);

        ContentValues values = new ContentValues();
        values.put(
            TodoListSQLiteHelper.COLUMN_ITEMS_STATUS,
            item.getStatus().ordinal()
        );

        TodoListDataSource dataSource = new TodoListDataSource(mParent);
        dataSource.update(
            TodoListSQLiteHelper.TABLE_TODO_LIST_ITEMS,
            TodoListSQLiteHelper.COLUMN_ITEMS_ID,
            item.getId(),
            values
        );
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        mSortSelected = item.getItemId();
        String sortByColumn = "";
        String order = "";

        switch(item.getItemId()) {

            case R.id.sortByNone:
                sortByColumn = TodoListSQLiteHelper.COLUMN_ITEMS_POSITION;
                order = "ASC";
                break;

            case R.id.sortByName:
                sortByColumn = TodoListSQLiteHelper.COLUMN_ITEMS_DESCRIPTION;
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

            case R.id.sortByCompleted:
                sortByColumn = TodoListSQLiteHelper.COLUMN_ITEMS_STATUS;
                order = "DESC";
                break;
        }

        setItemAnimator(new FadeInDownAnimator(new LinearOutSlowInInterpolator()));

        TodoListDataSource dataSource = new TodoListDataSource(mParent);
        final List<Task> tasks = dataSource.readTodoListTasks(mTodoList.getId(), sortByColumn, order);

        mAdapter.removeAll();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                mAdapter.addItems(tasks);
            }
        }, 500);
        return true;
    }
}
