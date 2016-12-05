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
    private int expectedDate = (int)new Date().getTime();
    private int expectedItemcount = 0;
    private TodoList mTodoList;

    @Before
    public void setUp() throws Exception {

        mTodoList = new TodoList(expectedId, "Some List", expectedDate);
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
    public void getItemsCount() throws Exception {

        // Assert
        Assert.assertEquals(expectedItemcount, mTodoList.getItemsCount());
    }
}