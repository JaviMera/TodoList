package todo.javier.mera.todolist.adapters;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v4.content.ContextCompat;
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
import todo.javier.mera.todolist.model.PriorityUtil;
import todo.javier.mera.todolist.model.Task;
import todo.javier.mera.todolist.model.TaskStatus;

/**
 * Created by javie on 12/5/2016.
 */
public class TodoListTaskViewHolder extends ViewHolderBase<Task>
    implements
    View.OnClickListener,
    View.OnLongClickListener {

    private final ObjectAnimator mCheckMarkScaleX;
    private final ObjectAnimator mCheckMarkScaleY;
    private final ObjectAnimator mDueDateScaleX;
    private final ObjectAnimator mDueDateScaleY;

    private TextView mDueDate;
    private LinearLayout mLayout;
    private TextView mDescription;
    private CheckBox mStatus;
    private ImageView mCheckMark;

    private int mNormalColor;
    private int mRemoveColor;
    private int mMoveColor;

    private ItemTaskListener mTaskListener;

    TodoListTaskViewHolder(View itemView, FragmentRecycler fragment) {
        super(itemView);
        mParent = fragment;
        mTaskListener = (FragmentTask)fragment;

        mNormalColor = ContextCompat.getColor(mParent.getActivity(), android.R.color.transparent);
        mRemoveColor = ContextCompat.getColor(mParent.getActivity(), R.color.remove_color_light);
        mMoveColor = ContextCompat.getColor(mParent.getActivity(), R.color.move_color_light);

        mCheckMarkScaleX = scaleUp(mCheckMark, "scaleX", 0.0f, 1.0f, 300);
        mCheckMarkScaleY = scaleUp(mCheckMark, "scaleY", 0.0f, 1.0f, 300);
        mDueDateScaleX = scaleUp(mDueDate, "scaleX", 0.0f, 1.0f, 300);
        mDueDateScaleY = scaleUp(mDueDate, "scaleY", 0.0f, 1.0f, 300);
    }

    private ObjectAnimator scaleUp(View view, String property, float from, float to, int duration) {

        return ObjectAnimator
            .ofFloat(view, property, from, to)
            .setDuration(duration);
    }

    @Override
    public void bind(final Task item) {

        boolean isCompleted = item.getStatus() == TaskStatus.Completed;
        mStatus.setChecked(isCompleted);
        mCheckMark.setVisibility(isCompleted ? View.VISIBLE : View.INVISIBLE);

        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

        mDescription.setText(item.getDescription());
        mDueDate.setText(format.format(item.getDueDate()));

        int priorityColor = getPriorityColor(item);
        mDueDate.setTextColor(priorityColor);
        
        int color = getLayoutColor(item);
        mLayout.setBackgroundColor(color);
    }

    @Override
    protected void setViews() {

        mStatus = (CheckBox) itemView.findViewById(R.id.itemCheckBoxView);
        mStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isCompleted) {

            mTaskListener.onStatusUpdate(getAdapterPosition(), isCompleted);

            if(isCompleted) {

                setCompleted(true);

                AnimatorSet scaleUp = new AnimatorSet();
                scaleUp
                    .play(mCheckMarkScaleX)
                    .with(mCheckMarkScaleY);

                scaleUp.start();
            }
            else {

                setCompleted(false);

                AnimatorSet set = new AnimatorSet();
                set
                    .play(mDueDateScaleX)
                    .with(mDueDateScaleY);

                set.start();
            }
            }
        });

        mDueDate = (TextView) itemView.findViewById(R.id.dueDateView);
        mLayout = (LinearLayout) itemView.findViewById(R.id.containerLayout);
        mDescription = (TextView) itemView.findViewById(R.id.itemDescriptionView);
        mCheckMark = (ImageView) itemView.findViewById(R.id.checkMarkView);

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

    private int getPriorityColor(Task task) {

        int taskPriority = task
            .getPriority()
            .ordinal();

        int color = PriorityUtil
            .getColor(taskPriority);

        return ContextCompat.getColor(
                mParent.getActivity(),
                color
        );
    }

    private void setCompleted(boolean isCompleted) {

        mDueDate.setVisibility(isCompleted ? View.INVISIBLE : View.VISIBLE);
        mCheckMark.setVisibility(isCompleted ? View.VISIBLE : View.INVISIBLE);
    }
}
