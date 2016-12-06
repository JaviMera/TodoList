package todo.javier.mera.todolist.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.adapters.RecyclerAdapter;
import todo.javier.mera.todolist.adapters.TodoListItemAdapter;
import todo.javier.mera.todolist.database.TodoListDataSource;
import todo.javier.mera.todolist.model.TodoList;
import todo.javier.mera.todolist.model.TodoListItem;
import todo.javier.mera.todolist.model.TodoListStatus;
import todo.javier.mera.todolist.ui.MainActivity;

/**
 * Created by javie on 12/2/2016.
 */

public class FragmentTodoList extends FragmentRecycler {

    public static final String TODO_LISt = "TODO_LISt";
    private TodoList mTodoList;

    @BindView(R.id.itemNameEditText)
    EditText mNameEditText;

    public static FragmentTodoList newInstance(TodoList todoList) {

        FragmentTodoList fragment = new FragmentTodoList();
        Bundle bundle = new Bundle();
        bundle.putParcelable(TODO_LISt, todoList);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.fragment_todo_list_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTodoList = getArguments().getParcelable(TODO_LISt);
    }

    @OnClick(R.id.fab)
    public void onAddButtonClick(View view) {

        String taskName = mNameEditText.getText().toString();
        int creationDate = (int)new Date().getTime();

        TodoListDataSource source = new TodoListDataSource(mParent);
        source.openWriteable();

        long newId = source.createTodoListItem(
            mTodoList.getId(),
            taskName,
            TodoListStatus.Created,
            creationDate
        );
        source.close();

        TodoListItemAdapter adapter = (TodoListItemAdapter) mRecyclerView.getAdapter();
        adapter.addItem(new TodoListItem(
            newId,
            mTodoList.getId(),
            mNameEditText.getText().toString(),
            TodoListStatus.Created,
            creationDate));
    }

    @Override
    protected RecyclerAdapter getAdapter() {

        return new TodoListItemAdapter(this);
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_todo_list;
    }

    @Override
    protected String getTitle() {

        return mTodoList.getTitle();
    }
}
