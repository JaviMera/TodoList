package todo.javier.mera.todolist.fragments.dialogs;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by javie on 2/3/2017.
 */

public interface DialogEditView {

    void setTitle(String title);
    void setDescriptionText(String description);
    void setSaveText(String saveText);
    void setDueDateText(Date date, SimpleDateFormat formatter);
    void setPriorityText(int position);
    void startAnimation(View view, Animation animation);
}
