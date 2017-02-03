package todo.javier.mera.todolist.fragments.dialogs;

/**
 * Created by javie on 2/3/2017.
 */
public class DialogEditTaskPresenter {

    private DialogEditTaskView mView;

    public DialogEditTaskPresenter(DialogEditTaskView view) {

        mView = view;
    }

    public void setEnableReminder(boolean isEnable) {

        mView.setEnableReminder(isEnable);
    }

    public void setReminderText(String text) {

        mView.setReminderText(text);
    }
}
