package todo.javier.mera.todolist.fragments.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.fragments.FragmentTodoList;
import todo.javier.mera.todolist.model.Priority;
import todo.javier.mera.todolist.model.PriorityUtil;
import todo.javier.mera.todolist.ui.MainActivity;

/**
 * Created by javie on 12/6/2016.
 */

public class DialogTodoList extends DialogBase
    implements ReminderListener, PriorityListener{

    private static final String DIALOG_TITLE = "Create a To-do list!";
    private static final long EMPTY_DUE_TIME = 0L;

    private DialogTodoListListener mListener;
    private Date mDueDate;
    private long mDueTime;
    private Priority mPriority;

    @BindView(R.id.dialogTitleView)
    TextView mTitleView;

    @BindView(R.id.todoListEditTextView)
    EditText mTitleEditText;

    @BindView(R.id.dueDateTextView)
    TextView mDueDateTextView;

    @BindView(R.id.priorityTextView)
    TextView mPriorityTextView;

    @BindView(R.id.priorityMessageTextView)
    TextView mPriorityMessage;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mListener = (FragmentTodoList) getTargetFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPriority = Priority.None;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = LayoutInflater
            .from(mParent)
            .inflate(R.layout.todo_list_dialog, null
        );

        ButterKnife.bind(this, view);

        mTitleView.setText(DIALOG_TITLE);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mParent);
        dialogBuilder.setView(view);

        return createDialog(dialogBuilder);
    }

    @OnClick(R.id.dueDateTextView)
    public void onDueDateClick(View view) {

        DialogReminder dialog = DialogReminder.newInstance("Set Due Date");
        dialog.setTargetFragment(this, 1);
        dialog.show(mParent.getSupportFragmentManager(), "due_date_dialog");
    }

    @OnClick(R.id.priorityTextView)
    public void onPriorityClick(View view) {

        DialogPriority dialog = new DialogPriority();
        dialog.setTargetFragment(this, 1);
        dialog.show(mParent.getSupportFragmentManager(), "priority_dialog");
    }

    @OnClick(R.id.addTaskView)
    public void onAddClick(View view) {

        if(mTitleEditText.getText().toString().isEmpty()) {

            showToast("To-do list title cannot be blank.");
            mTitleEditText.startAnimation(mShakeAnimation);
            return;
        }

        if(mDueDate == null || mDueTime == EMPTY_DUE_TIME) {

            showToast("Task due date cannot be blank.");
            mDueDateTextView.startAnimation(mShakeAnimation);
            return;
        }

        // Set the selected time to the date object
        // This way there is no need to pass the date and time separately
        mDueDate.setTime(mDueTime);

        mListener.onCreateTodoList(
            mTitleEditText.getText().toString(),
            mDueDate,
            mPriority);

        dismiss();
    }

    @Override
    public void onReminderSet(Date date, long time) {

        mDueDate = date;
        mDueTime = time;
        SimpleDateFormat format = new SimpleDateFormat("LLL, EEE dd  HH:mm");
        date.setTime(time);
        mDueDateTextView.setText(format.format(date));
    }

    @Override
    public void onPrioritySelected(int position) {

        mPriority = Priority.values()[position];
        mPriorityTextView.setText(PriorityUtil.getName(mPriority.ordinal()));
        mPriorityMessage.setVisibility(View.GONE);
    }
}
