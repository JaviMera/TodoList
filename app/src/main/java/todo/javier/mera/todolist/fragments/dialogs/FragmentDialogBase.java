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

public abstract class FragmentDialogBase extends DialogFragment {

    private MainActivity mParent;
    private Animation mShakeAnimation;

    protected FragmentDialogListener mListener;
    private String mErrorText;

    protected abstract String getTitle();
    protected abstract String getHint();

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
        mErrorText = mParent.getString(R.string.dialog_todo_list_hint_error);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = LayoutInflater.from(mParent).inflate(R.layout.fragment_dialog, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mParent);
        dialogBuilder.setView(view);

        ButterKnife.bind(this, view);

        mTitleView.setText(getTitle());
        mNameEditText.setHint(getHint());

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

        if(mNameEditText.getText().toString().isEmpty()) {

            mNameEditText.setHint(mErrorText);

            int hintColor = ContextCompat.getColor(mParent, android.R.color.holo_red_light);
            mNameEditText.setHintTextColor(hintColor);
            mNameEditText.startAnimation(mShakeAnimation);
        }
        else {

            mListener.onAddItem(mNameEditText.getText().toString());
            dismiss();
        }
    }
}
