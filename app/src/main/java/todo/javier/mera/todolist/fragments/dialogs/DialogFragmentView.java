package todo.javier.mera.todolist.fragments.dialogs;

import android.content.Context;
import android.view.animation.Animation;

/**
 * Created by javie on 12/6/2016.
 */
public interface DialogFragmentView {

    void updateEditTextHintColor(int colorId);
    void updateEditTextHint(String text);
    void startEditTextAnim(Animation anim);
    void setDialogTitle(String title);
}
