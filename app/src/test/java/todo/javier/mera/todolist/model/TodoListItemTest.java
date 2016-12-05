package todo.javier.mera.todolist.model;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

/**
 * Created by javie on 11/29/2016.
 */
public class TodoListItemTest {

    private long mExpectedTodolistId = 123;
    private long mExpectedItemId = 321;
    private String mExpectedDescription = "Some task to do";
    private TodoListStatus mExpectedStatus = TodoListStatus.Created;
    private int mExpectedCreationDate = (int) new Date().getTime();
    private TodoListItem mItem;

    @Before
    public void setUp() throws Exception {

        mItem = new TodoListItem(
            mExpectedItemId,
            mExpectedTodolistId,
            mExpectedDescription,
            mExpectedStatus,
            mExpectedCreationDate);
    }

    @Test
    public void getTodoListId() throws Exception {

        // Assert
        Assert.assertEquals(mExpectedTodolistId, mItem.getTodoListId());
    }

    @Test
    public void getItemId() throws Exception {

        // Assert
        Assert.assertEquals(mExpectedItemId, mItem.getItemId());
    }

    @Test
    public void getDescription() throws Exception {

        // Assert
        Assert.assertEquals(mExpectedDescription, mItem.getDescription());
    }

    @Test
    public void getStatus() throws Exception {

        // Assert
        Assert.assertEquals(mExpectedStatus, mItem.getStatus());
    }

    @Test
    public void getCreationDate() throws Exception {

        // Assert
        Assert.assertEquals(mExpectedCreationDate, mItem.getCreationDate());
    }

    @Test
    public void setCompleted() throws Exception {

        // Arrange
        TodoListStatus expectedStatus = TodoListStatus.Completed;

        // Act
        mItem.update(expectedStatus);
        TodoListStatus actualStatus = mItem.getStatus();

        // Assert
        Assert.assertEquals(expectedStatus, actualStatus);
    }
}