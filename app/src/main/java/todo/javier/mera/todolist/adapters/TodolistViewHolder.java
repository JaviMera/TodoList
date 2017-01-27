package todo.javier.mera.todolist.adapters;

import android.graphics.Color;
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

class TodolistViewHolder extends ViewHolderBase<TodoList>
    implements View.OnClickListener, View.OnLongClickListener{

    private LinearLayout mLayout;
    private TextView mTodolistTitle;
    private TextView mTotalitems;
    private TextView mCreationDate;

    TodolistViewHolder(FragmentRecycler fragment, View itemView) {

        super(fragment, itemView);
    }

    @Override
    public void bind(TodoList item) {

        mTodolistTitle.setText(item.getTitle());
        mTotalitems.setText(String.format(Locale.ENGLISH, "TASKS  %d", item.getItemsCount()));

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyy");

        mCreationDate.setText(dateFormat.format(item.getCreationDate()));

        if(item.getCanRemove()) {

            int color = ContextCompat.getColor(mParent.getContext(), R.color.remove_color_light);
            mLayout.setBackgroundColor(color);
        }
        else {

            int color = ContextCompat.getColor(mParent.getContext(), android.R.color.white);
            mLayout.setBackgroundColor(color);
        }
    }

    @Override
    protected void setViews() {

        mLayout = (LinearLayout) itemView.findViewById(R.id.itemLayout);
        mTodolistTitle = (TextView) itemView.findViewById(R.id.todoListNameView);
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
