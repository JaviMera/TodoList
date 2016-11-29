package todo.javier.mera.todolist;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.ButterKnife;
import todo.javier.mera.todolist.dialogs.AddTodoListDialogFragment;
import todo.javier.mera.todolist.dialogs.DialogView;
import todo.javier.mera.todolist.fragments.FragmentHome;

public class MainActivity extends AppCompatActivity
    implements DialogView {

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
            .add(R.id.fragmentContainer, fragment, FRAGMENT_HOME_TAG)
            .commit();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddTodoListDialogFragment fragment = new AddTodoListDialogFragment();
                fragment.show(getSupportFragmentManager(), "add_list_dialog");

//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
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
    public void onAddTodoList(String todoListTitle) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentHome fragment = (FragmentHome) fragmentManager.findFragmentByTag(FRAGMENT_HOME_TAG);

        if(null != fragment) {

            fragment.addTodoList(todoListTitle);
        }
    }
}
