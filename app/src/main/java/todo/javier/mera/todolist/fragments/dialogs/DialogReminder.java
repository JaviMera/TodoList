package todo.javier.mera.todolist.fragments.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import todo.javier.mera.todolist.R;

/**
 * Created by javie on 1/30/2017.
 */

public class DialogReminder extends DialogBase {

    private ReminderListener mListener;

    private Date mDueDate;
    private Date mReminderDate;

    @BindView(R.id.reminderRadioGroup)
    RadioGroup mReminderGroup;

    public static DialogReminder newInstance(Date date) {

        DialogReminder dialog = new DialogReminder();
        Bundle bundle = new Bundle();
        bundle.putLong("due_date", date.getTime());
        dialog.setArguments(bundle);

        return dialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mListener = (ReminderListener)getTargetFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = LayoutInflater.from(mParent).inflate(R.layout.reminder_layout, null);

        ButterKnife.bind(this, view);

        long date = getArguments().getLong("due_date");
        mDueDate = new Date();
        mDueDate.setTime(date);

        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        Toast.makeText(mParent.getApplicationContext(), format.format(mDueDate.getTime()), Toast.LENGTH_SHORT).show();

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mParent);
        dialogBuilder.setView(view);

        return dialogBuilder.create();
    }

    @OnClick(R.id.setReminderTextView)
    public void onSetReminderClick(View view) {

        Calendar c = Calendar.getInstance();
        c.setTime(mDueDate);

        int selectedId = mReminderGroup.getCheckedRadioButtonId();
        switch(selectedId) {

            case R.id.fiveBeforeButton:
                c.add(Calendar.MINUTE, -5);
                break;
            case R.id.tenBeforeButton:
                c.add(Calendar.MINUTE, -10);
                break;
            case R.id.twentyBeforeButton:
                c.add(Calendar.MINUTE, -20);
                break;
            case R.id.thirtyBeforeButton:
                c.add(Calendar.MINUTE, -30);
                break;
            case R.id.hourBeforeButton:
                c.add(Calendar.MINUTE, -60);
                break;
        }

        mListener.onReminderSelected(c.getTime());
        dismiss();
    }
}
