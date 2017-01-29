package todo.javier.mera.todolist.fragments.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;
import todo.javier.mera.todolist.R;

/**
 * Created by javie on 1/23/2017.
 */

public class DialogPriority extends DialogFragment {

    private PriorityListener mListener;

    private static Map<Integer, Integer> priorityMap = new LinkedHashMap<Integer, Integer>() {
        {
            put(R.id.priorityNoneLayout, 0);
            put(R.id.priorityLowLayout, 1);
            put(R.id.priorityMediumLayout, 2);
            put(R.id.priorityHighLayout, 3);
        }
    };

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

        return dialogBuilder.create();
    }

    @OnClick({
    R.id.priorityNoneLayout,
    R.id.priorityLowLayout,
    R.id.priorityMediumLayout,
    R.id.priorityHighLayout})
    public void onPriorityClicked(View view) {

        mListener.onPrioritySelected(priorityMap.get(view.getId()));
        dismiss();
    }
}
