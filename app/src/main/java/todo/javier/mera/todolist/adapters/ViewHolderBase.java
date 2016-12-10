package todo.javier.mera.todolist.adapters;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.fragments.FragmentRecycler;

/**
 * Created by javie on 11/30/2016.
 */
public abstract class ViewHolderBase<T> extends RecyclerView.ViewHolder{

    protected FragmentRecycler mParent;
    protected Drawable mTitleRemoveDrawable;
    protected Drawable mTitleDrawable;
    protected Drawable mBodyRemoveDrawable;
    protected Drawable mBodyDrawable;
    protected Drawable mBodyMoveDrawable;
    protected Drawable mTitleMoveDrawable;

    public abstract void bind(T item);
    protected abstract void setViews();

    public ViewHolderBase(View itemView, FragmentRecycler parent) {
        super(itemView);

        setViews();

        mParent = parent;

        mTitleRemoveDrawable = ContextCompat.getDrawable(mParent.getActivity(), R.drawable.title_remove_background);
        mTitleDrawable = ContextCompat.getDrawable(mParent.getActivity(), R.drawable.title_background);
        mTitleMoveDrawable = ContextCompat.getDrawable(mParent.getActivity(), R.drawable.title_move_background);
        mBodyRemoveDrawable = ContextCompat.getDrawable(mParent.getActivity(), R.drawable.body_remove_background);
        mBodyDrawable = ContextCompat.getDrawable(mParent.getActivity(), R.drawable.body_background);
        mBodyMoveDrawable = ContextCompat.getDrawable(mParent.getActivity(), R.drawable.body_move_background);
    }
}
