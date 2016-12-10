package todo.javier.mera.todolist.adapters;

import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.fragments.FragmentRecycler;
import todo.javier.mera.todolist.model.Task;

/**
 * Created by javie on 12/5/2016.
 */

public class TodoListTaskAdapter extends RecyclerAdapter<Task, TodoListTaskViewHolder> {

    public TodoListTaskAdapter(FragmentRecycler fragment) {

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
