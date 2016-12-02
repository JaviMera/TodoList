package todo.javier.mera.todolist.adapters;

import android.view.View;
import android.widget.TextView;

import java.util.Locale;

import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.model.TodoList;

public class TodolistViewHolder extends ViewHolderBase<TodoList> implements View.OnClickListener{

    private TextView mTodolistTitle;
    private TextView mTodolistItems;

    public TodolistViewHolder(View itemView) {
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
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        String text = mTodolistTitle.getText().toString();
    }
}
