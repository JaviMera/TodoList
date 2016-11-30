package todo.javier.mera.todolist.adapters;

import android.view.View;
import android.widget.TextView;

import java.util.Locale;

import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.model.TodoList;

/**
 * Created by javie on 11/30/2016.
 */

public class TodolistViewHolderPortrait extends ViewHolderBase<TodoList> {

    TextView mTodolistTitle;
    TextView mTodolistItems;

    public TodolistViewHolderPortrait(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(TodoList item) {

        mTodolistTitle.setText(item.getTitle());
        mTodolistItems.setText(String.format(Locale.ENGLISH, "%d items...", item.getItemsCount()));
    }

    @Override
    protected void setViews() {

        mTodolistTitle = (TextView) itemView.findViewById(R.id.todoTitleView);
        mTodolistItems = (TextView) itemView.findViewById(R.id.todoItemsView);
    }
}
