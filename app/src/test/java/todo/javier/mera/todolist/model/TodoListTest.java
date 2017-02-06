package todo.javier.mera.todolist.model;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.UUID;

/**
 * Created by javie on 11/29/2016.
 */
public class TodoListTest {

    private String expectedId = UUID.randomUUID().toString();
    private String expectedTitle = "Some List";
    private long expectedDate = new Date().getTime();
    private int expectedItemcount = 0;
    private TodoList mTodoList;

    @Before
    public void setUp() throws Exception {

        mTodoList = new TodoList(
            expectedId,
            "Some List",
            expectedDate,
                Priority.None);
    }

    @Test
    public void getTitle() throws Exception {

        // Assert
        Assert.assertEquals(expectedTitle, mTodoList.getDescription());
    }

    @Test
    public void getId() throws Exception {

        // Assert
        Assert.assertEquals(expectedId, mTodoList.getId());
    }

    @Test
    public void getItemsCount() throws Exception {

        // Assert
        Assert.assertEquals(expectedItemcount, mTodoList.getTaskNumber());
    }

    @Test
    public void hasDueDateReturnsTrueWithValidDate() throws Exception {

        // Assert
        Assert.assertTrue(mTodoList.hasDueDate());
    }

    @Test
    public void hasDueDateReturnsFalseWithZeroDate() throws Exception {
        // Act
        mTodoList.setDueDate(0L);

        // Assert
        Assert.assertFalse(mTodoList.hasDueDate());
    }
}