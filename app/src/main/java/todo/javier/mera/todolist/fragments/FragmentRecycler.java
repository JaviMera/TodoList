package todo.javier.mera.todolist.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.adapters.RecyclerAdapter;
import todo.javier.mera.todolist.database.TodoListDataSource;
import todo.javier.mera.todolist.ui.MainActivity;

/**
 * Created by javie on 12/5/2016.
 */

public abstract class FragmentRecycler extends Fragment
    implements FragmentRecyclerView{

    private FragmentRecyclerPresenter mPresenter;

    protected MainActivity mParent;
    protected abstract RecyclerAdapter getAdapter();
    protected abstract int getLayout();
    protected abstract String getTitle();
    protected abstract RecyclerView.LayoutManager getLayoutManager(Context context);

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

    protected int getOrientation(Context context) {

        if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            return LinearLayoutManager.HORIZONTAL;
        }

        return LinearLayoutManager.VERTICAL;
    }
}
