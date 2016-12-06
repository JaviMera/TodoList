package todo.javier.mera.todolist.adapters;

import android.support.v4.app.Fragment;

import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.model.TodoListItem;

/**
 * Created by javie on 12/5/2016.
 */

public class TodoListItemAdapter extends RecyclerAdapter<TodoListItem, TodoListItemViewHolder> {

    public TodoListItemAdapter(Fragment fragment) {

        super(fragment, TodoListItemViewHolder.class);
    }

    @Override
    protected TodoListItem getItem(int position) {

        return mItems.get(position);
    }

    @Override
    protected void removeItem(int position) {

        mItems.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    protected int getLayout() {

        return R.layout.todo_list_item;
    }

    @Override
    public void addItem(TodoListItem item) {

        mItems.add(item);
        notifyItemInserted(getItemCount() - 1);
    }
}
