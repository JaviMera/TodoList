package todo.javier.mera.todolist.fragments.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.ui.ActivityBase;

/**
 * Created by javie on 12/6/2016.
 */

public abstract class DialogBase extends DialogFragment
    implements DialogBaseView {

    protected ActivityBase mParent;
    protected Animation mShakeAnimation;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mParent = (ActivityBase)context;
        mShakeAnimation = AnimationUtils.loadAnimation(mParent, R.anim.shake);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Initialize dialog animations when this event is fired.
        getDialog()
            .getWindow()
            .getAttributes()
            .windowAnimations = R.style.FragmentDialogTaskAnimations;
    }

    @Override
    public void showToast(Context ctx, String message, int duration) {

        Toast
            .makeText(ctx, message, duration)
            .show();
    }

    @Override
    public AlertDialog createDialog(AlertDialog dialog) {

        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }
}
