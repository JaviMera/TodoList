package todo.javier.mera.todolist.adapters;

import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.fragments.FragmentRecycler;
import todo.javier.mera.todolist.fragments.FragmentTasks;
import todo.javier.mera.todolist.model.TodoListTask;

/**
 * Created by javie on 12/5/2016.
 */
public class TodoListTaskViewHolder extends ViewHolderBase<TodoListTask>
    implements View.OnClickListener, View.OnLongClickListener {

    private FragmentRecycler mParent;

    private LinearLayout mLayout;
    private TextView mDescription;
    protected final int mRemovableColor;
    protected final int mNonRemovableColor;

    TodoListTaskViewHolder(View itemView, FragmentRecycler fragment) {
        super(itemView);
        mParent = fragment;

        mRemovableColor = ContextCompat.getColor(fragment.getActivity(), R.color.remove_item_color);
        mNonRemovableColor = ContextCompat.getColor(fragment.getActivity(), android.R.color.transparent);
    }

    @Override
    public void bind(final TodoListTask item) {

        mDescription.setText(item.getDescription());

        if(item.getCanRemove()) {

            mLayout.setBackgroundColor(mRemovableColor);
        }
        else {

            mLayout.setBackgroundColor(mNonRemovableColor);
        }
    }

    @Override
    protected void setViews() {

        mLayout = (LinearLayout) itemView.findViewById(R.id.containerLayout);
        mDescription = (TextView) itemView.findViewById(R.id.itemDescriptionView);

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View view) {

        mParent.onClick(getLayoutPosition());
    }

    @Override
    public boolean onLongClick(View view) {

        mParent.onLongClick(getLayoutPosition());
        return true;
    }
}
