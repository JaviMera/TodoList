package todo.javier.mera.todolist.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.ui.MainActivity;

/**
 * Created by javie on 12/5/2016.
 */

public abstract class FragmentRecycler extends Fragment {

    protected MainActivity mParent;

    @BindView(R.id.todoListItemsRecyclerView)
    RecyclerView mRecyclerView;

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

        mParent.setToolbarTitle(getTitle());

        return view;
    }


    protected abstract int getLayout();
    protected abstract String getTitle();
}
