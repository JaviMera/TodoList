package todo.javier.mera.todolist.adapters;

import android.support.v4.content.ContextCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.fragments.FragmentRecycler;
import todo.javier.mera.todolist.fragments.FragmentTask;
import todo.javier.mera.todolist.fragments.ItemTaskListener;
import todo.javier.mera.todolist.model.Task;
import todo.javier.mera.todolist.model.TaskStatus;

/**
 * Created by javie on 12/5/2016.
 */
class TaskViewHolder extends ViewHolderBase<Task>
    implements
    View.OnClickListener,
    View.OnLongClickListener {

    private CheckBox mStatus;
    private ImageView mDragImageView;
    private ImageView mReminderImageView;
    private int mMoveColor;

    private ItemTaskListener mTaskListener;

    TaskViewHolder(FragmentRecycler fragment, View itemView) {

        super(fragment, itemView);

        mTaskListener = (FragmentTask)fragment;
        mMoveColor = ContextCompat.getColor(mParent.getActivity(), R.color.move_color_light);
    }

    @Override
    public void bind(Task task) {

        setDescription(task.getDescription());

        if(task.hasDueDate()) {

            SimpleDateFormat dateFormat = getDateFormat();
            setDueDate(dateFormat.format(task.getDueDate()));
        }
        else {

            setDueDate("no due date");
        }

        setDragImage(this);
        setStatus(task.getStatus() == TaskStatus.Completed);
        setBackgroundColor(task);

        // Check if the parent is removing items and not if the current item is selected to be removed
        // This will cause for each item in the list to be checked, instead of just the current item.
        if(mParent.isRemovingItems()) {

            setVisibility(mDragImageView, View.GONE);
            setVisibility(mStatus, View.GONE);
            setVisibility(mReminderImageView, View.GONE);
            setVisibility(mPriorityImageView, View.GONE);
        }
        else {

            setVisibility(mDragImageView, View.VISIBLE);
            setVisibility(mStatus, View.VISIBLE);
            setPriority(task.getPriority());
            setReminder(task.getReminderDate());
        }

        if (task.getStatus() == TaskStatus.Completed) {

            strikeThroughTextView(mDescriptionView);
            strikeThroughTextView(mDueDateTextView);
        } else {

            removeStrikeThroughTextView(mDescriptionView);
            removeStrikeThroughTextView(mDueDateTextView);
        }
    }

    @Override
    protected int getBackgrounColor(Task task) {

        if(task.isMoving()) {

            return mMoveColor;
        }

        if(task.getCanRemove()) {

            return mRemoveColor;
        }

        return mNormalColor;
    }

    @Override
    protected SimpleDateFormat getDateFormat() {

        return new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.ENGLISH);
    }

    @Override
    protected void setChildViews() {

        mStatus = (CheckBox) itemView.findViewById(R.id.itemCheckBoxView);
        mStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isCompleted) {

            if (isCompleted) {

                strikeThroughTextView(mDescriptionView);
                strikeThroughTextView(mDueDateTextView);
            } else {

                removeStrikeThroughTextView(mDescriptionView);
                removeStrikeThroughTextView(mDueDateTextView);
            }

            mTaskListener.onUpdateStatus(getAdapterPosition(), isCompleted);
            }
        });

        mDragImageView = (ImageView) itemView.findViewById(R.id.dragImageView);
        mReminderImageView = (ImageView) itemView.findViewById(R.id.reminderImageView);

        mReminderImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            ((FragmentTask)mParent).onReminderClick(getLayoutPosition());
            }
        });

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

    private View.OnTouchListener getTouchListener(final RecyclerView.ViewHolder viewHolder) {

        return new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(MotionEventCompat.getActionMasked(motionEvent) == MotionEvent.ACTION_DOWN)
                    mParent.onStartDrag(viewHolder, getLayoutPosition());

                return false;
            }
        };
    }

    private void setDragImage(ViewHolderBase viewHolderBase) {

        mDragImageView.setOnTouchListener(getTouchListener(viewHolderBase));
    }

    private void setStatus(boolean isCompleted) {

        mStatus.setChecked(isCompleted);
    }

    private void setReminder(long reminderDate) {

        if(reminderDate != 0L) {

            setVisibility(mReminderImageView, View.VISIBLE);
        }
        else {

            setVisibility(mReminderImageView, View.GONE);
        }
    }

    private void strikeThroughTextView (TextView view) {

        view.setText(view.getText().toString(), TextView.BufferType.SPANNABLE);
        Spannable spannable = (Spannable) view.getText();
        spannable.setSpan(new StrikethroughSpan(), 0, view.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private void removeStrikeThroughTextView(TextView view) {

        String viewText = view.getText().toString();
        view.setText(viewText);
    }
}
