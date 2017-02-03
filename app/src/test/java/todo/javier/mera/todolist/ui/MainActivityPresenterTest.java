package todo.javier.mera.todolist.ui;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.LinkedHashMap;
import java.util.Map;

import todo.javier.mera.todolist.model.ItemBase;

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
    public void showSnackBar() throws Exception {

        // Arrange
        String message = "sup";
        String action = "I dare you to click";
        Map<Integer, ItemBase> items = new LinkedHashMap<>();

        // Act
        mPresenter.showSnackBar(message, action, items);

        // Assert
        Mockito.verify(mView).showSnackBar(message,action,items);
    }

    @Test
    public void setIndicator() throws Exception {

        // Arrange
        int resourceId = 0;

        // Act
        mPresenter.setIndicator(resourceId);

        // Assert
        Mockito.verify(mView).setIndicator(resourceId);
    }
}