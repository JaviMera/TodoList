package todo.javier.mera.todolist.fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

/**
 * Created by javie on 11/29/2016.
 */

public interface FragmentRecyclerView {

    void setAdapter(Context context);
    void setItemAnimator(RecyclerView.ItemAnimator animator);
    void setLayoutManager(Context context);
    void setFixedSize(boolean isFixed);
}