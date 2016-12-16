package todo.javier.mera.todolist.fragments.dialogs;

import android.view.animation.Animation;

/**
 * Created by javie on 12/6/2016.
 */
class DialogBasePresenter {

    private DialogBaseView mView;

    DialogBasePresenter(DialogBaseView view) {

        mView = view;
    }

    void updateEditTextHintColor(int colorId) {

        mView.updateEditTextHintColor(colorId);
    }

    void startEditTextAnimation(Animation anim) {

        mView.startEditTextAnim(anim);
    }

    void updateEditTextHint(String text) {

        mView.updateEditTextHint(text);
    }

    void setDialogTitle(String title) {

        mView.setDialogTitle(title);
    }
}
