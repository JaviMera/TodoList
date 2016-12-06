package todo.javier.mera.todolist.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import jp.wasabeef.recyclerview.animators.FlipInTopXAnimator;
import todo.javier.mera.todolist.adapters.RecyclerAdapter;
import todo.javier.mera.todolist.database.TodoListDataSource;
import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.adapters.TodolistAdapter;
import todo.javier.mera.todolist.fragments.dialogs.FragmentDialogListener;
import todo.javier.mera.todolist.fragments.dialogs.FragmentDialogTodoList;
import todo.javier.mera.todolist.model.TodoList;

public class FragmentTodoLists extends FragmentRecycler
    implements TodoListListener, FragmentDialogListener {

    public static final int ANIM_DELAY = 1000;

    public static FragmentTodoLists newInstance() {

        return new FragmentTodoLists();
    }

    @Override
    protected RecyclerAdapter getAdapter() {

        return new TodolistAdapter(this);
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_todo_lists;
    }

    @Override
    protected String getTitle() {

        return "Home";
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager(Context context) {

        int orientation = getOrientation(context);
        return new LinearLayoutManager(context, orientation, false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                TodoListDataSource source = new TodoListDataSource(mParent);
                source.openReadable();
                List<TodoList> todoLists = source.readTodoLists();
                TodolistAdapter adapter = (TodolistAdapter) mRecyclerView.getAdapter();
                for(TodoList tl : todoLists) {

                    adapter.addItem(tl);
                }

                source.close();
            }
        },
        200);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.fragment_home_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @OnClick(R.id.fab)
    public void onAddListButtonClick(View view) {

        FragmentDialogTodoList dialogTodoList = new FragmentDialogTodoList();
        dialogTodoList.setTargetFragment(this, 1);
        dialogTodoList.show(mParent.getSupportFragmentManager(), "dialog_todolists");
    }

    private void scrollToLastPosition() {

        TodolistAdapter adapter = (TodolistAdapter) mRecyclerView.getAdapter();
        int lastPosition = adapter.getItemCount();
        mRecyclerView.smoothScrollToPosition(lastPosition);
    }

    private void addTodoList(TodoList todoList) {

        TodolistAdapter adapter = (TodolistAdapter) mRecyclerView.getAdapter();
        adapter.addItem(todoList);
    }

    @Override
    public void onTodoListClick(TodoList todoList) {

        mParent.showFragmentTodoList(todoList);
    }

    @Override
    public void onAddItem(final String title) {

        scrollToLastPosition();
        // Wait 200ms after the keyboard has been manually closed.
        // This gives a friendlier animation when the user is adding an item to the recycler view
        // and half of the screen is being blocked by the keyboard.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                scrollToLastPosition();

                setItemAnimator(new FlipInTopXAnimator());

                TodoListDataSource dataSource = new TodoListDataSource(mParent);
                long creationDate = new Date().getTime();

                dataSource.openWriteable();
                long newId = dataSource.createTodoList(
                    title,
                    creationDate
                );

                TodoList todoList = new TodoList(newId, title, creationDate);
                addTodoList(todoList);
                dataSource.close();
            }
        },
        ANIM_DELAY);
    }
}

