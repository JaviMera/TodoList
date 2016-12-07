package todo.javier.mera.todolist.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.animation.Animation;

/**
 * Created by javie on 11/29/2016.
 */

public interface FragmentRecyclerView {

    void setAdapter(Fragment context);
    void setItemAnimator(RecyclerView.ItemAnimator animator);
    void setLayoutManager(Context context);
    void setFixedSize(boolean isFixed);
}
