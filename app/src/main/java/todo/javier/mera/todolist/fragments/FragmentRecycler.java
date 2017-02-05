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
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.animators.FadeInDownAnimator;
import jp.wasabeef.recyclerview.animators.SlideInRightAnimator;
import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.adapters.OnStartDragListener;
import todo.javier.mera.todolist.adapters.RecyclerAdapter;
import todo.javier.mera.todolist.adapters.SimpleItemTouchHelperCallback;
import todo.javier.mera.todolist.model.ItemBase;
import todo.javier.mera.todolist.ui.TodosActivity;

/**
 * Created by javie on 12/5/2016.
 */

public abstract class FragmentRecycler<T extends ItemBase> extends Fragment
    implements FragmentRecyclerView,
    RecyclerItemListener<T>,
    OnStartDragListener{

    private FragmentRecyclerPresenter mPresenter;
    private boolean mIsRemovingItems;
    private Map<Integer, T> mRemovableItems;
    private ItemTouchHelper mHelper;

    protected TodosActivity mParent;
    protected RecyclerAdapter mAdapter;

    protected abstract RecyclerAdapter createAdapter();
    protected abstract String getTitle();
    protected abstract RecyclerView.LayoutManager getLayoutManager(Context context);
    protected abstract List<T> getAllItems();
    protected abstract int getDeleteTitle();
    protected abstract int removeItems(List<T> itemsToRemove);
    protected abstract void updateItemPositions(Map<String, Integer> items);

    public abstract void undoItemsDelete(Map<Integer, T> items);
    public abstract void showAddDialog();

    protected @BindView(R.id.recyclerView) RecyclerView mRecyclerView;

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder, int position) {

        mHelper.startDrag(viewHolder);
        mAdapter.notifyItemMove(position);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mParent = (TodosActivity) getActivity();
        mIsRemovingItems = false;

        // Use a treemap because the order of the map will matter.
        // Items should be ordered by their positions, so if the user selects items 3,0,1
        // then the map should have them as 0,1,3 to avoid having an Index exception when re-inserting items.
        mRemovableItems = new TreeMap<>();

        setHasOptionsMenu(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recycler, container, false);

        ButterKnife.bind(this, view);
        mParent.setToolbarTitle(getTitle());

        mPresenter = new FragmentRecyclerPresenter(this);
        mPresenter.setAdapter(this);
        mPresenter.setLayoutManager(mParent);
        mPresenter.setFixedSize(true);

        mPresenter.setItemAnimator(new FadeInDownAnimator(new LinearOutSlowInInterpolator()));

        // Make the initial addition of items a bit slower for better user experience
        mRecyclerView.getItemAnimator().setAddDuration(300);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mAdapter);
        mHelper = new ItemTouchHelper(callback);
        mHelper.attachToRecyclerView(mRecyclerView);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            List<T> items = getAllItems();
            mAdapter.addItems(items);
            }
        }, 500);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.fragment_recycler_menu, menu);
        MenuItem addItem = menu.findItem(R.id.action_add);
        MenuItem deleteItem = menu.findItem(R.id.action_delete);
        MenuItem sortItem = menu.findItem(R.id.action_sort);

        deleteItem.setTitle(getDeleteTitle());

        if(mIsRemovingItems) {

            addItem.setVisible(false);
            deleteItem.setVisible(true);
            sortItem.setVisible(false);
        }
        else {

            addItem.setVisible(true);
            deleteItem.setVisible(false);
            sortItem.setVisible(true);
        }

        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {

            case R.id.action_add:

                showAddDialog();
                break;

            case R.id.action_delete:

                if(mAdapter.isEmpty()) {

                    Toast.makeText(mParent, "You have no items to delete.", Toast.LENGTH_SHORT).show();
                    return true;
                }

                setItemAnimator(new SlideInRightAnimator());

                mIsRemovingItems = false;

                mParent.invalidateOptionsMenu();

                // Check if no items were selected to be removed
                if(mRemovableItems.isEmpty()) {

                    Toast.makeText(mParent, "No items selected.", Toast.LENGTH_SHORT).show();
                    return true;
                }

                // Remove items from the fragment's adapter
                mAdapter.removeItems();

                // Remove items from the database
                removeItems(new ArrayList(mRemovableItems.values()));

                // Update the position of the items left in the list
                updatePositions();

                mParent.updateToolbarBackground(R.color.colorPrimary);
                mParent.showSnackBar("Items deleted", "Undo", (Map<Integer, ItemBase>) mRemovableItems);
                mParent.canDisplayToggleButton(this);
                mAdapter.notifyItemRangeChanged(0, mAdapter.getItemCount());

                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    public void updatePositions() {

        List<T> currentItems = mAdapter.getItems();
        Map<String, Integer> itemsMap = new LinkedHashMap<>();

        for(int position = 0 ; position < currentItems.size() ; position++) {

            itemsMap.put(currentItems.get(position).getId(), position);
        }

        updateItemPositions(itemsMap);
    }



    @Override
    public void setAdapter(Fragment context) {

        mAdapter = createAdapter();
        mRecyclerView.setAdapter(mAdapter);
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

        if(!mIsRemovingItems) {

            mIsRemovingItems = true;
            mParent.updateToolbarBackground(R.color.remove_color_light);
            mParent.toggleBackButton(true);
            mAdapter.notifyItemRangeChanged(0, mAdapter.getItemCount());
            mAdapter.setRemovable(position, true);
            mRemovableItems.put(position, (T)mAdapter.getItem(position));
            mParent.invalidateOptionsMenu();
        }
    }

    @Override
    public void onClick(int position) {

        if(mIsRemovingItems) {

            if(mRemovableItems.containsKey(position)) {

                mRemovableItems.remove(position);
                mAdapter.setRemovable(position, false);
            }
            else {

                mRemovableItems.put(position, (T) mAdapter.getItem(position));
                mAdapter.setRemovable(position, true);
            }

            int count = mAdapter.getRemovableCount();
            if(count == 0) {

                mIsRemovingItems = false;
                mParent.invalidateOptionsMenu();
                mParent.updateToolbarBackground(R.color.colorPrimary);
                mParent.setIndicator(0);

                if(this instanceof FragmentTodoList) {

                    mParent.toggleBackButton(false);
                }

                mAdapter.notifyItemRangeChanged(0, mAdapter.getItemCount());
            }
        }
        else {

            T item = (T) mAdapter.getItem(position);
            showModifyDialog(item);
        }
    }

    protected abstract void showModifyDialog(T item);

    @Override
    public void onItemsUpdate(Map<String, Integer> items) {

        updateItemPositions(items);
    }

    protected int getOrientation(Context context) {

        if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            return LinearLayoutManager.HORIZONTAL;
        }

        return LinearLayoutManager.VERTICAL;
    }

    public boolean isRemovingItems() {

        return mIsRemovingItems;
    }

    public void resetItems() {

        mIsRemovingItems = false;
        mParent.invalidateOptionsMenu();
        mAdapter.resetItems();

        clearRemovableItems();
    }

    public void clearRemovableItems() {

        mRemovableItems.clear();
    }
}
