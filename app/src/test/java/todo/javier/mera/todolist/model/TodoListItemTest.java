package todo.javier.mera.todolist.model;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by javie on 11/29/2016.
 */
public class TodoListItemTest {

    private TodoListItem mItem;

    @Before
    public void setUp() throws Exception {

        mItem = new TodoListItem("Some task to do", TodoListStatus.Created);
    }

    @Test
    public void getDescription() throws Exception {

        // Arrange
        String expectedDesc = "Some task to do";

        // Act
        String actualDescription = mItem.getDescription();

        // Assert
        Assert.assertEquals(expectedDesc, actualDescription);
    }

    @Test
    public void isCompleted() throws Exception {

        // Arrange
        TodoListStatus expectedStatus = TodoListStatus.Created;

        // Act
        TodoListStatus actualStatus = mItem.getStatus();

        // Assert
        Assert.assertEquals(expectedStatus, actualStatus);
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