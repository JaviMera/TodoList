package todo.javier.mera.todolist.fragments.dialogs;

import android.view.animation.Animation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

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
    public void setDialogTitle() throws Exception {

        // Arrange
        String title = "harambe's memoirs";

        // Act
        mPresenter.setDialogTitle(title);

        // Assert
        Mockito.verify(mView).setDialogTitle(title);
    }

    @Test
    public void updateHintTextColor() throws Exception {

        // Arrange
        int colorId = 1234;

        // Act
        mPresenter.updateEditTextHintColor(colorId);

        // Assert
        Mockito.verify(mView).updateEditTextHintColor(colorId);
    }

    @Test
    public void startEditTextAnimation() throws Exception {

        // Arrange
        Animation anim = null;

        // Act
        mPresenter.startEditTextAnimation(anim);

        // Assert
        Mockito.verify(mView).startEditTextAnim(anim);
    }

    @Test
    public void updateEditTextHint() throws Exception {

        // Arrange
        String text = "harambe";

        // Act
        mPresenter.updateEditTextHint(text);

        // Assert
        Mockito.verify(mView).updateEditTextHint(text);
    }
}