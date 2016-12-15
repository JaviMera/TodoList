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

import butterknife.BindView;
import butterknife.ButterKnife;
import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.fragments.FragmentRecycler;
import todo.javier.mera.todolist.fragments.FragmentTodoList;
import todo.javier.mera.todolist.fragments.FragmentTask;
import todo.javier.mera.todolist.model.TodoList;

public class MainActivity extends AppCompatActivity
    implements ActivityView,
    NavigationView.OnNavigationItemSelectedListener{

    private FragmentHelper mFragmentHelper;
    private ActionBarDrawerToggle mToggle;

    @BindView(R.id.toolbar) Toolbar mToolBar;

    @BindView(R.id.navigationView)
    NavigationView mNavigationView;

    @BindView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToggle = new ActionBarDrawerToggle(this,
            mDrawerLayout,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        );

        Fragment fragment = FragmentTodoList.newInstance();
        mFragmentHelper.replace(R.id.fragmentContainer, fragment);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        mToggle.syncState();
    }

    @Override
    public void onBackPressed() {

        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)) {

            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
        else {

            FragmentRecycler fragment = (FragmentRecycler) mFragmentHelper.findFragment("fragment_recycler");
            if (fragment.isRemovingItems()) {

                fragment.clearRemovableItems();
                updateToolbarBackground(R.color.colorPrimary);
                return;
            }
            else {

                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {

            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setToolbarTitle(String text) {

        mToolBar.setTitle(text);
    }

    @Override
    public void showFragmentTodoList(TodoList todoList) {

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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
