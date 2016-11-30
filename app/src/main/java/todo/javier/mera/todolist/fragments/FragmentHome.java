package todo.javier.mera.todolist.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.animators.FlipInTopXAnimator;
import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.adapters.TodolistAdapterPortrait;
import todo.javier.mera.todolist.adapters.TodolistViewHolderPortrait;
import todo.javier.mera.todolist.model.TodoList;

public class FragmentHome extends Fragment
    implements FragmentRecyclerView{

    private FragmentActivity mParent;
    private FragmentRecyclerPresenter mPresenter;

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
        mPresenter = new FragmentRecyclerPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ButterKnife.bind(this, view);

        RecyclerView.ItemAnimator animator = new FlipInTopXAnimator();
        mPresenter.setItemAnimator(animator);

        mPresenter.setAdapter(mParent);
        mPresenter.setLayoutManager(mParent);
        mPresenter.setFixedSize(true);

        return view;
    }

    public void addTodoList(String todoListTitle) {

        if(mRecyclerEmptyText.getVisibility() == View.VISIBLE) {

            mRecyclerEmptyText.setVisibility(View.INVISIBLE);
        }

        TodoList todoList = new TodoList(todoListTitle);
        TodolistAdapterPortrait adapter = (TodolistAdapterPortrait) mRecyclerView.getAdapter();
        int scrollto = adapter.addItem(todoList);
        mRecyclerView.smoothScrollToPosition(scrollto);
    }

    @Override
    public void setAdapter(Context context) {
        TodolistAdapterPortrait adapter = new TodolistAdapterPortrait(context);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void setItemAnimator(RecyclerView.ItemAnimator animator) {

        mRecyclerView.setItemAnimator(animator);
    }

    @Override
    public void setLayoutManager(Context context) {

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(
            context,
            1,
            LinearLayoutManager.VERTICAL,
            false
        );

        mRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void setFixedSize(boolean isFixed) {

        mRecyclerView.setHasFixedSize(isFixed);
    }
}
