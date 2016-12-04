package todo.javier.mera.todolist.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.fragments.FragmentHome;
import todo.javier.mera.todolist.fragments.FragmentTodoList;
import todo.javier.mera.todolist.model.TodoList;

public class MainActivity extends AppCompatActivity
    implements ActivityView {

    public static final String FRAGMENT_HOME_TAG = "fragment_home";
    private static final String FRAGMENT_TODO_LIST = "fragment_todo_list";

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

        Fragment fragment = FragmentHome.newInstance();
        mFragmentHelper.replace(R.id.fragmentContainer, fragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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

        Fragment fragment = FragmentTodoList.newInstance(todoList);
        mFragmentHelper.replaceWithBackStack(
            R.id.fragmentContainer,
            fragment,
            null
        );
    }
}
