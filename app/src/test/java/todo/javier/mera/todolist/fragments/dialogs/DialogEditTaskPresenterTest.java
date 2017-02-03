package todo.javier.mera.todolist.fragments.dialogs;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by javie on 2/3/2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class DialogEditTaskPresenterTest {

    @Mock
    public DialogEditTaskView mView;

    private DialogEditTaskPresenter mPresenter;

    @Before
    public void setUp() throws Exception {

        mPresenter = new DialogEditTaskPresenter(mView);
    }

    @Test
    public void setEnableReminder() throws Exception {

        // Arrange
        boolean isEnable = true;

        // Act
        mPresenter.setEnableReminder(isEnable);

        // Assert
        Mockito.verify(mView).setEnableReminder(isEnable);
    }

    @Test
    public void setReminderText() throws Exception {

        // Arrange
        String text = "";

        // Act
        mPresenter.setReminderText(text);

        // Assert
        Mockito.verify(mView).setReminderText(text);
    }
}
