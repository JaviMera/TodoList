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
import todo.javier.mera.todolist.fragments.FragmentTask;

/**
 * Created by javie on 12/6/2016.
 */

public class DialogTask extends DialogBase
    implements DatePickerListener{

    private Date mDueDate;
    private DialogTaskListener mListener;

    @BindView(R.id.datePickerButton)
    Button mDateButton;

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

        View view = LayoutInflater.from(mParent).inflate(R.layout.task_dialog, null);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (FragmentTask)getTargetFragment();
    }

    @OnClick(R.id.addTaskView)
    public void onAddClick() {

        if(canDismiss()) {

            // If edit text contains text, then add it and close the dialog
            mListener.onCreatedTask(
                mNameEditText.getText().toString(),
                mDueDate
            );

            dismiss();
        }
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
}
