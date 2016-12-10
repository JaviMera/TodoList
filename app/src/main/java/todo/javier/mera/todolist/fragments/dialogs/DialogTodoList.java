package todo.javier.mera.todolist.fragments.dialogs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.fragments.FragmentTodoList;

/**
 * Created by javie on 12/6/2016.
 */

public class DialogTodoList extends DialogBase {

    private DialogTodoListListener mListener;


    @Override
    protected String getTitle() {

        return "Create a new To-do list!";
    }

    @Override
    protected String getHint() {

        return "enter todo list";
    }

    @Override
    protected String getHintError() {

        return "To-do list name can't be blank";
    }

    @Override
    protected View getDialogView() {

        View view = LayoutInflater.from(mParent).inflate(R.layout.dialog_base, null);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (FragmentTodoList)getTargetFragment();
    }

    @OnClick(R.id.addTaskView)
    public void onAddClick(View view) {

        if(canDismiss()) {

            mListener.onCreateTodoList(mNameEditText.getText().toString());
            dismiss();
        }
    }
}
