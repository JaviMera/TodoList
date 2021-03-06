package todo.javier.mera.todolist.ui;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.broadcasts.NotificationPublisher;
import todo.javier.mera.todolist.database.TodoListDataSource;
import todo.javier.mera.todolist.database.TodoListSQLiteHelper;
import todo.javier.mera.todolist.fragments.FragmentRecycler;
import todo.javier.mera.todolist.fragments.FragmentTask;
import todo.javier.mera.todolist.fragments.FragmentTodoList;
import todo.javier.mera.todolist.model.ItemBase;
import todo.javier.mera.todolist.model.Task;
import todo.javier.mera.todolist.model.TodoList;

public class TodosActivity extends ActivityBase
        implements TodosActivityView {

    public static final int TASK_NOTIFICATION_CODE = 10;
    public static final String NOTIFICATION_ID = "ID";
    public static final String NOTIFICATION = "NOTIFICATION";
    public static final String NOTIFICATION_BUNDLE = "notification_bundle";
    public static final String NOTIFICATION_TODO_ID = "todo_list_id";
    private static final String NOTIFICATION_TASK_ID = "todo_list_task_id";

    private FragmentHelper mFragmentHelper;
    private TodosActivityPresenter mPresenter;
    private AlarmManager mAlarmManager;

    @BindView(R.id.toolbar)
    Toolbar mToolBar;

    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout mCoordinatorLayout;

    private static final String FRAGMENT_TAG = "fragment_recycler";
    private FragmentRecycler mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todos);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(new Slide(Gravity.RIGHT));
        }

        mPresenter = new TodosActivityPresenter(this);
        mFragmentHelper = new FragmentHelper(getSupportFragmentManager());
        mAlarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

        ButterKnife.bind(this);

        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.mipmap.ic_sort);
        mToolBar.setOverflowIcon(drawable);

        // For some reason if the title is not initially set to something, when the Fragment calls
        // for the first time to set the title, the title will not be changed.
        mPresenter.setToolbarTitle("");

        mPresenter.setToolbar();
        mPresenter.toggleBackButton(true);

        if(savedInstanceState != null) {

            mCurrentFragment = (FragmentRecycler) mFragmentHelper.findFragment(FRAGMENT_TAG);
            mFragmentHelper.replace(R.id.fragmentContainer, mCurrentFragment, FRAGMENT_TAG);
        }
        else {

            mCurrentFragment = FragmentTodoList.newInstance();
            mFragmentHelper.replace(R.id.fragmentContainer, mCurrentFragment, FRAGMENT_TAG);

            // Check if the activity is being opened by a notification touch
            Intent intent = getIntent();

            Bundle bundle;
            bundle = intent.getBundleExtra(MainActivity.NEW_TODO_BUNDLE);

            if(bundle != null) {

                showFragmentTaskWithNewList(bundle);
                showSnackBar("ADDED NEW LIST!", "", null);
            }
            else {

                bundle = intent.getBundleExtra(NOTIFICATION_BUNDLE);

                if(bundle != null) {

                   showFragmentTaskAfterNotificationClick(bundle);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {

        // Get the current fragment in the container after a back press
        // If this is not retrieved, Fragment task could be referenced even though the user
        // pressed the back button to go back to Fragment To-do List
        mCurrentFragment = (FragmentRecycler) mFragmentHelper.findFragment(FRAGMENT_TAG);

        if (mCurrentFragment.isRemovingItems()) {

            mCurrentFragment.resetItems();
            mPresenter.updateToolbarBackground(R.color.colorPrimary);
        }
        else {

            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {

            case android.R.id.home:

                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setToolbarTitle(String text) {

        mToolBar.setTitle(text);
    }

    @Override
    public void showFragmentTodoList(TodoList todoList) {

        mCurrentFragment = FragmentTask.newInstance(todoList);
        mFragmentHelper.replaceWithBackStack(
            R.id.fragmentContainer,
            mCurrentFragment,
            FRAGMENT_TAG,
            null
        );
    }

    @Override
    public void updateToolbarBackground(int color) {

        Drawable newDrawable = ContextCompat.getDrawable(this, color);
        mToolBar.setBackground(newDrawable);
    }

    @Override
    public void toggleBackButton(boolean canDisplay) {

        getSupportActionBar().setDisplayHomeAsUpEnabled(canDisplay);
    }

    @Override
    public void setToolbar() {

        setSupportActionBar(mToolBar);
    }

    @Override
    public void showSnackBar(String message, String action, final Map<Integer, ItemBase> items) {

        Snackbar.make(
            mCoordinatorLayout,
            message,
            Snackbar.LENGTH_LONG
        )
        .setAction(action, new View.OnClickListener() {

            @Override
            public void onClick(View view) {
            FragmentRecycler currentFragment = (FragmentRecycler) mFragmentHelper.findFragment(FRAGMENT_TAG);
            currentFragment.undoItemsDelete(items);
            currentFragment.clearRemovableItems();
            currentFragment.updatePositions();
            }
        })
        .show();
    }

    @Override
    public void setIndicator(int resourceId) {

        getSupportActionBar().setHomeAsUpIndicator(resourceId);
    }

    @Override
    public void cancelReminder(Task task) {

        Intent intent = new Intent(this, NotificationPublisher.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
            TodosActivity.this,
            task.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        );

        pendingIntent.cancel();
        mAlarmManager.cancel(pendingIntent);
    }

    @Override
    public void createReminder(Task task) {

        Notification notification = createNotification(
            "Task Reminder",
            task,
            R.mipmap.ic_alarm_on,
            R.mipmap.ic_logo
        );

        Intent intent = createNotificationIntent(task, notification);
        PendingIntent pendingIntent = createPendingIntent(intent, task.hashCode());
        Calendar c = createCalendarWithDate(task.getReminderDate());

        mAlarmManager.set(
            AlarmManager.RTC_WAKEUP,
            c.getTimeInMillis(),
            pendingIntent
        );
    }

    private PendingIntent createPendingIntent(Intent intent, int hashcode) {

       return PendingIntent.getBroadcast(
            this,
            hashcode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        );
    }


    private Intent createNotificationIntent(Task task, Notification notification) {

        Intent intent = new Intent(this, NotificationPublisher.class);
        intent.putExtra(NOTIFICATION_ID, task.hashCode());
        intent.putExtra(NOTIFICATION, notification);

        return intent;
    }

    private Calendar createCalendarWithDate(long date) {

        Calendar calendar = Calendar.getInstance();
        Date reminderDate = new Date();
        reminderDate.setTime(date);
        calendar.setTime(reminderDate);

        calendar.set(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH),
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            0
        );

        return calendar;
    }

    private Notification createNotification(String title, Task task, int smallIcon, int largeIcon) {

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder.setContentTitle(title);
        notificationBuilder.setVibrate(new long[]{1000, 1000});
        notificationBuilder.setContentText(task.getDescription());
        notificationBuilder.setColor(ContextCompat.getColor(this, R.color.colorPrimary));
        notificationBuilder.setSmallIcon(smallIcon);
        notificationBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), largeIcon));
        notificationBuilder.setAutoCancel(true);

        // This bundle will later be used to retrieve the list to where the task belongs
        // and will help us launch the fragment task with the corresponding to-do list
        Bundle bundle = new Bundle();
        bundle.putString(NOTIFICATION_TODO_ID, task.getTodoListId());
        bundle.putString(NOTIFICATION_TASK_ID, task.getId());

        notificationBuilder.setContentIntent(
            PendingIntent.getActivity(
                this,
                TodosActivity.TASK_NOTIFICATION_CODE,
                new Intent(this, TodosActivity.class)
                    .putExtra(NOTIFICATION_BUNDLE,bundle),
                PendingIntent.FLAG_CANCEL_CURRENT
            )
        );

        return notificationBuilder.build();
    }

    private void showFragmentTaskAfterNotificationClick(Bundle bundle) {

        String todoListId = bundle.getString(NOTIFICATION_TODO_ID);
        String taskId = bundle.getString(NOTIFICATION_TASK_ID);

        ContentValues values = new ContentValues();
        values.put(TodoListSQLiteHelper.COLUMN_ITEMS_REMINDER, 0L);

        TodoListDataSource source = new TodoListDataSource(this);
        source.update(
                TodoListSQLiteHelper.TABLE_TASKS,
                TodoListSQLiteHelper.COLUMN_ITEMS_ID,
                taskId,
                values);

        TodoList todoList = source.readTodoList(todoListId);
        showFragmentTodoList(todoList);
    }

    private void showFragmentTaskWithNewList(Bundle bundle) {

        String todoListId = bundle.getString(MainActivity.NEW_TODO_ID);
        TodoListDataSource source = new TodoListDataSource(this);
        TodoList todoList = source.readTodoList(todoListId);
        showFragmentTodoList(todoList);
    }
}
