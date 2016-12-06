package todo.javier.mera.todolist.adapters;

import android.view.View;
import android.widget.TextView;

import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.model.TodoListItem;

/**
 * Created by javie on 12/5/2016.
 */
public class TodoListItemViewHolder extends ViewHolderBase<TodoListItem>{

    private TextView mDescription;

    public TodoListItemViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(TodoListItem item) {

        mDescription.setText(item.getDescription());
    }

    @Override
    protected void setViews() {

        mDescription = (TextView) itemView.findViewById(R.id.itemDescriptionView);
    }
}
