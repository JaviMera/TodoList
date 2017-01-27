package todo.javier.mera.todolist.adapters;

import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.fragments.FragmentRecycler;
import todo.javier.mera.todolist.model.Task;

/**
 * Created by javie on 12/5/2016.
 */

public class TaskAdapter extends RecyclerAdapter<Task, TaskViewHolder> {

    public TaskAdapter(FragmentRecycler fragment) {

        super(fragment, TaskViewHolder.class);
    }

    @Override
    protected int getLayout() {

        return R.layout.task_item;
    }
}
