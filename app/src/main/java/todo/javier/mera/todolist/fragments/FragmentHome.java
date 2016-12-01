package todo.javier.mera.todolist.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.recyclerview.animators.FlipInTopXAnimator;
import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.adapters.TodolistAdapterPortrait;
import todo.javier.mera.todolist.model.TodoList;

public class FragmentHome extends Fragment
    implements FragmentRecyclerView{

    private FragmentActivity mParent;
    private FragmentRecyclerPresenter mPresenter;

    @BindView(R.id.todoListsRecyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.recyclerViewEmptyText)
    TextView mRecyclerEmptyText;

    @BindView(R.id.itemNameEditText)
    EditText mNameEditText;

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

        mPresenter.setAdapter(mParent);
        mPresenter.setLayoutManager(mParent);
        mPresenter.setFixedSize(true);

        return view;
    }

    @OnClick(R.id.fab)
    public void onAddListButtonClick(View view) {

        String name = mNameEditText.getText().toString();

        if(name.isEmpty()) {

            mNameEditText.setHint(R.string.dialog_todo_list_hint_error);

            int errorColor = ContextCompat.getColor(mParent, android.R.color.holo_red_light);
            mNameEditText.setHintTextColor(errorColor);
            Animation shake = AnimationUtils.loadAnimation(mParent, R.anim.shake);
            mNameEditText.startAnimation(shake);
        }
        else {

            if(mRecyclerEmptyText.getVisibility() == View.VISIBLE) {

                mRecyclerEmptyText.setVisibility(View.INVISIBLE);
            }

            // Hide the keyboard when adding a list
            // If not hidden. it interferes with updating the recycler view and sometimes the added
            // item is not drawn on the screen
            InputMethodManager manager = (InputMethodManager) mParent.getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(mParent.getCurrentFocus().getWindowToken(), 0);

            scrollToLastPosition();

            addTodoList(name);
            mNameEditText.getText().clear();

            int hintColor = ContextCompat.getColor(mParent, android.R.color.darker_gray);
            mNameEditText.setHintTextColor(hintColor);
        }
    }

    @Override
    public void setAdapter(Context context) {
        TodolistAdapterPortrait adapter = new TodolistAdapterPortrait(this);
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

    private void scrollToLastPosition() {

        TodolistAdapterPortrait adapter = (TodolistAdapterPortrait) mRecyclerView.getAdapter();
        int lastPosition = adapter.getItemCount();
        mRecyclerView.smoothScrollToPosition(lastPosition);
    }

    private void addTodoList(String todoListTitle) {

        TodoList todoList = new TodoList(todoListTitle);
        TodolistAdapterPortrait adapter = (TodolistAdapterPortrait) mRecyclerView.getAdapter();
        adapter.addItem(todoList);
    }
}
