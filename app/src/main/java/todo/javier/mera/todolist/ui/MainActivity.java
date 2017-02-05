package todo.javier.mera.todolist.ui;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    @BindView(R.id.activity_main)
    LinearLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.viewListsLayout)
    public void onViewListsClick(View view) {

        Intent intent = new Intent(this, TodosActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.createListLayout)
    public void onCreateListClick(View view) {

        DialogCreateTodoList dialogTodoList = new DialogCreateTodoList();
        dialogTodoList.show(getSupportFragmentManager(), "dialog_todolists");
    }

    @Override
    public void onCreateTodoList(String name, Date dueDate, Priority priority) {

        String id = UUID.randomUUID().toString();
        long date = dueDate.getTime();
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

            Snackbar snackbar = Snackbar.make(
                mLayout,
                "ADDED NEW LIST!",
                Snackbar.LENGTH_SHORT
            );

            snackbar.show();
        }
    }

    private int getNextPosition() {

        TodoListDataSource source = new TodoListDataSource(this);
        int position = source.getLastTodoList();

       return position == -1 ? 0 : position;
    }
}
