package todo.javier.mera.todolist.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.animation.Animation;

/**
 * Created by javie on 11/29/2016.
 */

public class FragmentRecyclerPresenter {

    private FragmentRecyclerView mView;

    public FragmentRecyclerPresenter(FragmentRecyclerView view) {

        mView = view;
    }

    public void setAdapter(Fragment fragment) {

        mView.setAdapter(fragment);
    }

    public void setItemAnimator(RecyclerView.ItemAnimator animator) {

        mView.setItemAnimator(animator);
    }

    public void setLayoutManager(Context context) {

        mView.setLayoutManager(context);
    }

    public void setFixedSize(boolean isFixed) {

        mView.setFixedSize(isFixed);
    }
}
