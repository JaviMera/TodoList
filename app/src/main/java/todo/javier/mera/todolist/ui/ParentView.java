package todo.javier.mera.todolist.ui;

import android.view.View;

/**
 * Created by javie on 12/2/2016.
 */
public interface ParentView {

    void hideKeyboard();
    void startActivityWithTransition(View viewToAnimate);
}
