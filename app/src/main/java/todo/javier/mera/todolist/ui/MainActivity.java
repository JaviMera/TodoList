package todo.javier.mera.todolist.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.fragments.FragmentHome;

public class MainActivity extends AppCompatActivity
    implements ParentView {

    public static final String FRAGMENT_HOME_TAG = "FRAGMENT_HOME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.home_text));

        setSupportActionBar(toolbar);

        Fragment fragment = FragmentHome.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction
            .replace(R.id.fragmentContainer, fragment, FRAGMENT_HOME_TAG)
            .commit();
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
    public void hideKeyboard() {

        // Hide the keyboard when adding a list
        // If not hidden. it interferes with updating the recycler view and sometimes the added
        // item is not drawn on the screen
        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public void startActivityWithTransition(View viewToAnimate) {

        Intent intent = new Intent(this, TodoListActivity.class);
        intent.putExtra(
            TodoListActivity.TODO_LIST_TITLE,
            ((TextView)viewToAnimate).getText().toString()
        );

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            viewToAnimate,
            getString(R.string.todo_list_transition_name)
        );

        startActivity(intent, options.toBundle());
    }
}
