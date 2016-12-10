package todo.javier.mera.todolist.fragments.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import todo.javier.mera.todolist.R;

/**
 * Created by javie on 12/9/2016.
 */

public class DatePickerDialog extends DialogFragment {

    private DatePickerListener mListener;

    @BindView(R.id.datePickerView)
    DatePicker mDate;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (DatePickerListener) getTargetFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(
            R.layout.date_picker_dialog,
            null
        );

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setView(view);

        ButterKnife.bind(this, view);

        return dialogBuilder.create();
    }

    @OnClick(R.id.addTaskView)
    public void onAddDateClick(View view) {

        String date = mDate.getMonth() + "/" + mDate.getDayOfMonth() + "/" + mDate.getYear();

        Calendar c = Calendar.getInstance();
        c.set(mDate.getYear(), mDate.getMonth(), mDate.getDayOfMonth());
        mListener.onDatePicked(c.getTime());
        dismiss();
    }
}
