package todo.javier.mera.todolist.model;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by javie on 11/29/2016.
 */
public class TodoListTest {

    private TodoList mTodoList;
    private String expectedTitle = "Some List";
    private int expectedItemcount = 0;

    @Before
    public void setUp() throws Exception {

        mTodoList = new TodoList("Some List");
    }

    @Test
    public void getTitle() throws Exception {

        // Assert
        Assert.assertEquals(expectedTitle, mTodoList.getTitle());
    }

    @Test
    public void getItemsCount() throws Exception {

        // Assert
        Assert.assertEquals(expectedItemcount, mTodoList.getItemsCount());
    }
}