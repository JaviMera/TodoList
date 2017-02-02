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
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.model.Priority;
import todo.javier.mera.todolist.model.PriorityUtil;

/**
 * Created by javie on 1/30/2017.
 */

public abstract class DialogEdit extends DialogBase
    implements DueDateListener, PriorityListener {

    protected Map<Integer, Integer> priorityOptions = new LinkedHashMap<Integer, Integer>() {
        {
            put(R.id.noneButton, 0);
            put(R.id.lowButton, 1);
            put(R.id.mediumButton, 2);
            put(R.id.highButton, 3);
        }
    };

    protected abstract String getTitle();
    protected abstract View getLayout();
    protected abstract void saveItem();
    protected abstract String getSaveText();

    protected Date mDueDate;
    protected long mDueTime;
    protected Priority mPriority;

    @BindView(R.id.dialogTitleView)
    TextView mTitleView;

    protected @BindView(R.id.editTextView)
    EditText mEditText;

    protected @BindView(R.id.dueDateTextView)
    TextView mDueDateTextView;

    protected @BindView(R.id.priorityTextView)
    TextView mPriorityTextView;

    @BindView(R.id.priorityMessageTextView)
    TextView mPriorityMessage;

    @BindView(R.id.saveItemView)
    TextView mSaveTextView;

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
        mSaveTextView.setText(getSaveText());
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mParent);
        dialogBuilder.setView(view);

        return createDialog(
            dialogBuilder.create()
        );
    }

    @OnClick(R.id.dueDateTextView)
    public void onDueDateClick(View view) {

        // Explicitly hide the virtual keyboard when taped on add due date text view
        // if the user didn't press enter after entering a description, the keyboard will still be
        // present.
        mParent.hideSoftKeyboard(view);

        DialogDueDate dialog = DialogDueDate.newInstance();
        dialog.setTargetFragment(this, 1);
        dialog.show(mParent.getSupportFragmentManager(), "due_date_dialog");
    }

    @OnClick(R.id.priorityTextView)
    public void onPriorityClick(View view) {

        // Explicitly hide the virtual keyboard when taped on add due date text view
        // if the user didn't press enter after entering a description, the keyboard will still be
        // present.
        mParent.hideSoftKeyboard(view);

        DialogPriority dialog = new DialogPriority();
        dialog.setTargetFragment(this, 1);
        dialog.show(mParent.getSupportFragmentManager(), "priority_dialog");
    }

    @Override
    public void onDueDateSelected(Date date) {

        mDueDate = new Date();
        mDueDate.setTime(date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("LLL, EEE dd  HH:mm", Locale.ENGLISH);

        mDueDateTextView.setText(format.format(mDueDate));
    }

    @Override
    public void onPrioritySelected(int buttonId) {

        int position = priorityOptions.get(buttonId);
        mPriority = Priority.values()[position];
        mPriorityTextView.setText(PriorityUtil.getName(mPriority.ordinal()));
    }

    @OnClick(R.id.saveItemView)
    public void onAddClick(View view) {

        if(mEditText.getText().toString().isEmpty()) {

            showToast("To-do list title cannot be blank.");
            mEditText.startAnimation(mShakeAnimation);
            return;
        }

        if(mDueDate == null) {

            showToast("Task due date cannot be blank.");
            mDueDateTextView.startAnimation(mShakeAnimation);
            return;
        }

        saveItem();
        dismiss();
    }

    @OnClick(R.id.cancelItemView)
    public void onCancelClick(View view) {

        dismiss();
    }
}
