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

    @BindView(R.id.reminderRadioGroup)
    RadioGroup mReminderGroup;

    public static DialogReminder newInstance() {

        DialogReminder dialog = new DialogReminder();
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

        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm");

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mParent);
        dialogBuilder.setView(view);

        mReminderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int buttonId) {

                mListener.onReminderSelected(buttonId);
                dismiss();
            }
        });

        return dialogBuilder.create();
    }
}
