package todo.javier.mera.todolist.fragments.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import todo.javier.mera.todolist.R;

/**
 * Created by javie on 12/6/2016.
 */

public abstract class DialogEditTask extends DialogEdit
    implements
    DialogEditTaskView,
    ReminderListener {

    private DialogEditTaskPresenter mPresenter;

    protected int mReminderTime;

    protected Map<Integer, Integer> reminderOptions = new LinkedHashMap<Integer, Integer>() {
        {
            put(R.id.fiveBeforeButton, -5);
            put(R.id.tenBeforeButton, -10);
            put(R.id.twentyBeforeButton, -20);
            put(R.id.thirtyBeforeButton, -30);
            put(R.id.hourBeforeButton, -60);
        }
    };

    @BindView(R.id.reminderTextView)
    TextView mReminderTextView;

    @Override
    protected String getTitle() {

        return "Create new task!";
    }

    @Override
    protected View getLayout() {

        View view = LayoutInflater.from(mParent).inflate(R.layout.task_dialog, null);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mReminderTime = -1;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog = super.onCreateDialog(savedInstanceState);

        // Add reminder dialog to the map of dialogs
        mDialogs.put(R.id.reminderTextView, DialogReminder.newInstance());

        mPresenter = new DialogEditTaskPresenter(this);
        mPresenter.setEnableReminder(false);

        return dialog;
    }

    @Override
    public void onDueDateSelected(Date date) {
        super.onDueDateSelected(date);

        mPresenter.setEnableReminder(true);

        String reminderText = mParent.getString(R.string.task_reminder_text);
        mPresenter.setReminderText(reminderText);
    }

    @OnClick(R.id.reminderTextView)
    public void onReminderClick(View view) {

        super.onTextViewClick(view);
    }

    @Override
    public void onReminderSelected(int buttonId) {

        mReminderTime = buttonId;
        Date reminder = getReminderDate(mReminderTime);

        mPresenter.setReminderText(
            mFormatter.format(reminder)
        );
    }

    @Override
    public void setEnableReminder(boolean isEnabled) {

        mReminderTextView.setEnabled(isEnabled);
    }

    @Override
    public void setReminderText(String text) {

        mReminderTextView.setText(text);
    }

    protected Date getReminderDate(int buttonId) {

        Calendar c = Calendar.getInstance();
        c.setTime(mDueDate);
        int reminderMinutes = reminderOptions.get(buttonId);
        c.add(Calendar.MINUTE, reminderMinutes);

        return c.getTime();
    }
}
