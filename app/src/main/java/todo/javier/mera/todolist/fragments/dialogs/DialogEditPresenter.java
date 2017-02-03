package todo.javier.mera.todolist.fragments.dialogs;

import android.view.View;
import android.view.animation.Animation;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by javie on 2/3/2017.
 */
public class DialogEditPresenter {

    private DialogEditView mView;

    public DialogEditPresenter(DialogEditView view) {

        mView = view;
    }

    public void setTitle(String title) {

        mView.setTitle(title);
    }


    public void setSaveText(String saveText) {

        mView.setSaveText(saveText);
    }

    public void setDueDateText(Date date, SimpleDateFormat formatter) {

        mView.setDueDateText(date, formatter);
    }

    public void setPriorityText(int position) {

        mView.setPriorityText(position);

    }

    public void startAnimation(View view, Animation anim) {

        mView.startAnimation(view, anim);
    }

    public void setDescriptionText(String description) {

        mView.setDescriptionText(description);
    }
}
