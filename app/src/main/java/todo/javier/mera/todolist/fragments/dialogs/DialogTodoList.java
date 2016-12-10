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

public class DialogTodoList extends DialogBase
    implements DatePickerListener {

    @BindView(R.id.datePickerButton)
    Button mDateButton;

    private DialogTodoListListener mListener;
    private Date mDueDate;

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

        View view = LayoutInflater.from(mParent).inflate(R.layout.todo_list_dialog, null);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mListener = (FragmentTodoList)getTargetFragment();
    }

    @OnClick(R.id.datePickerButton)
    public void onDateButtonClick(View view) {

        DatePickerDialog dialog = new DatePickerDialog();
        dialog.setTargetFragment(this, 1);
        dialog.show(mParent.getSupportFragmentManager(), "date_dialog");
    }

    @OnClick(R.id.addTaskView)
    public void onAddClick(View view) {

        if(canDismiss()) {

            mListener.onCreateTodoList(mNameEditText.getText().toString(), mDueDate);
            dismiss();
        }
    }

    @Override
    public void onDatePicked(Date date) {

        mDueDate = date;
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        String dateAsString = format.format(mDueDate);
        mDateButton.setText(dateAsString);
    }
}
