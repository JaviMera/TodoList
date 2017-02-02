package todo.javier.mera.todolist.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.fragments.FragmentRecycler;
import todo.javier.mera.todolist.model.TodoList;

class TodolistViewHolder extends ViewHolderBase<TodoList>
    implements View.OnClickListener, View.OnLongClickListener{

    private TextView mTotalitems;
    private ImageView mNavigateImageView;

    TodolistViewHolder(FragmentRecycler fragment, View itemView) {

        super(fragment, itemView);
    }

    @Override
    public void bind(TodoList todoList) {

        setDescription(todoList.getDescription());
        setDueDate(todoList.getDueDate());
        setTotalTasks(todoList.getTaskNumber());
        setBackgroundColor(todoList);

        if(mParent.isRemovingItems()) {

            setVisibility(mPriorityImageView, View.GONE);
            setVisibility(mNavigateImageView, View.GONE);
        }
        else {

            setPriority(todoList.getPriority());
            setVisibility(mNavigateImageView, View.VISIBLE);
        }
    }

    @Override
    protected int getBackgrounColor(TodoList todoList) {

        if(todoList.getCanRemove()) {

            return mRemoveColor;
        }

        return mNormalColor;
    }

    @Override
    protected SimpleDateFormat getDateFormat() {

        return new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.ENGLISH);
    }

    @Override
    protected void setChildViews() {

        mTotalitems = (TextView) itemView.findViewById(R.id.totalItemsText);
        mNavigateImageView = (ImageView) itemView.findViewById(R.id.navigateImageView);

        mNavigateImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mParent.onNavigateClick(getLayoutPosition());
            }
        });

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

    private void setTotalTasks(int totalTasks) {

        mTotalitems.setText(
            String.format(
                    Locale.ENGLISH,
                    "TASKS  %d",
                    totalTasks
            )
        );
    }
}
