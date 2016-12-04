package todo.javier.mera.todolist.adapters;

import android.view.View;
import android.widget.TextView;

import java.util.Locale;

import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.fragments.TodoListListener;
import todo.javier.mera.todolist.model.TodoList;

public class TodolistViewHolder extends ViewHolderBase<TodoList> implements View.OnClickListener{

    private final TodoListListener mListener;
    private TodoList mTodoList;
    private TextView mTodolistTitle;
    private TextView mTotalitems;
    private TextView mCompletedItems;
    private TextView mIncompleItems;

    public TodolistViewHolder(TodoListListener listener, View itemView) {
        super(itemView);

        mListener = listener;
    }

    @Override
    public void bind(TodoList item) {

        mTodoList = item;
        mTodolistTitle.setText(mTodoList.getTitle());
        mTotalitems.setText(String.format(Locale.ENGLISH, "%d items...", mTodoList.getItemsCount()));
        mCompletedItems.setText(String.format(Locale.ENGLISH, "%d Completed Items", mTodoList.getCompletedItems()));
        mIncompleItems.setText(String.format(Locale.ENGLISH, "%d Incomplete Items", mTodoList.getIncompleteItems()));
    }

    @Override
    protected void setViews() {

        mTodolistTitle = (TextView) itemView.findViewById(R.id.todoTitleView);
        mTotalitems = (TextView) itemView.findViewById(R.id.totalItemsText);
        mCompletedItems = (TextView) itemView.findViewById(R.id.completedItemsText);
        mIncompleItems = (TextView) itemView.findViewById(R.id.incompletedItemsText);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        mListener.onTodoListClick(mTodoList);
    }
}
