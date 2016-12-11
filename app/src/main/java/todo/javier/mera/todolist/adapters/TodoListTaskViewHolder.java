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
    }

    @Override
    public void bind(final Task item) {

        boolean isCompleted = item.getStatus() == TaskStatus.Completed;
        mStatus.setChecked(isCompleted);
        mCheckMark.setVisibility(isCompleted ? View.VISIBLE : View.INVISIBLE);

        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

        mDescription.setText(item.getDescription());
        mDueDate.setText(format.format(item.getDueDate()));

        int color = mNormalColor;
        if(item.isMoving()) {

            color = mMoveColor;
        }
        else if(item.getCanRemove()) {

            color = mRemoveColor;
        }

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

                    mDueDate.setVisibility(View.INVISIBLE);

                    ObjectAnimator scaleAnimationX = ObjectAnimator.ofFloat(mCheckMark, "scaleX", 0.0f, 1.0f);
                    ObjectAnimator scaleAnimationY = ObjectAnimator.ofFloat(mCheckMark, "scaleY", 0.0f, 1.0f);
                    scaleAnimationX.setDuration(300);
                    scaleAnimationY.setDuration(300);
                    AnimatorSet scaleUp = new AnimatorSet();
                    scaleUp.play(scaleAnimationX).with(scaleAnimationY);
                    mCheckMark.setVisibility(View.VISIBLE);
                    scaleUp.start();

                }
                else {

                    mDueDate.setVisibility(View.VISIBLE);
                    mCheckMark.setVisibility(View.INVISIBLE);
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
}
