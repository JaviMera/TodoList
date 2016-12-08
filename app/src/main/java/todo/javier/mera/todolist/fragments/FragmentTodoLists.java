package todo.javier.mera.todolist.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.Date;
import java.util.List;

import butterknife.OnClick;
import jp.wasabeef.recyclerview.animators.FadeInDownAnimator;
import jp.wasabeef.recyclerview.animators.FlipInTopXAnimator;
import jp.wasabeef.recyclerview.animators.SlideInRightAnimator;
import todo.javier.mera.todolist.adapters.RecyclerAdapter;
import todo.javier.mera.todolist.database.TodoListDataSource;
import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.adapters.TodolistAdapter;
import todo.javier.mera.todolist.fragments.dialogs.FragmentDialogTask;
import todo.javier.mera.todolist.fragments.dialogs.FragmentDialogTodoList;
import todo.javier.mera.todolist.model.TodoList;

public class FragmentTodoLists extends FragmentRecycler<TodoList> {

    private long mNewId;
    private long mCreationDate;

    public static FragmentTodoLists newInstance() {

        return new FragmentTodoLists();
    }

    @Override
    protected RecyclerAdapter getAdapter() {

        return new TodolistAdapter(this);
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_todo_lists;
    }

    @Override
    protected String getTitle() {

        return "Home";
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.fragment_todo_list_menu, menu);

        if(mIsRemovingItems) {

            menu.findItem(R.id.action_delete_task).setVisible(true);
        }
        else {

            menu.findItem(R.id.action_delete_task).setVisible(false);
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mIsRemovingItems = false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {

            case R.id.action_delete_task:

                setItemAnimator(new SlideInRightAnimator());
                removeItems();

                mIsRemovingItems = false;
                mParent.invalidateOptionsMenu();

                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager(Context context) {

        int orientation = getOrientation(context);
        return new LinearLayoutManager(context, orientation, false);
    }

    @Override
    protected TodoList createItem(TodoListDataSource source, String name) {

        setItemAnimator(new FlipInTopXAnimator());

        long creationDate = new Date().getTime();

        return source.createTodoList(
            name,
            creationDate
        );
    }

    @Override
    protected List<TodoList> getAllItems(TodoListDataSource source) {

        return source.readTodoLists();
    }

    @Override
    protected void showItem(TodoList item) {

        mParent.showFragmentTodoList(item);
    }

    @OnClick(R.id.fab)
    public void onAddListButtonClick(View view) {

        FragmentDialogTodoList dialogTodoList = new FragmentDialogTodoList();
        dialogTodoList.setTargetFragment(this, 1);
        dialogTodoList.show(mParent.getSupportFragmentManager(), "dialog_todolists");
    }

    private void addTodoList(TodoList todoList) {

        TodolistAdapter adapter = (TodolistAdapter) mRecyclerView.getAdapter();
        adapter.addItem(todoList);
    }
}

