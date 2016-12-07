package todo.javier.mera.todolist.fragments.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.ui.MainActivity;

/**
 * Created by javie on 12/6/2016.
 */

public abstract class FragmentDialogBase extends DialogFragment
    implements DialogFragmentView {

    private MainActivity mParent;
    private Animation mShakeAnimation;
    private DialogFragmentPresenter mPresenter;

    protected FragmentDialogListener mListener;

    protected abstract String getTitle();
    protected abstract String getHint();
    protected abstract String getHintError();

    @BindView(R.id.dialogTitleView)
    TextView mTitleView;

    @BindView(R.id.taskEditTextView)
    EditText mNameEditText;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mParent = (MainActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mShakeAnimation = AnimationUtils.loadAnimation(mParent, R.anim.shake);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = LayoutInflater.from(mParent).inflate(R.layout.fragment_dialog, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mParent);
        dialogBuilder.setView(view);

        ButterKnife.bind(this, view);

        mPresenter = new DialogFragmentPresenter(this);
        mPresenter.setDialogTitle(getTitle());
        mPresenter.updateEditTextHint(getHint());

        int colorId = ContextCompat.getColor(mParent, android.R.color.darker_gray);
        mPresenter.updateEditTextHintColor(colorId);

        return dialogBuilder.create();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Initialize dialog animations when this event is fired.
        getDialog()
            .getWindow()
            .getAttributes()
            .windowAnimations = R.style.FragmentDialogTaskAnimations;
    }

    @OnClick(R.id.cancelTaskView)
    public void onCancelClick(View view) {

        dismiss();
    }

    @OnClick(R.id.addTaskView)
    public void onAddClick(View view) {

        // Check if the user has not entered a description
        if(mNameEditText.getText().toString().isEmpty()) {

            // If the edit text is empty, then show the user the error
            mPresenter.updateEditTextHint(getHintError());

            int hintColor = ContextCompat.getColor(mParent, android.R.color.holo_red_light);
            mPresenter.updateEditTextHintColor(hintColor);
            mPresenter.startEditTextAnimation(mShakeAnimation);
        }
        else {

            // If edit text contains text, then add it and close the dialog
            mListener.onAddItem(mNameEditText.getText().toString());
            dismiss();
        }
    }

    @Override
    public void setDialogTitle(String title) {

        mTitleView.setText(title);
    }

    @Override
    public void updateEditTextHintColor(int colorId) {

        mNameEditText.setHintTextColor(colorId);
    }

    @Override
    public void updateEditTextHint(String text) {

        mNameEditText.setHint(text);
    }

    @Override
    public void startEditTextAnim(Animation anim) {

        mNameEditText.startAnimation(anim);
    }
}