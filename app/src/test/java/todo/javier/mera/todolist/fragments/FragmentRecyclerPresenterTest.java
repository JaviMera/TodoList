package todo.javier.mera.todolist.fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

/**
 * Created by javie on 11/29/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class FragmentRecyclerPresenterTest {

    @Mock
    public FragmentRecyclerView mView;

    private FragmentRecyclerPresenter mPresenter;

    @Before
    public void setUp() throws Exception {

        mPresenter = new FragmentRecyclerPresenter(mView);
    }

    @Test
    public void setAdapter() throws Exception {

        // Arrange
        Context awesomeContext = null;

        // Act
        mPresenter.setAdapter(awesomeContext);

        // Assert
        Mockito.verify(mView).setAdapter(awesomeContext);
    }

    @Test
    public void setItemAnimation() throws Exception {

        // Arrange
        RecyclerView.ItemAnimator animator = null;

        // Act
        mPresenter.setItemAnimator(animator);

        // Assert
        Mockito.verify(mView).setItemAnimator(animator);
    }

    @Test
    public void setLayoutManager() throws Exception {

        // Arrange
        Context context = null;

        // Act
        mPresenter.setLayoutManager(context);

        // Assert
        Mockito.verify(mView).setLayoutManager(context);
    }
}