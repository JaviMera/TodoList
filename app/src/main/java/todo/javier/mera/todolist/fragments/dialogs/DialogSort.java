package todo.javier.mera.todolist.fragments.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.database.TodoListSQLiteHelper;
import todo.javier.mera.todolist.fragments.FragmentTask;

/**
 * Created by javie on 12/14/2016.
 */
public class DialogSort extends DialogFragment{

    private DialogSortListener mListener;

    @BindView(R.id.sortRadioGroup)
    RadioGroup mSortRadioGroup;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mListener = (DialogSortListener)getTargetFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.sort_dialog, null);
        ButterKnife.bind(this, view);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setView(view);

        return dialogBuilder.create();
    }

    @OnClick(R.id.cancelDialogSort)
    public void onCancelDialogClick(View view) {

        dismiss();
    }

    @OnClick(R.id.okDialogSort)
    public void onOkDialogClick(View view) {

        String columnSortBy = "";

        switch(mSortRadioGroup.getCheckedRadioButtonId()) {

            case R.id.sortByNone:
                columnSortBy = TodoListSQLiteHelper.COLUMN_ITEMS_POSITION;
                break;

            case R.id.sortByDueDate:
                columnSortBy = TodoListSQLiteHelper.COLUMN_ITEMS_DUE_DATE;
                break;

            case R.id.sortByPriority:
                columnSortBy = TodoListSQLiteHelper.COLUMN_ITEMS_PRIORITY;
                break;

            case R.id.sortByCompleted:
                columnSortBy = TodoListSQLiteHelper.COLUMN_ITEMS_STATUS;
                break;
        }

        mListener.onSortSelected(columnSortBy);
        dismiss();
    }
}
