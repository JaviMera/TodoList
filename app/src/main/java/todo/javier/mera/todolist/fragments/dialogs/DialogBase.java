package todo.javier.mera.todolist.fragments.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.ui.MainMainActivity;

/**
 * Created by javie on 12/6/2016.
 */

public abstract class DialogBase extends DialogFragment
    implements DialogBaseView {

    protected MainMainActivity mParent;
    protected DialogBasePresenter mPresenter;
    protected Animation mShakeAnimation;

    protected abstract String getTitle();
    protected abstract String getHint();
    protected abstract String getHintError();
    protected abstract View getDialogView();

    @BindView(R.id.dialogTitleView)
    TextView mTitleView;

    protected @BindView(R.id.taskEditTextView)
    EditText mNameEditText;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mParent = (MainMainActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mShakeAnimation = AnimationUtils.loadAnimation(mParent, R.anim.shake);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = getDialogView();
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mParent);
        dialogBuilder.setView(view);

        ButterKnife.bind(this, view);

        mPresenter = new DialogBasePresenter(this);
        mPresenter.setDialogTitle(getTitle());
        mPresenter.updateEditTextHint(getHint());

        int colorId = ContextCompat.getColor(mParent, android.R.color.darker_gray);
        mPresenter.updateEditTextHintColor(colorId);

        AlertDialog dialog = dialogBuilder.create();
        dialog.setCanceledOnTouchOutside(false);

        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
                if ((keyCode == android.view.KeyEvent.KEYCODE_BACK)) {

                    mParent.showFabButton();
                }

                return false;
            }
        });
        return dialog;
    }

    @OnClick(R.id.cancelDialog)
    public void onCancelDialog(View view) {

        mParent.showFabButton();
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

    @OnClick(R.id.cancelDialog)
    public void onCancelClick(View view) {

        dismiss();
    }

    protected boolean canDismiss() {

        if(mNameEditText.getText().toString().isEmpty()) {

            // If the edit text is empty, then show the user the error
            mPresenter.updateEditTextHint(getHintError());

            int hintColor = ContextCompat.getColor(mParent, android.R.color.holo_red_light);
            mPresenter.updateEditTextHintColor(hintColor);
            mPresenter.startEditTextAnimation(mShakeAnimation);

            return false;
        }

        return true;
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
