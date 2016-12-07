package todo.javier.mera.todolist.adapters;

import android.view.View;
import android.widget.TextView;

import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.model.TodoListTask;

/**
 * Created by javie on 12/5/2016.
 */
public class TodoListItemViewHolder extends ViewHolderBase<TodoListTask>{

    private TextView mDescription;

    public TodoListItemViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(TodoListTask item) {

        mDescription.setText(item.getDescription());
    }

    @Override
    protected void setViews() {

        mDescription = (TextView) itemView.findViewById(R.id.itemDescriptionView);
    }
}
