package todo.javier.mera.todolist.fragments.dialogs;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.renderscript.RenderScript;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.adapters.PrioritySpinnerAdapter;
import todo.javier.mera.todolist.fragments.FragmentTask;
import todo.javier.mera.todolist.model.Priority;
import todo.javier.mera.todolist.model.TaskPriority;

/**
 * Created by javie on 12/6/2016.
 */

public class DialogTask extends DialogBase
    implements DatePickerListener{

    private Date mDueDate;
    private DialogTaskListener mListener;
    private TaskPriority mPriority;

    @BindView(R.id.datePickerButton)
    Button mDateButton;

    @BindView(R.id.prioritySpinnerView)
    Spinner mPrioritySpinner;

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
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (FragmentTask)getTargetFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPriority = TaskPriority.None;
    }

    @Override
    protected View getDialogView() {

        View view = LayoutInflater.from(mParent).inflate(R.layout.task_dialog, null);
        ButterKnife.bind(this, view);

        String[] priorityNames = mParent.getResources().getStringArray(R.array.priority_array);
        int[] priorityDrawables = new int[]{

            R.drawable.priority_none_background,
            R.drawable.priority_low_background,
            R.drawable.priority_medium_background,
            R.drawable.priority_high_background
        };

        Priority[] priorites = new Priority[priorityDrawables.length];

        for(int i = 0 ; i < priorites.length; i++) {

            priorites[i] = new Priority(
                priorityNames[i],
                priorityDrawables[i]
            );
        }

        PrioritySpinnerAdapter adapter = new PrioritySpinnerAdapter(mParent, priorites);
        mPrioritySpinner.setAdapter(adapter);

        mPrioritySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                mPriority = TaskPriority.values()[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return view;
    }


    @OnClick(R.id.addTaskView)
    public void onAddClick() {

        if(canDismiss()) {

            // If edit text contains text, then add it and close the dialog
            mListener.onCreatedTask(
                mNameEditText.getText().toString(),
                mDueDate,
                mPriority
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
