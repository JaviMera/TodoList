package todo.javier.mera.todolist.fragments.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.ui.MainActivity;

/**
 * Created by javie on 12/6/2016.
 */

public abstract class DialogBase extends DialogFragment {

    protected MainActivity mParent;
    protected Animation mShakeAnimation;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mParent = (MainActivity)context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mShakeAnimation = AnimationUtils.loadAnimation(mParent, R.anim.shake);
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

    protected void showToast(String message) {

        Toast
            .makeText(mParent, message, Toast.LENGTH_LONG)
            .show();
    }

    protected AlertDialog createDialog(AlertDialog.Builder builder) {

        AlertDialog dialog = builder.create();
        dialog.setOnKeyListener(getKeyListener());
        dialog.setCanceledOnTouchOutside(false);

        return dialog;
    }

    private DialogInterface.OnKeyListener getKeyListener() {

        return new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {

                if ((keyCode == android.view.KeyEvent.KEYCODE_BACK)) {

                    mParent.showFabButton();
                }

                return false;
            }
        };
    }
}
