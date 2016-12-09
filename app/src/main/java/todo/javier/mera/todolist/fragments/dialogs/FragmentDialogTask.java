package todo.javier.mera.todolist.fragments.dialogs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import butterknife.OnClick;
import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.fragments.FragmentTasks;

/**
 * Created by javie on 12/6/2016.
 */

public class FragmentDialogTask extends FragmentDialogBase {

    private FragmentDialogListener mListener;

    @Override
    protected String getTitle() {

        return "Create a new Task!";
    }

    @Override
    protected String getHint() {

        return "enter task";
    }

    @Override
    protected String getHintError() {

        return "task description can't be blank";
    }

    @Override
    protected View getDialogView() {

        View view = LayoutInflater.from(mParent).inflate(R.layout.fragment_dialog, null);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (FragmentTasks)getTargetFragment();
    }

    @OnClick(R.id.addTaskView)
    public void onAddClick() {

        if(canDismiss()) {

            // If edit text contains text, then add it and close the dialog
            mListener.onAddItem(mNameEditText.getText().toString());
            dismiss();
        }
    }
}
