package todo.javier.mera.todolist.adapters;

import android.support.v4.app.Fragment;

import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.model.TodoListTask;

/**
 * Created by javie on 12/5/2016.
 */

public class TodoListTaskAdapter extends RecyclerAdapter<TodoListTask, TodoListTaskViewHolder> {

    public TodoListTaskAdapter(Fragment fragment) {

        super(fragment, TodoListTaskViewHolder.class);
    }

    @Override
    protected void removeItem(int position) {

        mItems.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    protected int getLayout() {

        return R.layout.task_item;
    }
}
