package todo.javier.mera.todolist.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.animators.FadeInDownAnimator;
import jp.wasabeef.recyclerview.animators.SlideInRightAnimator;
import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.adapters.RecyclerAdapter;
import todo.javier.mera.todolist.adapters.SimpleItemTouchHelperCallback;
import todo.javier.mera.todolist.model.ItemBase;
import todo.javier.mera.todolist.ui.MainMainActivity;

/**
 * Created by javie on 12/5/2016.
 */

public abstract class FragmentRecycler<T extends ItemBase> extends Fragment
    implements FragmentRecyclerView,
    RecyclerItemListener<T> {

    private FragmentRecyclerPresenter mPresenter;
    private boolean mIsRemovingItems;

    protected MainMainActivity mParent;

    protected abstract RecyclerAdapter getAdapter();
    protected abstract String getTitle();
    protected abstract RecyclerView.LayoutManager getLayoutManager(Context context);
    protected abstract List<T> getAllItems();
    protected abstract void showItem(T item);
    protected abstract int getDeleteTitle();
    protected abstract int removeItems(List<T> itemsToRemove);
    protected abstract void onUpdatePosition(List<T> items);
    public abstract void showAddDialog();

    protected @BindView(R.id.recyclerView) RecyclerView mRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mParent = (MainMainActivity) getActivity();
        mIsRemovingItems = false;
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recycler, container, false);

        ButterKnife.bind(this, view);
        mPresenter = new FragmentRecyclerPresenter(this);

        mParent.setToolbarTitle(getTitle());

        mPresenter.setAdapter(this);
        mPresenter.setLayoutManager(mParent);
        mPresenter.setFixedSize(true);

        mPresenter.setItemAnimator(new FadeInDownAnimator(new LinearOutSlowInInterpolator()));

        RecyclerAdapter adapter = (RecyclerAdapter) mRecyclerView.getAdapter();
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        ItemTouchHelper mHelper = new ItemTouchHelper(callback);
        mHelper.attachToRecyclerView(mRecyclerView);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            List<T> items = getAllItems();
            RecyclerAdapter adapter = (RecyclerAdapter) mRecyclerView.getAdapter();
            adapter.addItems(items);
            }
        }, 500);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.fragment_recycler_menu, menu);
        MenuItem deleteItem = menu.findItem(R.id.action_delete);
        deleteItem.setTitle(getDeleteTitle());

        if(mIsRemovingItems) {

            deleteItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
        else {

            deleteItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        }

        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {

            case R.id.action_delete:

                RecyclerAdapter adapter = (RecyclerAdapter) mRecyclerView.getAdapter();
                if(adapter.getItemCount() == 0) {

                    Toast.makeText(mParent, "You have no items to delete.", Toast.LENGTH_SHORT).show();
                    return true;
                }

                if(!mIsRemovingItems) {

                    mIsRemovingItems = true;
                    mParent.updateToolbarBackground(R.color.remove_color_light);
                    mParent.hideFabButton();
                    adapter.notifyItemRangeChanged(0, adapter.getItemCount());
                }
                else {

                    setItemAnimator(new SlideInRightAnimator());

                    Map<Integer, T> items = adapter.removeItems();

                    // Check if no items were selected to be removed
                    if(items.isEmpty()) {

                        Toast.makeText(mParent, "No items selected.", Toast.LENGTH_SHORT).show();
                        return true;
                    }

                    // Remove items from either Task or TodoList Fragment
                    removeItems(new ArrayList(items.values()));
                    mParent.updateToolbarBackground(R.color.colorPrimary);

                    mIsRemovingItems = false;
                    mParent.showFabButton();
                    mParent.showSnackBar("Items deleted", "Undo", (Map<Integer, ItemBase>) items);
                }

                mParent.invalidateOptionsMenu();

                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
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
    public void onLongClick(int position) {

        // Don't allow the user to drag items while they are selecting items to be removed.
        if(mIsRemovingItems) {

            return;
        }

        RecyclerAdapter adapter = (RecyclerAdapter) mRecyclerView.getAdapter();
        adapter.changeItemColor(position);
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
                mParent.updateToolbarBackground(R.color.colorPrimary);
                adapter.notifyItemRangeChanged(0, adapter.getItemCount());
            }
        }
        else {

            T item = (T) adapter.getItem(position);
            showItem(item);
        }
    }

    @Override
    public void onItemsUpdate(List<T> items) {

        onUpdatePosition(items);
    }

    protected int getOrientation(Context context) {

        if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            return LinearLayoutManager.HORIZONTAL;
        }

        return LinearLayoutManager.VERTICAL;
    }

    protected void scrollToLastPosition() {

        RecyclerAdapter adapter = (RecyclerAdapter) mRecyclerView.getAdapter();
        int lastPosition = adapter.getItemCount();
        mRecyclerView.smoothScrollToPosition(lastPosition);
    }

    public boolean isRemovingItems() {

        return mIsRemovingItems;
    }

    public void clearRemovableItems() {

        // Assign a default animator when the user clears out all selected items to be removed.
        // This will make the items visually change back to normal without reloading the list.
        setItemAnimator(new DefaultItemAnimator());

        RecyclerAdapter adapter = (RecyclerAdapter) mRecyclerView.getAdapter();
        adapter.clearRemovableItems();
        mIsRemovingItems = false;
        mParent.invalidateOptionsMenu();
    }

    public abstract void undoItemsDelete(Map<Integer, T> items);
}
