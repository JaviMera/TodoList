package todo.javier.mera.todolist.adapters;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.fragments.FragmentRecycler;
import todo.javier.mera.todolist.model.Task;

/**
 * Created by javie on 12/5/2016.
 */
public class TodoListTaskViewHolder extends ViewHolderBase<Task>
    implements View.OnClickListener, View.OnLongClickListener {

    private TextView mDueDate;
    private LinearLayout mLayout;
    private TextView mDescription;

    TodoListTaskViewHolder(View itemView, FragmentRecycler fragment) {
        super(itemView, fragment);
    }

    @Override
    public void bind(final Task item) {

        mDescription.setText(item.getDescription());

        if(item.isMoving()) {

            mDueDate.setBackground(mTitleMoveDrawable);
            mLayout.setBackground(mBodyMoveDrawable);
        }
        else if(item.getCanRemove()) {

            mDueDate.setBackground(mTitleRemoveDrawable);
            mLayout.setBackground(mBodyRemoveDrawable);
        }
        else {

            mDueDate.setBackground(mTitleDrawable);
            mLayout.setBackground(mBodyDrawable);
        }
    }

    @Override
    protected void setViews() {

        mDueDate = (TextView) itemView.findViewById(R.id.dueDateView);
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
