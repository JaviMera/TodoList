package todo.javier.mera.todolist.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import todo.javier.mera.todolist.R;

/**
 * Created by javie on 12/2/2016.
 */

public class TodoListActivity extends AppCompatActivity {

    public static final String TODO_LIST_TITLE = "TODO_LIST_TITLE";

    @BindView(R.id.todoTitleView)
    TextView mTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        ButterKnife.bind(this);

        mTitle.setText(getIntent().getStringExtra(TODO_LIST_TITLE));
    }
}
