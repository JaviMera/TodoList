package todo.javier.mera.todolist.fragments;

import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import butterknife.OnClick;
import jp.wasabeef.recyclerview.animators.FadeInUpAnimator;
import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.adapters.RecyclerAdapter;
import todo.javier.mera.todolist.adapters.TodoListTaskAdapter;
import todo.javier.mera.todolist.database.TodoListDataSource;
import todo.javier.mera.todolist.database.TodoListSQLiteHelper;
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
    ItemTaskListener {

    public static final String TODO_LISt = "TODO_LISt";
    private TodoList mTodoList;

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
    protected void updateItems(List<Task> items) {

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
    public void onCreatedTask(final String title, final Date dueDate, final TaskPriority priority) {

        setItemAnimator(new FadeInUpAnimator());
        scrollToLastPosition();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                scrollToLastPosition();
                RecyclerAdapter adapter = (RecyclerAdapter) mRecyclerView.getAdapter();

                TodoListDataSource source = new TodoListDataSource(mParent);

                String id = UUID.randomUUID().toString();
                long creationDate = new Date().getTime();
                long date = dueDate.getTime();
                TaskStatus status = TaskStatus.Created;

                Task newTask = new Task(
                    id,
                    mTodoList.getId(),
                    adapter.getItemCount(),
                    title,
                    status,
                    creationDate,
                    date,
                    priority
                );

                long rowId = source.createTodoListTask(newTask);

                if(rowId > -1) {

                    adapter.addItem(newTask);
                }
            }
        }, 1000);
    }

    @Override
    public void onStatusUpdate(int position, boolean isCompleted) {

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
}
