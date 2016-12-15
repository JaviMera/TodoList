package todo.javier.mera.todolist.fragments;

import android.app.Dialog;
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
import android.view.View;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import butterknife.OnClick;
import jp.wasabeef.recyclerview.animators.FadeInDownAnimator;
import jp.wasabeef.recyclerview.animators.FadeInUpAnimator;
import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.adapters.RecyclerAdapter;
import todo.javier.mera.todolist.adapters.TodoListTaskAdapter;
import todo.javier.mera.todolist.database.TodoListDataSource;
import todo.javier.mera.todolist.database.TodoListSQLiteHelper;
import todo.javier.mera.todolist.fragments.dialogs.DialogSort;
import todo.javier.mera.todolist.fragments.dialogs.DialogSortListener;
import todo.javier.mera.todolist.fragments.dialogs.DialogTaskListener;
import todo.javier.mera.todolist.fragments.dialogs.DialogTask;
import todo.javier.mera.todolist.model.TaskPriority;
import todo.javier.mera.todolist.model.TaskStatus;
import todo.javier.mera.todolist.model.TodoList;
import todo.javier.mera.todolist.model.Task;

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

    @OnClick(R.id.fab)
    public void onAddListButtonClick(View view) {

        DialogTask dialogFragment = new DialogTask();
        dialogFragment.setTargetFragment(this, 1);
        dialogFragment.show(mParent.getSupportFragmentManager(), "dialog_task");
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
    protected int removeItems(List<Task> itemsToRemove) {

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

                long rowId = source.createTodoListTask(newTask);

                if(rowId > -1) {

                    adapter.addItem(newTask);
                }
            }
        }, 1000);
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
    public void onSortSelected(String sortByColumn, int sortSelected) {

        mSortSelected = sortSelected;
        setItemAnimator(new FadeInDownAnimator(new LinearOutSlowInInterpolator()));

        TodoListDataSource dataSource = new TodoListDataSource(mParent);
        final List<Task> tasks = dataSource.readTodoListTasks(mTodoList.getId(), sortByColumn);

        final RecyclerAdapter adapter = (RecyclerAdapter) mRecyclerView.getAdapter();
        adapter.removeAll();

        // Check if user has selected sort by completed
        if(!sortByColumn.equals(TodoListSQLiteHelper.COLUMN_ITEMS_STATUS)) {

            // If user hasn't selected sort by Completed, then push down all completed tasks to the bottom
            // as some of them might show up in between other undone tasks.
            moveCompletedToBottom(tasks);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            adapter.addItems(tasks);
            }
        }, 500);
    }

    private void moveCompletedToBottom(List<Task> tasks) {

        List<Task> completedTasks = new LinkedList<>();
        for(int i = 0 ; i < tasks.size() ; i++) {

            if(tasks.get(i).getStatus() == TaskStatus.Completed) {

                completedTasks.add(tasks.get(i));
            }
        }

        tasks.removeAll(completedTasks);
        tasks.addAll(tasks.size(), completedTasks);
    }
}
