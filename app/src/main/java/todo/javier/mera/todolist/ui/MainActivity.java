package todo.javier.mera.todolist.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.fragments.FragmentRecycler;
import todo.javier.mera.todolist.fragments.FragmentTask;
import todo.javier.mera.todolist.fragments.FragmentTodoList;
import todo.javier.mera.todolist.model.TodoList;

public class MainActivity extends AppCompatActivity
    implements MainActivityView {

    private FragmentHelper mFragmentHelper;
    private MainActivityPresenter mPresenter;

    @BindView(R.id.toolbar)
    Toolbar mToolBar;

    @BindView(R.id.fab)
    FloatingActionButton mFab;

    private static final String FRAGMENT_TAG = "fragment_recycler";
    private ObjectAnimator mFabScaleXDown;
    private ObjectAnimator mFabScaleYDown;
    private ObjectAnimator mFabScaleXUp;
    private ObjectAnimator mFabScaleYUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFabScaleXDown = ObjectAnimator.ofFloat(mFab, "scaleX", 1.0f, 0.0f);
        mFabScaleYDown = ObjectAnimator.ofFloat(mFab, "scaleY", 1.0f, 0.0f);
        mFabScaleXUp = ObjectAnimator.ofFloat(mFab, "scaleX", 0.0f, 1.0f);
        mFabScaleYUp = ObjectAnimator.ofFloat(mFab, "scaleY", 0.0f, 1.0f);

        mPresenter = new MainActivityPresenter(this);
        mFragmentHelper = new FragmentHelper(getSupportFragmentManager());
        ButterKnife.bind(this);

        // For some reason if the title is not initially set to something, when the Fragment calls
        // for the first time to set the title, the title will not be changed.
        mPresenter.setToolbarTitle("");

        mPresenter.setToolbar();
        mPresenter.toggleBackButton(false);

        FragmentRecycler fragmentRecycler;

        if(savedInstanceState != null) {

            fragmentRecycler = (FragmentRecycler) mFragmentHelper.findFragment(FRAGMENT_TAG);
            mPresenter.toggleBackButton(true);
        }
        else {

            fragmentRecycler = FragmentTodoList.newInstance();
        }


        mFragmentHelper.replace(R.id.fragmentContainer, fragmentRecycler, FRAGMENT_TAG);
    }

    @Override
    public void onBackPressed() {

        FragmentRecycler fragment = (FragmentRecycler) mFragmentHelper.findFragment(FRAGMENT_TAG);
        if (fragment.isRemovingItems()) {

            fragment.clearRemovableItems();
            mPresenter.updateToolbarBackground(R.color.colorPrimary);
            mPresenter.showFabButton();
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

        Fragment fragment = FragmentTask.newInstance(todoList);
        mFragmentHelper.replaceWithBackStack(
            R.id.fragmentContainer,
            fragment,
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
    public void hideFabButton() {

        AnimatorSet set = new AnimatorSet();
        set.play(mFabScaleXDown).with(mFabScaleYDown);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mPresenter.setFabVisibility(View.INVISIBLE);
            }
        });
        set.start();
    }

    @OnClick(R.id.fab)
    public void onAddListButtonClick(View view) {

        FragmentRecycler fragment = (FragmentRecycler) mFragmentHelper.findFragment(FRAGMENT_TAG);
        fragment.showAddDialog();
        hideFabButton();
    }

    @Override
    public void showFabButton() {

        mPresenter.setFabVisibility(View.VISIBLE);

        AnimatorSet set = new AnimatorSet();
        set.play(mFabScaleXUp).with(mFabScaleYUp);

        set.start();
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
    public void setFabVisibility(int visibility) {

        mFab.setVisibility(visibility);
    }
}
