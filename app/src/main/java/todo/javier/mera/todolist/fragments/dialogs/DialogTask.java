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

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.fragments.FragmentTask;
import todo.javier.mera.todolist.model.PriorityUtil;
import todo.javier.mera.todolist.model.TaskPriority;
import todo.javier.mera.todolist.ui.MainActivity;

/**
 * Created by javie on 12/6/2016.
 */

public class DialogTask extends DialogFragment
    implements
    DatePickerListener,
    PriorityListener{

    private MainActivity mParent;
    private Date mDueDate;
    private DialogTaskListener mListener;
    private TaskPriority mPriority;
    private Animation mShakeAnimation;

    @BindView(R.id.dialogTitleView)
    TextView mTitleView;

    @BindView(R.id.taskEditTextView)
    EditText mDescriptionEditText;

    @BindView(R.id.datePickerTextView)
    TextView mDateTextView;

    @BindView(R.id.priorityTextView)
    TextView mPriorityTextView;

    @BindView(R.id.priorityMessageTextView)
    TextView mPriorityMessage;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mParent = (MainActivity) context;
        mListener = (FragmentTask)getTargetFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPriority = TaskPriority.None;
        mShakeAnimation = AnimationUtils.loadAnimation(mParent, R.anim.shake);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = LayoutInflater
            .from(mParent)
            .inflate(R.layout.task_dialog, null
        );

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mParent);
        dialogBuilder.setView(view);

        ButterKnife.bind(this, view);

        mTitleView.setText("Create new task!");

        AlertDialog dialog = dialogBuilder.create();
        dialog.setCanceledOnTouchOutside(false);

        dialog.setOnKeyListener(getKeyListener());
        return dialog;
    }

    private DialogInterface.OnKeyListener getKeyListener() {

        return new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
                if ((keyCode == android.view.KeyEvent.KEYCODE_BACK)) {

                    mParent.showFabButton();
                }

                return false;
            }
        };
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Initialize dialog animations when this event is fired.
        getDialog()
            .getWindow()
            .getAttributes()
            .windowAnimations = R.style.FragmentDialogTaskAnimations;
    }

    @OnClick(R.id.addTaskView)
    public void onAddClick() {

        if(mDescriptionEditText.getText().toString().isEmpty()) {

            showToast("Task description cannot be blank.");
            mDescriptionEditText.startAnimation(mShakeAnimation);
            return;
        }

        if(mDueDate == null) {

            showToast("Task due date cannot be blank.");
            mDateTextView.startAnimation(mShakeAnimation);
            return;
        }

        // If edit text contains text, then add it and close the dialog
        mListener.onCreatedTask(
            mDescriptionEditText.getText().toString(),
            mDueDate,
            mPriority
        );

        dismiss();
    }

    @OnClick(R.id.cancelDialog)
    public void onCancelDialog(View view){

        dismiss();
    }

    @OnClick(R.id.datePickerTextView)
    public void onDateButtonClick(View view) {

        DatePickerDialog dialog = new DatePickerDialog();
        dialog.setTargetFragment(this, 1);
        dialog.show(mParent.getSupportFragmentManager(), "date_dialog");
    }

    @OnClick(R.id.priorityTextView)
    public void onPriorityClick(View view) {

        DialogPriority dialog = new DialogPriority();
        dialog.setTargetFragment(this, 1);
        dialog.show(mParent.getSupportFragmentManager(), "priority_dialog");
    }

    @Override
    public void onDatePicked(Date date) {

        mDueDate = date;
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        String dateAsString = format.format(mDueDate);
        mDateTextView.setText(dateAsString);
    }

    @Override
    public void onPrioritySelected(int position) {

        mPriority = TaskPriority.values()[position];
        mPriorityTextView.setText(PriorityUtil.getName(mPriority.ordinal()));
        mPriorityMessage.setVisibility(View.GONE);
    }

    private void showToast(String message) {

        Toast
            .makeText(mParent, message, Toast.LENGTH_LONG)
            .show();
    }
}
