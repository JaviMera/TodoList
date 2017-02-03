package todo.javier.mera.todolist.fragments.dialogs;

import android.view.View;
import android.view.animation.Animation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

import todo.javier.mera.todolist.model.PriorityUtil;

/**
 * Created by javie on 2/3/2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class DialogEditPresenterTest {

    @Mock
    public DialogEditView mView;

    private DialogEditPresenter mPresenter;

    @Before
    public void setUp() throws Exception {

        mPresenter = new DialogEditPresenter(mView);
    }

    @Test
    public void setTitle() throws Exception {

        // Arrange
        String title = "";

        // Act
        mPresenter.setTitle(title);

        // Assert
        Mockito.verify(mView).setTitle(title);
    }

    @Test
    public void setSaveText() throws Exception {

        // Arrange
        String saveText = "";

        // Act
        mPresenter.setSaveText(saveText);

        // Assert
        Mockito.verify(mView).setSaveText(saveText);
    }

    @Test
    public void setDueDateText() throws Exception {

        // Arrange
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat();
        // Act
        mPresenter.setDueDateText(date, formatter);

        // Assert
        Mockito.verify(mView).setDueDateText(date, formatter);
    }

    @Test
    public void setPriorityText() throws Exception {

        // Arrange
        int position = 1;

        // Act
        mPresenter.setPriorityText(position);

        // Assert
        Mockito.verify(mView).setPriorityText(position);
    }

    @Test
    public void startAnimation() throws Exception {

        // Arrange
        View view = null;
        Animation anim = null;

        // Act
        mPresenter.startAnimation(view, anim);

        // Assert
        Mockito.verify(mView).startAnimation(view, anim);
    }

    @Test
    public void setDescriptionText() throws Exception {

        // Arrange
        String description = "";

        // Act
        mPresenter.setDescriptionText(description);

        // Assert
        Mockito.verify(mView).setDescriptionText(description);
    }
}
