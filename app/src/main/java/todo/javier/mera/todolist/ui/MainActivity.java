package todo.javier.mera.todolist.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.database.TodoListDataSource;
import todo.javier.mera.todolist.fragments.FragmentRecycler;
import todo.javier.mera.todolist.fragments.FragmentTask;
import todo.javier.mera.todolist.fragments.FragmentTodoList;
import todo.javier.mera.todolist.model.ItemBase;
import todo.javier.mera.todolist.model.TodoList;

public class MainActivity extends AppCompatActivity
        implements MainActivityView {

    public static final int TASK_NOTIFICATION_CODE = 10;

    private FragmentHelper mFragmentHelper;
    private MainActivityPresenter mPresenter;

    @BindView(R.id.toolbar)
    Toolbar mToolBar;

    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout mCoordinatorLayout;

    private static final String FRAGMENT_TAG = "fragment_recycler";
    private FragmentRecycler mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPresenter = new MainActivityPresenter(this);
        mFragmentHelper = new FragmentHelper(getSupportFragmentManager());
        ButterKnife.bind(this);

        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.mipmap.ic_sort);
        mToolBar.setOverflowIcon(drawable);

        // For some reason if the title is not initially set to something, when the Fragment calls
        // for the first time to set the title, the title will not be changed.
        mPresenter.setToolbarTitle("");

        mPresenter.setToolbar();
        mPresenter.toggleBackButton(false);

        if(savedInstanceState != null) {

            mCurrentFragment = (FragmentRecycler) mFragmentHelper.findFragment(FRAGMENT_TAG);
            mPresenter.toggleBackButton(true);
        }
        else {

            Intent intent = getIntent();

            Bundle bundle = intent.getBundleExtra("bundle");
            if(bundle == null) {

                mCurrentFragment = FragmentTodoList.newInstance();
            }
            else {

                String listId = bundle.getString("ID");
                TodoListDataSource source = new TodoListDataSource(this);
                TodoList todoList = source.readTodoList(listId);
                showFragmentTodoList(todoList);
            }
        }


        mFragmentHelper.replace(R.id.fragmentContainer, mCurrentFragment, FRAGMENT_TAG);
    }

    @Override
    public void onBackPressed() {

        if (mCurrentFragment.isRemovingItems()) {

            mCurrentFragment.resetItems();
            mPresenter.updateToolbarBackground(R.color.colorPrimary);

            if(mCurrentFragment instanceof FragmentTodoList) {

                mPresenter.toggleBackButton(false);
            }
            else {

                mPresenter.toggleBackButton(true);
            }
        }
        else {

            mPresenter.toggleBackButton(false);
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
        mPresenter.toggleBackButton(true);

        mCurrentFragment = FragmentTask.newInstance(todoList);
        mFragmentHelper.replaceWithBackStack(
            R.id.fragmentContainer,
            mCurrentFragment,
            FRAGMENT_TAG,
            null
        );
    }

    @Override
    public void updateToolbarBackground(int color) {

        Drawable newDrawable = ContextCompat.getDrawable(this, color);
        mToolBar.setBackground(newDrawable);
    }

    @Override
    public void toggleBackButton(boolean canDisplay) {

        getSupportActionBar().setDisplayHomeAsUpEnabled(canDisplay);
    }

    @Override
    public void setToolbar() {

        setSupportActionBar(mToolBar);
    }

    @Override
    public void showSnackBar(String message, String action, final Map<Integer, ItemBase> items) {

        Snackbar.make(
            mCoordinatorLayout,
            message,
            Snackbar.LENGTH_LONG
        )
        .setAction(action, new View.OnClickListener() {

            @Override
            public void onClick(View view) {
            FragmentRecycler currentFragment = (FragmentRecycler) mFragmentHelper.findFragment(FRAGMENT_TAG);
            currentFragment.undoItemsDelete(items);
            currentFragment.clearRemovableItems();
            currentFragment.updatePositions();
            }
        })
        .show();
    }

    @Override
    public void setIndicator(int resourceId) {

        getSupportActionBar().setHomeAsUpIndicator(resourceId);
    }
}
