package todo.javier.mera.todolist.ui;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.pm.ActivityInfoCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.recyclerview.animators.FlipInTopXAnimator;
import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.comparators.Comparator;
import todo.javier.mera.todolist.comparators.ComparatorFactory;
import todo.javier.mera.todolist.database.TodoListDataSource;
import todo.javier.mera.todolist.database.TodoListSQLiteHelper;
import todo.javier.mera.todolist.fragments.dialogs.DialogCreateTodoList;
import todo.javier.mera.todolist.fragments.dialogs.DialogTodoListListener;
import todo.javier.mera.todolist.model.Priority;
import todo.javier.mera.todolist.model.TodoList;

public class MainActivity extends ActivityBase
    implements
    DialogTodoListListener{

    public static final String NEW_TODO_ID = "new_todo_id";
    public static final String NEW_TODO_BUNDLE = "new_todo_bundle";
    @BindView(R.id.activity_main)
    LinearLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setExitTransition(new Slide(Gravity.LEFT));
        }

        ButterKnife.bind(this);
    }

    @OnClick(R.id.viewListsLayout)
    public void onViewListsClick(View view) {

        startActivityWithTransition(this, TodosActivity.class);
    }

    @OnClick(R.id.createListLayout)
    public void onCreateListClick(View view) {

        DialogCreateTodoList dialogTodoList = new DialogCreateTodoList();
        dialogTodoList.show(getSupportFragmentManager(), "dialog_todolists");
    }

    @Override
    public void onCreateTodoList(String name, Date dueDate, Priority priority) {

        String id = UUID.randomUUID().toString();
        long date = dueDate == null ? 0L : dueDate.getTime();
        int position = getNextPosition();

        TodoListDataSource source = new TodoListDataSource(this);
        TodoList newList = new TodoList(
            id,
            name,
            date,
            priority);

        long rowId = source.createTodoList(
            newList,
            position
        );

        if(rowId != -1 ){

            startActivityWithTodolist(this, TodosActivity.class, id);
        }
    }

    private int getNextPosition() {

        TodoListDataSource source = new TodoListDataSource(this);
        int position = source.getLastTodoList();

       return position == -1 ? 0 : position;
    }

    private void startActivityWithTransition(AppCompatActivity actLauncher, Class<?> actToLaunch) {

        Intent intent = new Intent(this, actToLaunch);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(actLauncher);
        startActivity(intent, options.toBundle());
    }

    private void startActivityWithTodolist(AppCompatActivity actLauncher, Class<?> actToLaunch, String todoId) {

        Intent intent = new Intent(actLauncher, actToLaunch);
        Bundle bundle = new Bundle();
        bundle.putString(NEW_TODO_ID, todoId);
        intent.putExtra(NEW_TODO_BUNDLE, bundle);

        startActivity(intent);
    }
}
