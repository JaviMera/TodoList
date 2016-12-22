package todo.javier.mera.todolist.fragments;

import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import jp.wasabeef.recyclerview.animators.FadeInDownAnimator;
import jp.wasabeef.recyclerview.animators.FadeInUpAnimator;
import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.adapters.RecyclerAdapter;
import todo.javier.mera.todolist.adapters.TodoListTaskAdapter;
import todo.javier.mera.todolist.database.TodoListDataSource;
import todo.javier.mera.todolist.database.TodoListSQLiteHelper;
import todo.javier.mera.todolist.fragments.dialogs.DialogSort;
import todo.javier.mera.todolist.fragments.dialogs.DialogSortListener;
import todo.javier.mera.todolist.fragments.dialogs.DialogTask;
import todo.javier.mera.todolist.fragments.dialogs.DialogTaskListener;
import todo.javier.mera.todolist.model.Task;
import todo.javier.mera.todolist.model.TaskPriority;
import todo.javier.mera.todolist.model.TaskStatus;
import todo.javier.mera.todolist.model.TodoList;

/**
 * Created by javie on 12/2/2016.
 */

public class FragmentTask extends FragmentRecycler<Task>
    implements
    DialogTaskListener,
    ItemTaskListener,
    DialogSortListener{

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
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);

        menu
            .findItem(R.id.action_sort)
            .setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {

            case R.id.action_sort:
                DialogSort dialogSort = DialogSort.newInstance(mSortSelected);
                dialogSort.setTargetFragment(this, 1);
                dialogSort.show(
                    mParent.getSupportFragmentManager(),
                    "sort_dialog"
                );

                break;

            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    protected int getDeleteTitle() {

        return R.string.menu_delete_task;
    }

    @Override
    protected int deleteRecords(List<Task> itemsToRemove) {

        TodoListDataSource source = new TodoListDataSource(mParent);

        Task[] items = itemsToRemove.toArray(new Task[itemsToRemove.size()]);
        int affectedRows = source.removeTodoListTasks(items);

        return affectedRows;
    }

    @Override
    protected RecyclerAdapter getAdapter() {

        return new TodoListTaskAdapter(this);
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

        return mTodoList.getTasks();
    }

    @Override
    protected void showItem(Task item) {

        // Todo: add behavior to handle a regular task click
    }

    @Override
    protected void onUpdatePosition(List<Task> items) {

        TodoListDataSource source = new TodoListDataSource(mParent);

        ContentValues values = new ContentValues();
        for(Task task : items) {

            values.put(TodoListSQLiteHelper.COLUMN_ITEMS_POSITION, task.getPosition());

            source.update(
                TodoListSQLiteHelper.TABLE_TODO_LIST_ITEMS,
                TodoListSQLiteHelper.COLUMN_ITEMS_ID,
                task.getId(),
                values
            );

            values.clear();
        }

        mTodoList.setItems(items);
    }

    @Override
    public void showAddDialog() {

        DialogTask dialogFragment = new DialogTask();
        dialogFragment.setTargetFragment(this, 1);
        dialogFragment.show(mParent.getSupportFragmentManager(), "dialog_task");
    }

    @Override
    public void onCreatedTask(final String taskDescription, final Date taskDuedate, final TaskPriority taskPriority) {

        setItemAnimator(new FadeInUpAnimator());
        scrollToLastPosition();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                scrollToLastPosition();
                RecyclerAdapter adapter = (RecyclerAdapter) mRecyclerView.getAdapter();

                TodoListDataSource source = new TodoListDataSource(mParent);

                String taskId = UUID.randomUUID().toString();
                long taskCreationDate = new Date().getTime();
                TaskStatus taskStatus = TaskStatus.Created;

                Task newTask = new Task(
                    taskId,
                    mTodoList.getId(),
                    adapter.getItemCount(),
                    taskDescription,
                    taskStatus,
                    taskCreationDate,
                    taskDuedate.getTime(),
                    taskPriority
                );

                long rowId = source.addTaskRecord(newTask);

                if(rowId > -1) {

                    adapter.addItem(newTask);
                     mTodoList.addTask(newTask);
                }
            }
        }, 1000);


        // Display back the add button when the user is finished adding a task
        mParent.showFabButton();
    }

    @Override
    public void onUpdateStatus(int position, boolean isCompleted) {

        RecyclerAdapter adapter = (RecyclerAdapter) mRecyclerView.getAdapter();
        Task item = (Task)adapter.getItem(position);
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
    public void onSortSelected(int sortSelected, String sortByColumn, String order) {

        mSortSelected = sortSelected;

        setItemAnimator(new FadeInDownAnimator(new LinearOutSlowInInterpolator()));

        TodoListDataSource dataSource = new TodoListDataSource(mParent);
        final List<Task> tasks = dataSource.readTodoListTasks(
            mTodoList.getId(),
            sortByColumn,
            order
        );

        final RecyclerAdapter adapter = (RecyclerAdapter) mRecyclerView.getAdapter();
        adapter.removeAll();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            adapter.addItems(tasks);
            }
        }, 500);
    }

    @Override
    public void restoreRecords() {

        TodoListDataSource dataSource = new TodoListDataSource(mParent);
        RecyclerAdapter adapter = getAdapter();

        for(Task task : mRemovedItems) {

            dataSource.addTaskRecord(task);
            adapter.restoreItem(task);
        }
    }
}
