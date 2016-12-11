package todo.javier.mera.todolist.adapters;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.fragments.FragmentRecycler;
import todo.javier.mera.todolist.fragments.FragmentTask;
import todo.javier.mera.todolist.fragments.ItemTaskListener;
import todo.javier.mera.todolist.model.Task;
import todo.javier.mera.todolist.model.TaskStatus;

/**
 * Created by javie on 12/5/2016.
 */
public class TodoListTaskViewHolder extends ViewHolderBase<Task>
    implements
    View.OnClickListener,
    View.OnLongClickListener {

    private TextView mDueDate;
    private LinearLayout mLayout;
    private TextView mDescription;
    private CheckBox mStatus;

    private ItemTaskListener mTaskListener;

    TodoListTaskViewHolder(View itemView, FragmentRecycler fragment) {
        super(itemView, fragment);

        mTaskListener = (FragmentTask) mParent;
    }

    @Override
    public void bind(final Task item) {

        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        mDueDate.setText("Due by  " + format.format(item.getDueDate()));

        boolean isCompleted = item.getStatus() == TaskStatus.Completed;
        mStatus.setChecked(isCompleted);

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

        mStatus = (CheckBox) itemView.findViewById(R.id.itemCheckBoxView);
        mStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isCompleted) {

                mTaskListener.onStatusUpdate(getAdapterPosition(), isCompleted);
            }
        });

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
