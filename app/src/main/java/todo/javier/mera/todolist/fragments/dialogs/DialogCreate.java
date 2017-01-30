package todo.javier.mera.todolist.fragments.dialogs;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.model.Priority;
import todo.javier.mera.todolist.model.PriorityUtil;

/**
 * Created by javie on 1/30/2017.
 */

public abstract class DialogCreate extends DialogBase
    implements DueDateListener, PriorityListener {

    protected abstract String getTitle();
    protected abstract View getLayout();
    protected abstract void createItem();

    protected Date mDueDate;
    protected long mDueTime;
    protected Priority mPriority;

    @BindView(R.id.dialogTitleView)
    TextView mTitleView;

    @BindView(R.id.editTextView)
    EditText mEditText;

    @BindView(R.id.dueDateTextView)
    TextView mDueDateTextView;

    @BindView(R.id.priorityTextView)
    TextView mPriorityTextView;

    @BindView(R.id.priorityMessageTextView)
    TextView mPriorityMessage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPriority = Priority.None;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = getLayout();
        ButterKnife.bind(this, view);
        mTitleView.setText(getTitle());

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mParent);
        dialogBuilder.setView(view);

        return createDialog(
            dialogBuilder.create()
        );
    }

    @OnClick(R.id.dueDateTextView)
    public void onDueDateClick(View view) {

        DialogDueDate dialog = DialogDueDate.newInstance();
        dialog.setTargetFragment(this, 1);
        dialog.show(mParent.getSupportFragmentManager(), "due_date_dialog");
    }

    @OnClick(R.id.priorityTextView)
    public void onPriorityClick(View view) {

        DialogPriority dialog = new DialogPriority();
        dialog.setTargetFragment(this, 1);
        dialog.show(mParent.getSupportFragmentManager(), "priority_dialog");
    }

    @Override
    public void onDueDateSelected(Date date, long time) {

        mDueDate = date;
        mDueTime = time;
        mDueDate.setTime(time);
        SimpleDateFormat format = new SimpleDateFormat("LLL, EEE dd  HH:mm");

        mDueDateTextView.setText(format.format(mDueDate));
    }

    @Override
    public void onPrioritySelected(int position) {

        mPriority = Priority.values()[position];
        mPriorityTextView.setText(PriorityUtil.getName(mPriority.ordinal()));
    }

    @OnClick(R.id.createItemView)
    public void onAddClick(View view) {

        if(mEditText.getText().toString().isEmpty()) {

            showToast("To-do list title cannot be blank.");
            mEditText.startAnimation(mShakeAnimation);
            return;
        }

        if(mDueDate == null || mDueTime == 0L) {

            showToast("Task due date cannot be blank.");
            mDueDateTextView.startAnimation(mShakeAnimation);
            return;
        }

        // Set the selected time to the date object
        // This way there is no need to pass the date and time separately
        mDueDate.setTime(mDueTime);
        createItem();
        dismiss();
    }
}
