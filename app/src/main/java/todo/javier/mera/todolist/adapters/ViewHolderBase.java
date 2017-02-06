package todo.javier.mera.todolist.adapters;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.fragments.FragmentRecycler;
import todo.javier.mera.todolist.model.ItemBase;
import todo.javier.mera.todolist.model.Priority;
import todo.javier.mera.todolist.model.PriorityUtil;

/**
 * Created by javie on 11/30/2016.
 */
abstract class ViewHolderBase<T extends ItemBase> extends RecyclerView.ViewHolder{

    protected FragmentRecycler mParent;

    protected LinearLayout mLayout;
    protected TextView mDescriptionView;
    protected ImageView mPriorityImageView;
    protected TextView mDueDateTextView;

    protected int mNormalColor;
    protected int mRemoveColor;

    public abstract void bind(T item);

    protected abstract int getBackgrounColor(T item);
    protected abstract SimpleDateFormat getDateFormat();
    protected abstract void setChildViews();

    ViewHolderBase(FragmentRecycler fragment, View itemView) {
        super(itemView);

        mParent = fragment;

        mLayout = (LinearLayout) itemView.findViewById(R.id.itemLayout);
        mDescriptionView = (TextView) itemView.findViewById(R.id.itemDescriptionView);
        mDueDateTextView = (TextView) itemView.findViewById(R.id.dueDateView);
        mPriorityImageView = (ImageView) itemView.findViewById(R.id.priorityIconView);

        mNormalColor = ContextCompat.getColor(mParent.getActivity(), android.R.color.transparent);
        mRemoveColor = ContextCompat.getColor(mParent.getActivity(), R.color.remove_color_light);

        setChildViews();
    }

    protected void setDescription(String text) {

        mDescriptionView.setText(text);
    }

    protected void setDueDate(String text) {

        mDueDateTextView.setText(text);
    }

    protected void setVisibility(View view, int visibility) {

        view.setVisibility(visibility);
    }

    protected void setPriority(Priority priority) {

        if(Priority.None != priority) {

            setVisibility(mPriorityImageView, View.VISIBLE);
            setPriorityImage(priority);
        }
        else {

            setVisibility(mPriorityImageView, View.GONE);
        }
    }

    protected void setPriorityImage(Priority priority) {

        Drawable icon = ContextCompat.getDrawable(
            mParent.getContext(),
            PriorityUtil.getDrawable(priority.ordinal())
        );
        mPriorityImageView.setImageDrawable(icon);
    }

    protected void setBackgroundColor(T item) {

        int color = getBackgrounColor(item);
        mLayout.setBackgroundColor(color);
    }
}
