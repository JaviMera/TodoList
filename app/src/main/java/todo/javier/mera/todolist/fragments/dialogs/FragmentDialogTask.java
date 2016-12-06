package todo.javier.mera.todolist.fragments.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.fragments.FragmentTodoList;
import todo.javier.mera.todolist.ui.MainActivity;

/**
 * Created by javie on 12/6/2016.
 */

public class FragmentDialogTask extends DialogFragment {

    private MainActivity mParent;
    private FragmentDialogListener mListener;

    @BindView(R.id.taskEditTextView)
    EditText mEditText;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mParent = (MainActivity) context;
        mListener = (FragmentTodoList)getTargetFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = LayoutInflater.from(mParent).inflate(R.layout.fragment_dialog_task, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mParent);
        dialogBuilder.setView(view);

        ButterKnife.bind(this, view);

        return dialogBuilder.create();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getDialog()
            .getWindow()
            .getAttributes()
            .windowAnimations = R.style.FragmentDialogTaskAnimations;
    }

    @OnClick(R.id.cancelTaskView)
    public void onCancelClick(View view) {

    }

    @OnClick(R.id.addTaskView)
    public void onAddClick(View view) {

        mListener.onAddTask(mEditText.getText().toString());
        dismiss();
    }
}
