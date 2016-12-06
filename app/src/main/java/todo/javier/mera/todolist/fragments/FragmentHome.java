package todo.javier.mera.todolist.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.recyclerview.animators.FlipInTopXAnimator;
import todo.javier.mera.todolist.adapters.RecyclerAdapter;
import todo.javier.mera.todolist.database.TodoListDataSource;
import todo.javier.mera.todolist.ui.MainActivity;
import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.adapters.TodolistAdapter;
import todo.javier.mera.todolist.model.TodoList;
import todo.javier.mera.todolist.ui.ActivityView;

public class FragmentHome extends FragmentRecycler
    implements TodoListListener {

    public static final int ANIM_DELAY = 200;

    private Animation mShakeAnimation;

    @BindView(R.id.itemNameEditText)
    EditText mNameEditText;

    public static FragmentHome newInstance() {

        return new FragmentHome();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mShakeAnimation = AnimationUtils.loadAnimation(context, R.anim.shake);
    }

    @Override
    protected RecyclerAdapter getAdapter() {

        return new TodolistAdapter(this);
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_home;
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

        if(mNameEditText.getText().toString().isEmpty()) {

            String errorText = mParent.getString(R.string.dialog_todo_list_hint_error);
            mNameEditText.setHint(errorText);

            int hintColor = ContextCompat.getColor(mParent, android.R.color.holo_red_light);
            mNameEditText.setHintTextColor(hintColor);
            mNameEditText.startAnimation(mShakeAnimation);
        }
        else {

            mParent.hideKeyboard();

            // Wait 200ms after the keyboard has been manually closed.
            // This gives a friendlier animation when the user is adding an item to the recycler view
            // and half of the screen is being blocked by the keyboard.
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    scrollToLastPosition();

                    String name = mNameEditText.getText().toString();
                    setItemAnimator(new FlipInTopXAnimator());
                    mNameEditText.setText("");

                    int hintColor = ContextCompat.getColor(mParent, android.R.color.darker_gray);
                    mNameEditText.setHintTextColor(hintColor);

                    TodoListDataSource dataSource = new TodoListDataSource(mParent);
                    long creationDate = new Date().getTime();

                    dataSource.openWriteable();
                    long newId = dataSource.createTodoList(
                        name,
                        creationDate
                    );

                    TodoList todoList = new TodoList(newId, name, (int) creationDate);
                    addTodoList(todoList);
                    dataSource.close();
                }
            },
            ANIM_DELAY);
        }
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
}
