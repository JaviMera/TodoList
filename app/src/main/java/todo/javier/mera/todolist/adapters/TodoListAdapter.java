package todo.javier.mera.todolist.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.model.TodoList;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.TodoListViewHolder> {

    private final Context mContext;
    private List<TodoList> mItems;

    public TodoListAdapter(Context context) {

        mContext = context;
        mItems = new LinkedList<>();
    }

    @Override
    public TodoListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.todo_list_item, parent, false);
        return new TodoListViewHolder(view);
    }

    @Override
    public int getItemCount() {

        return mItems.size();
    }

    public TodoList getItem(int position) {

        return mItems.get(position);
    }

    @Override
    public void onBindViewHolder(TodoListViewHolder holder, int position) {

        holder.bind(mItems.get(position));
    }

    public void addTodoList(TodoList todoList) {
        int currentSize = mItems.size();
        mItems.add(todoList);
        notifyItemInserted(currentSize);
    }

    class TodoListViewHolder extends RecyclerView.ViewHolder {

        TextView mTitle;

        public TodoListViewHolder(View itemView) {
            super(itemView);

            mTitle = (TextView) itemView.findViewById(R.id.todoTitleView);
        }

        public void bind(TodoList todoList) {

            mTitle.setText(todoList.getTitle());
        }
    }
}
