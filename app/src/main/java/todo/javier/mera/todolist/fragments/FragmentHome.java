package todo.javier.mera.todolist.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.recyclerview.animators.FlipInTopXAnimator;
import todo.javier.mera.todolist.ui.MainActivity;
import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.adapters.TodolistAdapter;
import todo.javier.mera.todolist.model.TodoList;
import todo.javier.mera.todolist.ui.ParentView;

public class FragmentHome extends Fragment
    implements FragmentRecyclerView,
    TodoListListener {

    private FragmentActivity mParent;
    private FragmentRecyclerPresenter mPresenter;
    private Animation mShakeAnimation;
    private String mTitle;

    @BindView(R.id.todoListsRecyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.itemNameEditText)
    EditText mNameEditText;


    public static FragmentHome newInstance() {

        return new FragmentHome();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mParent = getActivity();
        mShakeAnimation = AnimationUtils.loadAnimation(mParent, R.anim.shake);
        mPresenter = new FragmentRecyclerPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ButterKnife.bind(this, view);

        mPresenter.setAdapter(this);
        mPresenter.setLayoutManager(mParent, getOrientation(mParent));
        mPresenter.setFixedSize(true);

        mTitle = "Home";
        ((MainActivity)mParent).setToolbarTitle(mTitle);

        return view;
    }

    @OnClick(R.id.fab)
    public void onAddListButtonClick(View view) {

        String name = mNameEditText.getText().toString();

        if(name.isEmpty()) {

            String errorText = mParent.getString(R.string.dialog_todo_list_hint_error);
            mPresenter.updateEditTextHint(errorText);

            mPresenter.updateEditTextHintColor(mParent, android.R.color.holo_red_light);
            mPresenter.startEditTextAnim(mShakeAnimation);
        }
        else {

            ((ParentView)mParent).hideKeyboard();

            scrollToLastPosition();

            mPresenter.setItemAnimator(new FlipInTopXAnimator());
            mPresenter.updateEditText("");
            mPresenter.updateEditTextHintColor(mParent, android.R.color.darker_gray);

            addTodoList(name);
        }
    }

    @Override
    public void setAdapter(Fragment fragment) {
        TodolistAdapter adapter = new TodolistAdapter(fragment);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void setItemAnimator(RecyclerView.ItemAnimator animator) {

        if(mRecyclerView.getItemAnimator() != animator)
            mRecyclerView.setItemAnimator(animator);
    }

    @Override
    public void setLayoutManager(Context context, int orientation) {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(
            context, orientation, false);

        mRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void setFixedSize(boolean isFixed) {

        mRecyclerView.setHasFixedSize(isFixed);
    }

    @Override
    public void updateEditTextHintColor(Context context, int colorId) {

        int hintColor = ContextCompat.getColor(context, colorId);
        mNameEditText.setHintTextColor(hintColor);
    }

    @Override
    public void updateEditText(String text) {

        mNameEditText.setText(text);
    }

    @Override
    public void updateEditTextHint(String text) {

        mNameEditText.setHint(text);
    }

    @Override
    public void startEditTextAnim(Animation anim) {

        mNameEditText.startAnimation(anim);
    }

    private void scrollToLastPosition() {

        TodolistAdapter adapter = (TodolistAdapter) mRecyclerView.getAdapter();
        int lastPosition = adapter.getItemCount();
        mRecyclerView.smoothScrollToPosition(lastPosition);
    }

    private void addTodoList(String todoListTitle) {

        TodoList todoList = new TodoList(todoListTitle);
        TodolistAdapter adapter = (TodolistAdapter) mRecyclerView.getAdapter();
        adapter.addItem(todoList);
    }

    private int getOrientation(Context context) {

        if(mParent.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            return LinearLayoutManager.HORIZONTAL;
        }

        return LinearLayoutManager.VERTICAL;
    }

    @Override
    public void onTodoListClick(TodoList todoList) {

        ((ParentView)mParent).showFragmentTodoList(todoList);
    }
}
