package todo.javier.mera.todolist.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import todo.javier.mera.todolist.MainActivity;
import todo.javier.mera.todolist.R;

/**
 * Created by Javier on 11/28/2016.
 */

public class AddTodoListDialogFragment extends DialogFragment {

    private MainActivity mParent;
    private DialogView mListener;

    @BindView(R.id.todoListNameEditText) EditText mNameEditText;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mParent = (MainActivity) getActivity();
        mListener = mParent;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = LayoutInflater.from(mParent).inflate(R.layout.dialog_add_todo_list, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mParent);
        dialogBuilder.setView(view);

        ButterKnife.bind(this, view);

        return dialogBuilder.create();
    }

    @OnClick(R.id.todoListAddButton)
    public void onAddButtonClick(View view) {

        String name = mNameEditText.getText().toString();
        mListener.onAddTodoList(name);
        dismiss();
    }
}
