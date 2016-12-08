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
import todo.javier.mera.todolist.model.TodoListTask;
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
        TodoList todoList = mDataSource.createTodoList(expectedTitle, expectedCreationDate);

        // Assert
        Assert.assertTrue(todoList.getId() > -1);
    }

    @Test
    public void dbShouldReadTodoList() throws Exception {

        // Arrange
        String expectedTitle = "My List";
        long expectedCreationDate = new Date().getTime();

        // Act
        mDataSource.openReadable();
        TodoList todoList = mDataSource.createTodoList(expectedTitle, expectedCreationDate);

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

        TodoList todoList = mDataSource.createTodoList(todoListTitle, todoListCreationDate);
        TodoListTask task = mDataSource.createTodoListTask(todoList.getId(), description, status, timeStamp);

        // Assert
        Assert.assertTrue(task.getId() > -1);
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

        TodoList todoList = mDataSource.createTodoList(todoListTitle, todoListCreationDate);
        TodoListTask expectedTask = mDataSource.createTodoListTask(todoList.getId(), description, status, timeStamp);

        mDataSource.close();
        mDataSource.openReadable();
        List<TodoListTask> items = mDataSource.readTodoListTasks(todoList.getId());

        // Assert
        Assert.assertEquals(expectedSize, items.size());

        TodoListTask item = items.get(0);
        Assert.assertEquals(expectedTask.getId(), item.getId());
        Assert.assertEquals(expectedTask.getTodoListId(), item.getTodoListId());
        Assert.assertEquals(expectedTask.getDescription(), item.getDescription());
        Assert.assertEquals(expectedTask.getCreationDate(), item.getCreationDate());
        Assert.assertEquals(expectedTask.getStatus(), item.getStatus());
    }

    @Test
    public void dbShouldRemoveTask() throws Exception {

        // Arrange
        String todoListTitle = "My Other list";
        long todoListCreationDate = new Date().getTime();

        String description = "first task";
        TaskStatus status = TaskStatus.Created;
        long timeStamp = new Date().getTime();
        int expectedSize = 1;

        // Act
        mDataSource.openWriteable();

        TodoList todoList = mDataSource.createTodoList(todoListTitle, todoListCreationDate);
        TodoListTask expectedTask = mDataSource.createTodoListTask(todoList.getId(), description, status, timeStamp);

        mDataSource.close();

        mDataSource.openWriteable();

        int expectedRowCount = mDataSource.removeTodoListTask(expectedTask);

        // Assert
        Assert.assertEquals(expectedRowCount, 1);
    }
}
