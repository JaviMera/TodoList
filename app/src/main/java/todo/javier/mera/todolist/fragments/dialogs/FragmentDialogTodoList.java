package todo.javier.mera.todolist.fragments.dialogs;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.fragments.FragmentTodoLists;

/**
 * Created by javie on 12/6/2016.
 */

public class FragmentDialogTodoList extends FragmentDialogBase
    implements DatePickerListener{

    @BindView(R.id.datePickerButton)
    Button mDateButton;

    private TodoListDialogListener mListener;
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

        View view = LayoutInflater.from(mParent).inflate(R.layout.fragment_todo_list_dialog, null);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mListener = (FragmentTodoLists)getTargetFragment();
    }

    @OnClick(R.id.datePickerButton)
    public void onDateButtonClick(View view) {

        DatePickerDialog dialog = new DatePickerDialog();
        dialog.setTargetFragment(this, 1);
        dialog.show(mParent.getSupportFragmentManager(), "date_dialog");
    }

    @Override
    public void onDatePicked(Date date) {

        mDueDate = date;
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        String dateAsString = format.format(mDueDate);
        mDateButton.setText(dateAsString);
    }

    @OnClick(R.id.addTaskView)
    public void onAddClick(View view) {

        if(canDismiss()) {

            mListener.onCreateTodoList(mNameEditText.getText().toString(), mDueDate);
            dismiss();
        }
    }
}
