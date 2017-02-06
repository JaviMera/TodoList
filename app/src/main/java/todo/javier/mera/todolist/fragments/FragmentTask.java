package todo.javier.mera.todolist.fragments;

import android.content.ContentValues;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import jp.wasabeef.recyclerview.animators.FadeInDownAnimator;
import jp.wasabeef.recyclerview.animators.FadeInLeftAnimator;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.adapters.RecyclerAdapter;
import todo.javier.mera.todolist.adapters.ReminderClickListener;
import todo.javier.mera.todolist.adapters.TaskAdapter;
import todo.javier.mera.todolist.comparators.Comparator;
import todo.javier.mera.todolist.comparators.ComparatorFactory;
import todo.javier.mera.todolist.database.TodoListDataSource;
import todo.javier.mera.todolist.database.TodoListSQLiteHelper;
import todo.javier.mera.todolist.fragments.dialogs.DialogCreateTask;
import todo.javier.mera.todolist.fragments.dialogs.DialogModifyListener;
import todo.javier.mera.todolist.fragments.dialogs.DialogModifyTask;
import todo.javier.mera.todolist.fragments.dialogs.DialogCreateTaskListener;
import todo.javier.mera.todolist.model.Task;
import todo.javier.mera.todolist.model.Priority;
import todo.javier.mera.todolist.model.TaskStatus;
import todo.javier.mera.todolist.model.TodoList;

/**
 * Created by javie on 12/2/2016.
 */

public class FragmentTask extends FragmentRecycler<Task>
    implements
    DialogCreateTaskListener,
    ItemTaskListener,
    DialogModifyListener<Task>,
    ReminderClickListener,
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

        // Check the version of the current device since this feature is only available on
        // API 21 and up
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setEnterTransition(new Slide(Gravity.RIGHT));
            setExitTransition(new Slide(Gravity.LEFT));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
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
    protected void showModifyDialog(Task item) {

        DialogModifyTask dialogModifyTask = DialogModifyTask.newInstance(item);
        dialogModifyTask.setTargetFragment(this, 1);
        dialogModifyTask.show(mParent.getSupportFragmentManager(), "modify_dialog");
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

        return mTodoList.getDescription();
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager(Context context) {

        return new LinearLayoutManager(context);
    }

    @Override
    protected List<Task> getAllItems() {

        TodoListDataSource dataSource = new TodoListDataSource(mParent);
        return dataSource.readTask(mTodoList.getId());
    }

    @Override
    protected void updateItemPositions(Map<String, Integer> items) {

        TodoListDataSource source = new TodoListDataSource(mParent);

        ContentValues values = new ContentValues();
        for(Map.Entry<String, Integer> item : items.entrySet()) {

            values.put(TodoListSQLiteHelper.COLUMN_ITEMS_POSITION, item.getValue());

            source.update(
                TodoListSQLiteHelper.TABLE_TASKS,
                TodoListSQLiteHelper.COLUMN_ITEMS_ID,
                item.getKey(),
                values
            );

            values.clear();
        }
    }

    @Override
    public void showAddDialog() {

        DialogCreateTask dialogFragment = DialogCreateTask.newInstance();
        dialogFragment.setTargetFragment(this, 1);
        dialogFragment.show(mParent.getSupportFragmentManager(), "dialog_task");
    }

    @Override
    public void onCreatedTask(
        String taskDescription,
        Date taskDuedate,
        long reminderDate,
        Priority priority) {

        setItemAnimator(new FadeInLeftAnimator());
        mRecyclerView.getItemAnimator().setAddDuration(500);

        TodoListDataSource source = new TodoListDataSource(mParent);
        String taskId = UUID.randomUUID().toString();
        long dueTime = taskDuedate == null ? 0L : taskDuedate.getTime();
        TaskStatus taskStatus = TaskStatus.Created;

        Task newTask = new Task(
            taskId,
            mTodoList.getId(),
            taskDescription,
            taskStatus,
            dueTime,
            priority,
            reminderDate
        );

        long rowId = source.createTask(
            newTask,
            mAdapter.getItemCount()
        );

        if(rowId > -1) {

            Comparator comparator = new ComparatorFactory<Task>()
                .getComparator(mSortSelected);

            List<Task> tasks = mAdapter.getItems();
            int position = comparator.getPosition(newTask, tasks);
            mAdapter.addItem(position, newTask);

            mParent.showSnackBar("ADDED NEW TASK!", null, null);

            if(reminderDate != 0L) {

                mParent.createReminder(newTask);
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
            TodoListSQLiteHelper.TABLE_TASKS,
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
        final List<Task> tasks = dataSource.readTask(mTodoList.getId(), sortByColumn, order);

        mAdapter.removeAll();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            mAdapter.addItems(tasks);
            }
        }, 500);
        return true;
    }

    @Override
    public void onModifyItem(Task task) {

        setItemAnimator(new SlideInLeftAnimator());
        mRecyclerView.getItemAnimator().setChangeDuration(500);

        ContentValues values = new ContentValues();
        TodoListDataSource dataSource = new TodoListDataSource(mParent);
        values.put(TodoListSQLiteHelper.COLUMN_ITEMS_DESCRIPTION, task.getDescription());
        values.put(TodoListSQLiteHelper.COLUMN_ITEMS_DUE_DATE, task.getDueDate());
        values.put(TodoListSQLiteHelper.COLUMN_ITEMS_REMINDER, task.getReminderDate());
        values.put(TodoListSQLiteHelper.COLUMN_ITEMS_PRIORITY, task.getPriority().ordinal());

        int affectedRows = dataSource.update(
            TodoListSQLiteHelper.TABLE_TASKS,
            TodoListSQLiteHelper.COLUMN_ITEMS_ID,
            task.getId(),
            values
        );

        if(affectedRows != -1) {

            mAdapter.updateItem(task);

            if(task.getReminderDate() != 0L) {

                mParent.createReminder(task);
                mAdapter.updateItem(task);
            }
            else {

                mParent.cancelReminder(task);
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.fragment_task_menu, menu);
        MenuItem addItem = menu.findItem(R.id.action_add);
        MenuItem deleteItem = menu.findItem(R.id.action_delete);
        MenuItem sortItem = menu.findItem(R.id.action_sort);

        deleteItem.setTitle(getDeleteTitle());

        if(isRemovingItems()) {

            addItem.setVisible(false);
            deleteItem.setVisible(true);
            sortItem.setVisible(false);
        }
        else {

            addItem.setVisible(true);
            deleteItem.setVisible(false);
            sortItem.setVisible(true);
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onReminderClick(int position) {

        Task task = (Task) mAdapter.getItem(position);
        task.setReminder(0L);

        ContentValues values = new ContentValues();
        TodoListDataSource dataSource = new TodoListDataSource(mParent);
        values.put(TodoListSQLiteHelper.COLUMN_ITEMS_REMINDER, task.getReminderDate());

        int affectedRows = dataSource.update(
            TodoListSQLiteHelper.TABLE_TASKS,
            TodoListSQLiteHelper.COLUMN_ITEMS_ID,
            task.getId(),
            values
        );

        if(affectedRows != -1) {

            mParent.cancelReminder(task);
            mAdapter.updateItem(task);
        }
    }
}
