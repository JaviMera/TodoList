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
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.fragments.FragmentTodoList;
import todo.javier.mera.todolist.ui.MainActivity;

/**
 * Created by javie on 12/6/2016.
 */

public class DialogTodoList extends DialogBase {

    private DialogTodoListListener mListener;

    @BindView(R.id.todoListEditTextView)
    EditText mTitleEditText;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mListener = (FragmentTodoList) getTargetFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = LayoutInflater
            .from(mParent)
            .inflate(R.layout.todo_list_dialog, null
        );

        ButterKnife.bind(this, view);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mParent);
        dialogBuilder.setView(view);
        dialogBuilder.setOnKeyListener(getKeyListener());

        return dialogBuilder.create();
    }

    @OnClick(R.id.addTaskView)
    public void onAddClick(View view) {

        if(mTitleEditText.getText().toString().isEmpty()) {

            showToast("To-do list title cannot be blank.");
            mTitleEditText.startAnimation(mShakeAnimation);
            return;
        }

        mListener.onCreateTodoList(mTitleEditText.getText().toString());
        dismiss();
    }

    @OnClick(R.id.cancelDialog)
    public void onCancelClick(View view) {

        dismiss();
        mParent.showFabButton();
    }
}
