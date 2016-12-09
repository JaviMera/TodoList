package todo.javier.mera.todolist.model;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by javie on 11/29/2016.
 */
public class TodoListTest {

    private long expectedId = 1234;
    private String expectedTitle = "Some List";
    private long expectedDate = new Date().getTime();
    private int expectedItemcount = 0;
    private TodoList mTodoList;
    private int expectedPosition = 0;
    private long mExpectedDueDate = new Date().getTime();

    @Before
    public void setUp() throws Exception {

        mTodoList = new TodoList(
            expectedId,
            "Some List",
            expectedDate,
            mExpectedDueDate,
            expectedPosition
        );
    }

    @Test
    public void getTitle() throws Exception {

        // Assert
        Assert.assertEquals(expectedTitle, mTodoList.getTitle());
    }

    @Test
    public void getId() throws Exception {

        // Assert
        Assert.assertEquals(expectedId, mTodoList.getId());
    }

    @Test
    public void getCreationdate() throws Exception {

        // Assert
        Assert.assertEquals(expectedDate, mTodoList.getCreationDate());
    }

    @Test
    public void getDueDate() throws Exception {

        // Assert
        Assert.assertEquals(mExpectedDueDate, mTodoList.getDueDate());
    }

    @Test
    public void getItemsCount() throws Exception {

        // Assert
        Assert.assertEquals(expectedItemcount, mTodoList.getItemsCount());
    }

    @Test
    public void getPosition() throws Exception {

        // Assert
        Assert.assertEquals(expectedPosition, mTodoList.getPosition());
    }

    @Test
    public void setPosition() throws Exception {

        // Arrange
        int newPosition = 2;

        // Act
        mTodoList.setPosition(newPosition);

        // Assert
        Assert.assertEquals(newPosition, mTodoList.getPosition());
    }
}