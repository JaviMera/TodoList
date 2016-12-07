package todo.javier.mera.todolist.fragments.dialogs;

import android.view.animation.Animation;

/**
 * Created by javie on 12/6/2016.
 */
public class DialogFragmentPresenter {

    private DialogFragmentView mView;

    public DialogFragmentPresenter(DialogFragmentView view) {

        mView = view;
    }

    public void updateEditTextHintColor(int colorId) {

        mView.updateEditTextHintColor(colorId);
    }

    public void startEditTextAnimation(Animation anim) {

        mView.startEditTextAnim(anim);
    }

    public void updateEditTextHint(String text) {

        mView.updateEditTextHint(text);
    }

    public void setDialogTitle(String title) {

        mView.setDialogTitle(title);
    }
}
