package todo.javier.mera.todolist.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.model.TodoList;
import todo.javier.mera.todolist.ui.MainActivity;

/**
 * Created by javie on 12/2/2016.
 */

public class FragmentTodoList extends Fragment {

    public static final String TODO_LISt = "TODO_LISt";
    private FragmentActivity mParent;
    private TodoList mTodoList;

    public static FragmentTodoList newInstance(TodoList todoList) {

        FragmentTodoList fragment = new FragmentTodoList();
        Bundle bundle = new Bundle();
        bundle.putParcelable(TODO_LISt, todoList);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mParent = getActivity();
        mTodoList = getArguments().getParcelable(TODO_LISt);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_todo_list, container, false);

        ButterKnife.bind(this, view);

        ((MainActivity)mParent).setToolbarTitle(mTodoList.getTitle());

        return view;
    }
}
