package todo.javier.mera.todolist.adapters;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.fragments.FragmentRecycler;
import todo.javier.mera.todolist.fragments.FragmentTask;
import todo.javier.mera.todolist.fragments.ItemTaskListener;
import todo.javier.mera.todolist.model.Priority;
import todo.javier.mera.todolist.model.PriorityUtil;
import todo.javier.mera.todolist.model.Task;
import todo.javier.mera.todolist.model.TaskStatus;

/**
 * Created by javie on 12/5/2016.
 */
class TaskViewHolder extends ViewHolderBase<Task>
    implements
    View.OnClickListener,
    View.OnLongClickListener {

    private TextView mDueDate;
    private LinearLayout mLayout;
    private TextView mDescription;
    private CheckBox mStatus;
    private ImageView mDragImageView;
    private ImageView mPriorityImageView;

    private int mNormalColor;
    private int mRemoveColor;
    private int mMoveColor;

    private ItemTaskListener mTaskListener;

    TaskViewHolder(FragmentRecycler fragment, View itemView) {

        super(fragment, itemView);

        mTaskListener = (FragmentTask)fragment;

        mNormalColor = ContextCompat.getColor(mParent.getActivity(), android.R.color.transparent);
        mRemoveColor = ContextCompat.getColor(mParent.getActivity(), R.color.remove_color_light);
        mMoveColor = ContextCompat.getColor(mParent.getActivity(), R.color.move_color_light);
    }

    @Override
    public void bind(final Task item) {

        final ViewHolderBase currentObject = this;

        mDragImageView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(MotionEventCompat.getActionMasked(motionEvent) == MotionEvent.ACTION_DOWN)
                    mParent.onStartDrag(currentObject, getLayoutPosition());

                return false;
            }
        });

        boolean isCompleted = item.getStatus() == TaskStatus.Completed;
        mStatus.setChecked(isCompleted);

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        mDescription.setText(item.getDescription());
        mDueDate.setText(
            "Due by " +
            dateFormat.format(item.getDueDate()) +
            " at " +
            timeFormat.format(item.getmDueTime()));
        
        int color = getLayoutColor(item);
        mLayout.setBackgroundColor(color);

        if(mParent.isRemovingItems()) {

            mDragImageView.setVisibility(View.GONE);
            mStatus.setVisibility(View.GONE);
        }
        else {

            mDragImageView.setVisibility(View.VISIBLE);
            mStatus.setVisibility(View.VISIBLE);
        }

        if(Priority.None != item.getPriority()) {

            Drawable icon = ContextCompat.getDrawable(mParent.getContext(), PriorityUtil.getDrawable(item.getPriority().ordinal()));
            mPriorityImageView.setImageDrawable(icon);
        }
    }

    @Override
    protected void setViews() {

        mStatus = (CheckBox) itemView.findViewById(R.id.itemCheckBoxView);
        mStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isCompleted) {

            mTaskListener.onUpdateStatus(getAdapterPosition(), isCompleted);
            }
        });

        mDueDate = (TextView) itemView.findViewById(R.id.dueDateView);
        mLayout = (LinearLayout) itemView.findViewById(R.id.containerLayout);
        mDescription = (TextView) itemView.findViewById(R.id.itemDescriptionView);
        mDragImageView = (ImageView) itemView.findViewById(R.id.dragImageView);
        mPriorityImageView = (ImageView) itemView.findViewById(R.id.priorityIconView);

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

    private int getLayoutColor(Task task) {

        if(task.isMoving()) {

            return mMoveColor;
        }

        if(task.getCanRemove()) {

            return mRemoveColor;
        }

        return mNormalColor;
    }
}
