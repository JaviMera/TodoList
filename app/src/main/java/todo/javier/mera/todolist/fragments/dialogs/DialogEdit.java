package todo.javier.mera.todolist.fragments.dialogs;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    implements
    DialogEditView,
    DueDateListener,
    PriorityListener {

    private DialogEditPresenter mPresenter;

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
    protected Priority mPriority;
    protected SimpleDateFormat mFormatter;

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
        mDueDate = null;
        mFormatter = new SimpleDateFormat("LLL, EEE dd  HH:mm", Locale.ENGLISH);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = getLayout();
        ButterKnife.bind(this, view);

        mPresenter = new DialogEditPresenter(this);
        mPresenter.setTitle(getTitle());
        mPresenter.setSaveText(getSaveText());

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mParent);
        dialogBuilder.setView(view);

        if(savedInstanceState != null) {

            long dueDate = savedInstanceState.getLong("due");
            if(dueDate != 0L) {

                mDueDate = new Date();
                mDueDate.setTime(dueDate);
                mPresenter.setDueDateText(mDueDate, mFormatter);
            }

            int priorityPosition = savedInstanceState.getInt("priority");
            mPriority = Priority.values()[priorityPosition];
            mPresenter.setPriorityText(priorityPosition);
        }

        return createDialog(
            dialogBuilder.create()
        );
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putLong("due", mDueDate == null ? 0L : mDueDate.getTime());
        outState.putInt("priority", mPriority.ordinal());
    }

    @OnClick(R.id.dueDateTextView)
    public void onDueDateClick(View view) {

        // Explicitly hide the virtual keyboard when taped on add due date text view
        // if the user didn't press enter after entering a description, the keyboard will still be
        // present.
        mParent.hideSoftKeyboard(view);

        DialogBase dialog = DialogDueDate.newInstance(mDueDate);
        dialog.setTargetFragment(this, 1);
        dialog.show(mParent.getSupportFragmentManager(), "dialog");
    }

    @OnClick(R.id.priorityTextView)
    public void onPriorityClick(View view) {

        // Explicitly hide the virtual keyboard when taped on add due date text view
        // if the user didn't press enter after entering a description, the keyboard will still be
        // present.
        mParent.hideSoftKeyboard(view);

        DialogBase dialog = DialogPriority.newInstance();
        dialog.setTargetFragment(this, 1);
        dialog.show(mParent.getSupportFragmentManager(), "dialog");
    }

    @Override
    public void onDueDateSelected(Date date) {

        mDueDate = new Date();
        mDueDate.setTime(date.getTime());
        mPresenter.setDueDateText(mDueDate, mFormatter);
    }

    @Override
    public void onPrioritySelected(int buttonId) {

        int position = priorityOptions.get(buttonId);
        mPriority = Priority.values()[position];
        mPresenter.setPriorityText(mPriority.ordinal());
    }

    @OnClick(R.id.saveItemView)
    public void onAddClick(View view) {

        if(mEditText.getText().toString().isEmpty()) {

            showToast(mParent, "To-do list title cannot be blank.", Toast.LENGTH_SHORT);
            mPresenter.startAnimation(mEditText, mShakeAnimation);
            return;
        }

        if(mDueDate == null) {

            showToast(mParent, "Task due date cannot be blank.", Toast.LENGTH_SHORT);
            mPresenter.startAnimation(mDueDateTextView, mShakeAnimation);
            return;
        }

        saveItem();
        dismiss();
    }

    @OnClick(R.id.cancelItemView)
    public void onCancelClick(View view) {

        dismiss();
    }

    @Override
    public void setTitle(String title) {

        mTitleView.setText(title);
    }

    @Override
    public void setSaveText(String saveText) {

        mSaveTextView.setText(saveText);
    }

    @Override
    public void setDueDateText(Date date, SimpleDateFormat formatter) {

        mDueDateTextView.setText(formatter.format(mDueDate));
    }

    @Override
    public void setPriorityText(int position) {

        mPriorityTextView.setText(
                PriorityUtil.getName(position)
        );
    }

    @Override
    public void startAnimation(View view, Animation animation) {

        view.startAnimation(animation);
    }

    @Override
    public void setDescriptionText(String description) {

        mEditText.setText(description);
    }
}
