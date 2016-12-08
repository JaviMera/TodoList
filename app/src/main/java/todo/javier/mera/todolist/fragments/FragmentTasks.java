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
import android.widget.Toast;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import butterknife.OnClick;
import jp.wasabeef.recyclerview.animators.FadeInDownAnimator;
import jp.wasabeef.recyclerview.animators.SlideInRightAnimator;
import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.adapters.ItemLongClickListener;
import todo.javier.mera.todolist.adapters.RecyclerAdapter;
import todo.javier.mera.todolist.adapters.TodoListTaskAdapter;
import todo.javier.mera.todolist.database.TodoListDataSource;
import todo.javier.mera.todolist.fragments.dialogs.FragmentDialogTask;
import todo.javier.mera.todolist.fragments.dialogs.FragmentDialogTodoList;
import todo.javier.mera.todolist.model.TodoList;
import todo.javier.mera.todolist.model.TodoListTask;
import todo.javier.mera.todolist.model.TaskStatus;

/**
 * Created by javie on 12/2/2016.
 */

public class FragmentTasks extends FragmentRecycler<TodoListTask> {

    public static final String TODO_LISt = "TODO_LISt";
    private TodoList mTodoList;

    public static FragmentTasks newInstance(TodoList todoList) {

        FragmentTasks fragment = new FragmentTasks();
        Bundle bundle = new Bundle();
        bundle.putParcelable(TODO_LISt, todoList);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTodoList = getArguments().getParcelable(TODO_LISt);
        mIsRemovingItems = false;
    }

    @Override
    protected int getDeleteTitle() {

        return R.string.menu_delete_task;
    }

    @Override
    protected int removeItems(TodoListDataSource source, List<TodoListTask> itemsToRemove) {

        TodoListTask[] items = itemsToRemove.toArray(new TodoListTask[itemsToRemove.size()]);
        return source.removeTodoListTask(items);
    }

    @OnClick(R.id.fab)
    public void onAddListButtonClick(View view) {

        DialogFragment dialogFragment = new FragmentDialogTask();
        dialogFragment.setTargetFragment(this, 1);
        dialogFragment.show(mParent.getSupportFragmentManager(), "dialog_task");
    }

    @Override
    protected RecyclerAdapter getAdapter() {

        return new TodoListTaskAdapter(this);
    }

    @Override
    protected String getTitle() {

        return mTodoList.getTitle();
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager(Context context) {

        return new LinearLayoutManager(context);
    }

    @Override
    protected List<TodoListTask> getAllItems(TodoListDataSource source) {

        return source.readTodoListTasks(mTodoList.getId());
    }

    @Override
    protected TodoListTask createItem(TodoListDataSource source, String name) {

        long creationDate = new Date().getTime();
        TaskStatus status = TaskStatus.Created;

        return source.createTodoListTask(
            mTodoList.getId(),
            name,
            status,
            creationDate
        );
    }

    @Override
    protected void showItem(TodoListTask item) {

        // Todo: add behavior to handle a regular task click
    }
}
