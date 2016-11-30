package todo.javier.mera.todolist.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by javie on 11/30/2016.
 */
public abstract class ViewHolderBase<T> extends RecyclerView.ViewHolder{

    public abstract void bind(T item);
    protected abstract void setViews();

    public ViewHolderBase(View itemView) {
        super(itemView);
        setViews();
    }
}
