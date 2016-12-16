package todo.javier.mera.todolist.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.animation.Animation;

/**
 * Created by javie on 11/29/2016.
 */

class FragmentRecyclerPresenter {

    private FragmentRecyclerView mView;

    FragmentRecyclerPresenter(FragmentRecyclerView view) {

        mView = view;
    }

    void setAdapter(Fragment fragment) {

        mView.setAdapter(fragment);
    }

    void setItemAnimator(RecyclerView.ItemAnimator animator) {

        mView.setItemAnimator(animator);
    }

    void setLayoutManager(Context context) {

        mView.setLayoutManager(context);
    }

    void setFixedSize(boolean isFixed) {

        mView.setFixedSize(isFixed);
    }
}
