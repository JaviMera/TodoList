package todo.javier.mera.todolist.ui;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by javie on 2/5/2017.
 */

public abstract class ActivityBase extends AppCompatActivity {

    public void hideSoftKeyboard(View view) {

        InputMethodManager manager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
