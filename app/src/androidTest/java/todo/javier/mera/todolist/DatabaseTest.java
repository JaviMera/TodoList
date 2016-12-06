package todo.javier.mera.todolist;

import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

import todo.javier.mera.todolist.database.TodoListDataSource;
import todo.javier.mera.todolist.model.TodoList;
import todo.javier.mera.todolist.model.TodoListItem;
import todo.javier.mera.todolist.model.TaskStatus;

/**
 * Created by javie on 12/5/2016.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class DatabaseTest {

    private TodoListDataSource mDataSource;
    private SQLiteDatabase mDb;

    @Before
    public void setUp() {

        mDataSource = new TodoListDataSource(InstrumentationRegistry.getTargetContext());
    }

    @After
    public void tearDown() throws Exception {

        mDataSource.clear();
        mDataSource.close();
    }

    @Test
    public void dbShouldExist() throws Exception {

        // Act
        mDataSource.openWriteable();

        // Assert
        Assert.assertTrue(mDataSource.isOpen());
    }

    @Test
    public void dbShouldAddTodoList() throws Exception {

        // Arrange
        String expectedTitle = "My List";
        long expectedCreationDate = new Date().getTime();

        // Act
        mDataSource.openWriteable();
        long newId = mDataSource.createTodoList(expectedTitle, (int) expectedCreationDate);

        // Assert
        Assert.assertTrue(newId > -1);
    }

    @Test
    public void dbShouldReadTodoList() throws Exception {

        // Arrange
        String expectedTitle = "My List";
        long expectedCreationDate = new Date().getTime();

        // Act
        mDataSource.openReadable();
        long newId = mDataSource.createTodoList(expectedTitle, expectedCreationDate);
        TodoList todoList = new TodoList(newId, expectedTitle, expectedCreationDate);

        // Assert
        Assert.assertNotNull(todoList);
        Assert.assertEquals(expectedTitle, todoList.getTitle());
        Assert.assertEquals(expectedCreationDate, todoList.getCreationDate());
    }

    @Test
    public void dbShouldAddTodoListItem() throws Exception {

        // Arrange
        String todoListTitle = "My Other list";
        long todoListCreationDate = new Date().getTime();

        String description = "first task";
        TaskStatus status = TaskStatus.Created;
        long timeStamp = new Date().getTime();

        // Act
        mDataSource.openWriteable();

        long todoListId = mDataSource.createTodoList(todoListTitle, todoListCreationDate);
        long newId = mDataSource.createTodoListItem(todoListId, description, status, timeStamp);

        // Assert
        Assert.assertTrue(newId > -1);
    }

    @Test
    public void dbShouldReadTodoListItems() throws Exception {

        // Arrange
        String todoListTitle = "My Other list";
        long todoListCreationDate = new Date().getTime();

        String description = "first task";
        TaskStatus status = TaskStatus.Created;
        long timeStamp = new Date().getTime();
        int expectedSize = 1;

        // Act
        mDataSource.openWriteable();

        long todoListId = mDataSource.createTodoList(todoListTitle, todoListCreationDate);
        long item1Id = mDataSource.createTodoListItem(todoListId, description, status, timeStamp);

        mDataSource.close();
        mDataSource.openReadable();
        List<TodoListItem> items = mDataSource.readAllTodoListItems(todoListId);

        // Assert
        Assert.assertEquals(expectedSize, items.size());

        TodoListItem item = items.get(0);
        Assert.assertEquals(item1Id, item.getItemId());
        Assert.assertEquals(todoListId, item.getTodoListId());
        Assert.assertEquals(description, item.getDescription());
        Assert.assertEquals(timeStamp, item.getCreationDate());
        Assert.assertEquals(status, item.getStatus());
    }
}
