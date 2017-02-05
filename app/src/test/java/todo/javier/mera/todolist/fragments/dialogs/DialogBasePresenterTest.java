package todo.javier.mera.todolist.fragments.dialogs;

import android.app.AlertDialog;
import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import todo.javier.mera.todolist.ui.TodosActivity;

/**
 * Created by javie on 12/6/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class DialogBasePresenterTest {

    @Mock
    public DialogBaseView mView;

    private DialogBasePresenter mPresenter;

    @Before
    public void setUp() throws Exception {

        mPresenter = new DialogBasePresenter(mView);
    }

    @Test
    public void showToast() throws Exception {

        // Arrange
        Context ctx = null;
        String msg = "";
        int duration = 0;

        // Act
        mPresenter.showToast(ctx, msg, duration);

        // Assert
        Mockito.verify(mView).showToast(ctx, msg, duration);
    }

    @Test
    public void createDialog() throws Exception {

        // Arrange
        Context ctx = new TodosActivity();
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ctx);

        // Act
        mPresenter.createDialog(dialogBuilder.create());

        // Assert
        Mockito.verify(mView).createDialog(dialogBuilder.create());
    }
}