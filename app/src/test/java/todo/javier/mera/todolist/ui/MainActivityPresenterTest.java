package todo.javier.mera.todolist.ui;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by javie on 12/17/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class MainActivityPresenterTest {

    @Mock
    public MainActivityView mView;

    private MainActivityPresenter mPresenter;

    @Before
    public void setUp() throws Exception {

        mPresenter = new MainActivityPresenter(mView);
    }

    @Test
    public void updateToolbarBackground() throws Exception {

        // Arrange
        int color = 1234;

        // Act
        mPresenter.updateToolbarBackground(color);

        // Assert
        Mockito.verify(mView).updateToolbarBackground(color);
    }

    @Test
    public void showAddButton() throws Exception {

        // Act
        mPresenter.showFabButton();

        // Assert
        Mockito.verify(mView).showFabButton();
    }

    @Test
    public void toggleBackButton() throws Exception {

        // Arrange
        boolean canDisplay = false;

        // Act
        mPresenter.toggleBackButton(canDisplay);

        // Assert
        Mockito.verify(mView).toggleBackButton(canDisplay);
    }

    @Test
    public void setToolbar() throws Exception {

        // Act
        mPresenter.setToolbar();

        // Assert
        Mockito.verify(mView).setToolbar();
    }

    @Test
    public void setToolbarTitle() throws Exception {

        // Arrange
        String title = "";

        // Act
        mPresenter.setToolbarTitle(title);

        // Assert
        Mockito.verify(mView).setToolbarTitle(title);
    }

    @Test
    public void setFabVisibility() throws Exception {

        // Arrange
        int visibility = 1;

        // Act
        mPresenter.setFabVisibility(visibility);

        // Assert
        Mockito.verify(mView).setFabVisibility(visibility);
    }

    @Test
    public void showSnackbar() throws Exception {

        // Arrange
        String message = "sup";
        String action = "undo";

        // Act
        mPresenter.showSnackbar(message, action);

        // Assert
        Mockito.verify(mView).showSnackBar(message, action);
    }
}