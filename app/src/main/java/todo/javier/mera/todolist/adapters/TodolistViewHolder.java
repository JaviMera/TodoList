package todo.javier.mera.todolist.adapters;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.fragments.FragmentRecycler;
import todo.javier.mera.todolist.model.TodoList;

public class TodolistViewHolder extends ViewHolderBase<TodoList>
    implements View.OnClickListener, View.OnLongClickListener{

    private LinearLayout mLayout;
    private TextView mTodolistTitle;
    private TextView mTotalitems;
    private TextView mCreationDate;

    private Drawable mTitleRemoveDrawable;
    private Drawable mTitleDrawable;
    private Drawable mBodyRemoveDrawable;
    private Drawable mBodyDrawable;
    private Drawable mBodyMoveDrawable;
    private Drawable mTitleMoveDrawable;

    TodolistViewHolder(FragmentRecycler fragment, View itemView) {
        super(itemView);

        mParent = fragment;

        mTitleRemoveDrawable = ContextCompat.getDrawable(mParent.getActivity(), R.drawable.title_remove_background);
        mTitleDrawable = ContextCompat.getDrawable(mParent.getActivity(), R.drawable.title_background);
        mTitleMoveDrawable = ContextCompat.getDrawable(mParent.getActivity(), R.drawable.title_move_background);
        mBodyRemoveDrawable = ContextCompat.getDrawable(mParent.getActivity(), R.drawable.body_remove_background);
        mBodyDrawable = ContextCompat.getDrawable(mParent.getActivity(), R.drawable.body_background);
        mBodyMoveDrawable = ContextCompat.getDrawable(mParent.getActivity(), R.drawable.body_move_background);
    }

    @Override
    public void bind(TodoList item) {

        mTodolistTitle.setText(item.getTitle());
        mTotalitems.setText(String.format(Locale.ENGLISH, "%d items...", item.getItemsCount()));

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyy");

        mCreationDate.setText(dateFormat.format(item.getCreationDate()));

        if(item.isMoving()) {

            mTodolistTitle.setBackground(mTitleMoveDrawable);
            mLayout.setBackground(mBodyMoveDrawable);
        }
        else if(item.getCanRemove()) {

            mTodolistTitle.setBackground(mTitleRemoveDrawable);
            mLayout.setBackground(mBodyRemoveDrawable);
        }
        else {

            mTodolistTitle.setBackground(mTitleDrawable);
            mLayout.setBackground(mBodyDrawable);
        }
    }

    @Override
    protected void setViews() {

        mLayout = (LinearLayout) itemView.findViewById(R.id.bodyLayout);
        mTodolistTitle = (TextView) itemView.findViewById(R.id.todoTitleView);
        mTotalitems = (TextView) itemView.findViewById(R.id.totalItemsText);
        mCreationDate = (TextView) itemView.findViewById(R.id.creationDateView);

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View view) {

        mParent.onClick(getLayoutPosition());
    }

    @Override
    public boolean onLongClick(View view) {

        mParent.onLongClick(getLayoutPosition());
        return true;
    }
}
