package todo.javier.mera.todolist.fragments.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;

import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import todo.javier.mera.todolist.R;

/**
 * Created by javie on 1/23/2017.
 */

public class DialogPriority extends DialogBase {

    private PriorityListener mListener;

    public static DialogPriority newInstance() {

        return new DialogPriority();
    }

    @BindView(R.id.priorityRadioGroup)
    RadioGroup mPriorityGroup;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mListener = (PriorityListener) getTargetFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = LayoutInflater
            .from(getActivity())
            .inflate(R.layout.priority_layout, null);

        ButterKnife.bind(this, view);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setView(view);

        mPriorityGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int buttonId) {

                mListener.onPrioritySelected(buttonId);
                dismiss();
            }
        });
        return dialogBuilder.create();
    }
}
