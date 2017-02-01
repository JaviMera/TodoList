package todo.javier.mera.todolist.adapters;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.fragments.FragmentRecycler;
import todo.javier.mera.todolist.model.Priority;
import todo.javier.mera.todolist.model.PriorityUtil;
import todo.javier.mera.todolist.model.TodoList;

class TodolistViewHolder extends ViewHolderBase<TodoList>
    implements View.OnClickListener, View.OnLongClickListener{

    private LinearLayout mLayout;
    private TextView mTodolistTitle;
    private TextView mTotalitems;
    private TextView mDueDateTextView;
    private ImageView mPriorityImageView;

    TodolistViewHolder(FragmentRecycler fragment, View itemView) {

        super(fragment, itemView);
    }

    @Override
    public void bind(TodoList item) {

        mTodolistTitle.setText(item.getDescription());
        mTotalitems.setText(String.format(Locale.ENGLISH, "TASKS  %d", item.getTaskNumber()));

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyy");

        mDueDateTextView.setText(dateFormat.format(item.getDueDate()));

        if(mParent.isRemovingItems()) {

            int color = ContextCompat.getColor(mParent.getContext(), R.color.remove_color_light);
            mLayout.setBackgroundColor(color);
            mPriorityImageView.setVisibility(View.GONE);
        }
        else {

            int color = ContextCompat.getColor(mParent.getContext(), android.R.color.white);
            mLayout.setBackgroundColor(color);

            if(Priority.None != item.getPriority()) {

                mPriorityImageView.setVisibility(View.VISIBLE);
                Drawable icon = ContextCompat.getDrawable(mParent.getContext(), PriorityUtil.getDrawable(item.getPriority().ordinal()));
                mPriorityImageView.setImageDrawable(icon);
            }
            else {

                mPriorityImageView.setVisibility(View.GONE);
            }
        }

    }

    @Override
    protected void setViews() {

        mLayout = (LinearLayout) itemView.findViewById(R.id.itemLayout);
        mTodolistTitle = (TextView) itemView.findViewById(R.id.todoListNameView);
        mTotalitems = (TextView) itemView.findViewById(R.id.totalItemsText);
        mDueDateTextView = (TextView) itemView.findViewById(R.id.creationDateView);
        mPriorityImageView = (ImageView) itemView.findViewById(R.id.priorityIconView);

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
