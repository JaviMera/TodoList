package todo.javier.mera.todolist.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.fragments.FragmentRecycler;
import todo.javier.mera.todolist.fragments.FragmentTodoLists;
import todo.javier.mera.todolist.fragments.FragmentTasks;
import todo.javier.mera.todolist.model.TodoList;

public class MainActivity extends AppCompatActivity
    implements ActivityView {

    public static final String FRAGMENT_HOME_TAG = "fragment_recycler";
    private static final String FRAGMENT_TODO_LIST = "fragment_tasks";

    private FragmentHelper mFragmentHelper;

    @BindView(R.id.toolbar) Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragmentHelper = new FragmentHelper(getSupportFragmentManager());
        ButterKnife.bind(this);

        // For some reason if the title is not initially set to something, when the Fragment calls
        // for the first time to set the title, the title will not be changed.
        mToolBar.setTitle("");

        setSupportActionBar(mToolBar);

        Fragment fragment = FragmentTodoLists.newInstance();
        mFragmentHelper.replace(R.id.fragmentContainer, fragment);
    }

    @Override
    public void onBackPressed() {

        FragmentRecycler fragment = (FragmentRecycler) mFragmentHelper.findFragment("fragment_recycler");
        if(fragment.isRemovingItems()) {

            fragment.clearRemovableItems();
            return;
        }

        super.onBackPressed();
    }

    @Override
    public void setToolbarTitle(String text) {

        mToolBar.setTitle(text);
    }

    @Override
    public void hideKeyboard() {

        // Hide the keyboard when adding a list
        // If not hidden, it interferes with updating the recycler view and sometimes the added
        // item is not drawn on the screen
        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        View view = getCurrentFocus();
        if(view != null) {

            manager.hideSoftInputFromWindow(
                view.getWindowToken(),
                0
            );
        }
    }

    @Override
    public void showFragmentTodoList(TodoList todoList) {

        Fragment fragment = FragmentTasks.newInstance(todoList);
        mFragmentHelper.replaceWithBackStack(
            R.id.fragmentContainer,
            fragment,
            null
        );
    }
}
