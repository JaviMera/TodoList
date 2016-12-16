package todo.javier.mera.todolist.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.fragments.FragmentRecycler;
import todo.javier.mera.todolist.fragments.FragmentTodoList;
import todo.javier.mera.todolist.fragments.FragmentTask;
import todo.javier.mera.todolist.fragments.dialogs.DialogTodoList;
import todo.javier.mera.todolist.fragments.dialogs.DialogTodoListListener;
import todo.javier.mera.todolist.model.TodoList;

public class MainActivity extends AppCompatActivity
    implements ActivityView {

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        Fragment fragment = FragmentTodoList.newInstance();
        mFragmentHelper.replace(R.id.fragmentContainer, fragment);
    }

    @Override
    public void onBackPressed() {

        FragmentRecycler fragment = (FragmentRecycler) mFragmentHelper.findFragment("fragment_recycler");
        if (fragment.isRemovingItems()) {

            fragment.clearRemovableItems();
            updateToolbarBackground(R.color.colorPrimary);
            return;
        }
        else {

            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {

            case android.R.id.home:

                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setToolbarTitle(String text) {

        mToolBar.setTitle(text);
    }

    @Override
    public void showFragmentTodoList(TodoList todoList) {

        // When the user selects a list, disply the back button also on the top left of the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Fragment fragment = FragmentTask.newInstance(todoList);
        mFragmentHelper.replaceWithBackStack(
            R.id.fragmentContainer,
            fragment,
            null
        );
    }

    @Override
    public void updateToolbarBackground(int color) {

        Drawable newDrawable = ContextCompat.getDrawable(this, color);
        mToolBar.setBackground(newDrawable);
    }

    @OnClick(R.id.fab)
    public void onAddListButtonClick(View view) {

        FragmentRecycler fragment = (FragmentRecycler) mFragmentHelper.findFragment("fragment_recycler");
        fragment.showAddDialog();
    }
}
