package todo.javier.mera.todolist.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.BinderThread;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.adapters.TodoListAdapter;
import todo.javier.mera.todolist.model.TodoList;

public class FragmentHome extends Fragment {

    private FragmentActivity mParent;

    @BindView(R.id.todoListsRecyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.recyclerViewEmptyText)
    TextView mRecyclerEmptyText;

    public static FragmentHome newInstance() {

        FragmentHome fragment = new FragmentHome();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mParent = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ButterKnife.bind(this, view);

        TodoListAdapter adapter = new TodoListAdapter(mParent);
        mRecyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(
            mParent,
            2,
            LinearLayoutManager.VERTICAL,
            false
        );

        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);

        return view;
    }

    public void addTodoList(String todoListTitle) {

        if(mRecyclerEmptyText.getVisibility() == View.VISIBLE) {

            mRecyclerEmptyText.setVisibility(View.INVISIBLE);
        }

        TodoList todoList = new TodoList(todoListTitle);
        TodoListAdapter adapter = (TodoListAdapter) mRecyclerView.getAdapter();
        adapter.addTodoList(todoList);
    }
}
