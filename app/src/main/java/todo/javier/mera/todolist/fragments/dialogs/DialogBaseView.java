package todo.javier.mera.todolist.fragments.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.view.animation.Animation;

/**
 * Created by javie on 12/6/2016.
 */
public interface DialogBaseView {

    void showToast(Context ctx, String message, int duration);
    AlertDialog createDialog(AlertDialog dialog);
}
