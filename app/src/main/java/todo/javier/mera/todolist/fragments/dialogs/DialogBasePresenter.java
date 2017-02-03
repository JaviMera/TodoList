package todo.javier.mera.todolist.fragments.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.view.animation.Animation;

/**
 * Created by javie on 12/6/2016.
 */
class DialogBasePresenter {

    private DialogBaseView mView;

    DialogBasePresenter(DialogBaseView view) {

        mView = view;
    }

    public void showToast(Context ctx, String msg, int duration) {

        mView.showToast(ctx, msg, duration);
    }

    public AlertDialog createDialog(AlertDialog dialog) {

        return mView.createDialog(dialog);
    }
}
