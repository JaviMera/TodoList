package todo.javier.mera.todolist.fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
/**
 * Created by javie on 11/29/2016.
 */

public class FragmentRecyclerPresenter {

    private FragmentRecyclerView mView;

    public FragmentRecyclerPresenter(FragmentRecyclerView view) {

        mView = view;
    }

    public void setAdapter(Context context) {

        mView.setAdapter(context);
    }

    public void setItemAnimator(RecyclerView.ItemAnimator animator) {

        mView.setItemAnimator(animator);
    }

    public void setLayoutManager(Context context, int orientation) {

        mView.setLayoutManager(context, orientation);
    }

    public void setFixedSize(boolean isFixed) {

        mView.setFixedSize(isFixed);
    }

    public void updateEditTextHintColor(Context context, int colorId) {

        mView.updateEditTextHintColor(context, colorId);
    }
}
