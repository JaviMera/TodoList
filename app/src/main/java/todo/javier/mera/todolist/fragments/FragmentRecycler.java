package todo.javier.mera.todolist.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.animators.FadeInDownAnimator;
import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.adapters.ItemLongClickListener;
import todo.javier.mera.todolist.adapters.RecyclerAdapter;
import todo.javier.mera.todolist.database.TodoListDataSource;
import todo.javier.mera.todolist.fragments.dialogs.FragmentDialogListener;
import todo.javier.mera.todolist.model.ItemBase;
import todo.javier.mera.todolist.model.TodoListTask;
import todo.javier.mera.todolist.ui.MainActivity;

/**
 * Created by javie on 12/5/2016.
 */

public abstract class FragmentRecycler<T extends ItemBase> extends Fragment
    implements FragmentRecyclerView,
    FragmentDialogListener, ItemLongClickListener, ItemClickListener {

    private FragmentRecyclerPresenter mPresenter;

    protected MainActivity mParent;
    protected boolean mIsRemovingItems;

    protected abstract RecyclerAdapter getAdapter();
    protected abstract int getLayout();
    protected abstract String getTitle();
    protected abstract RecyclerView.LayoutManager getLayoutManager(Context context);
    protected abstract T createItem(TodoListDataSource source, String name);
    protected abstract List<T> getAllItems(TodoListDataSource source);
    protected abstract void showItem(T item);

    protected @BindView(R.id.recyclerView) RecyclerView mRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mParent = (MainActivity) getActivity();
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(getLayout(), container, false);

        ButterKnife.bind(this, view);
        mPresenter = new FragmentRecyclerPresenter(this);

        mParent.setToolbarTitle(getTitle());

        mPresenter.setAdapter(this);
        mPresenter.setLayoutManager(mParent);
        mPresenter.setFixedSize(true);

        mPresenter.setItemAnimator(new FadeInDownAnimator(new LinearOutSlowInInterpolator()));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                TodoListDataSource source = new TodoListDataSource(mParent);

                    source.openReadable();
                    List<T> items = getAllItems(source);
                    RecyclerAdapter adapter = (RecyclerAdapter) mRecyclerView.getAdapter();
                    adapter.addItems(items);
                    source.close();
            }
        }, 500);

        return view;
    }

    @Override
    public void setAdapter(Fragment context) {

        RecyclerAdapter adapter = getAdapter();
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void setItemAnimator(RecyclerView.ItemAnimator animator) {

        if(mRecyclerView.getItemAnimator() != animator)
            mRecyclerView.setItemAnimator(animator);
    }

    @Override
    public void setLayoutManager(Context context) {

        RecyclerView.LayoutManager layoutManager = getLayoutManager(mParent);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void setFixedSize(boolean isFixed) {

        mRecyclerView.setHasFixedSize(isFixed);
    }

    @Override
    public void onAddItem(final String title) {

        scrollToLastPosition();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            scrollToLastPosition();
            TodoListDataSource source = new TodoListDataSource(mParent);
            source.openWriteable();
            T item = createItem(source, title);

            RecyclerAdapter adapter = (RecyclerAdapter) mRecyclerView.getAdapter();
            adapter.addItem(item);
            source.close();
            }
        }, 1000);
    }

    @Override
    public void onLongClick(int position) {

        if(!mIsRemovingItems) {

            mIsRemovingItems = true;
            RecyclerAdapter adapter = (RecyclerAdapter) mRecyclerView.getAdapter();
            adapter.setRemovable(position);

            mParent.invalidateOptionsMenu();
        }
    }

    @Override
    public void onClick(int position) {

        RecyclerAdapter adapter = (RecyclerAdapter) mRecyclerView.getAdapter();

        if(mIsRemovingItems) {

            adapter.setRemovable(position);

            int count = adapter.getRemovableCount();
            if(count == 0) {

                mIsRemovingItems = false;
                mParent.invalidateOptionsMenu();
            }
        }
        else {

            T item = (T) adapter.getItem(position);
            showItem(item);
        }
    }

    public void removeItems() {

        TodoListDataSource source = new TodoListDataSource(mParent);
        RecyclerAdapter adapter = (RecyclerAdapter) mRecyclerView.getAdapter();
        List<T> itemsToRemove = adapter.getRemovableItems();

        source.openWriteable();
        int removedCount = source.removeTodoListTask(itemsToRemove.toArray(new TodoListTask[itemsToRemove.size()]));

        if(removedCount > 0){

            adapter.removeItems(itemsToRemove);
        }

        source.close();
    }

    protected int getOrientation(Context context) {

        if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            return LinearLayoutManager.HORIZONTAL;
        }

        return LinearLayoutManager.VERTICAL;
    }

    private void scrollToLastPosition() {

        RecyclerAdapter adapter = (RecyclerAdapter) mRecyclerView.getAdapter();
        int lastPosition = adapter.getItemCount();
        mRecyclerView.smoothScrollToPosition(lastPosition);
    }

    public boolean isRemovingItems() {

        return mIsRemovingItems;
    }

    public void clearRemovableItems() {

        RecyclerAdapter adapter = (RecyclerAdapter) mRecyclerView.getAdapter();
        adapter.clearRemovableItems();
        mIsRemovingItems = false;
        mParent.invalidateOptionsMenu();
    }
}
