package todo.javier.mera.todolist;

import android.content.ContentValues;
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
import java.util.LinkedList;
import java.util.List;

import todo.javier.mera.todolist.database.TodoListDataSource;
import todo.javier.mera.todolist.database.TodoListSQLiteHelper;
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

//        mDataSource.clear();
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
        TodoList todoList = mDataSource.createTodoList(expectedTitle, expectedCreationDate, 0);

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
        TodoList todoList = mDataSource.createTodoList(expectedTitle, expectedCreationDate, 0);

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

        TodoList todoList = mDataSource.createTodoList(todoListTitle, todoListCreationDate, 0);

        int position = 0;
        TodoListTask task = mDataSource.createTodoListTask(todoList.getId(), position, description, status, timeStamp);

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

        TodoList todoList = mDataSource.createTodoList(todoListTitle, todoListCreationDate, 0);

        int position = 2;
        TodoListTask expectedTask = mDataSource.createTodoListTask(todoList.getId(), position, description, status, timeStamp);

        mDataSource.close();
        mDataSource.openReadable();
        List<TodoListTask> items = mDataSource.readTodoListTasks(todoList.getId());

        // Assert
        Assert.assertEquals(expectedSize, items.size());

        TodoListTask item = items.get(0);
        Assert.assertEquals(expectedTask.getId(), item.getId());
        Assert.assertEquals(expectedTask.getTodoListId(), item.getTodoListId());
        Assert.assertEquals(position, item.getPosition());
        Assert.assertEquals(expectedTask.getDescription(), item.getDescription());
        Assert.assertEquals(expectedTask.getCreationDate(), item.getCreationDate());
        Assert.assertEquals(expectedTask.getStatus(), item.getStatus());
    }

    @Test
    public void dbShuoldUpdateTodoList() throws Exception {

        // Arrange
        String title = "Some list";
        long creationDate = new Date().getTime();

        // Act
        mDataSource.openWriteable();
        TodoList todoList = mDataSource.createTodoList(title, creationDate, 0);

        int position = 3;
        todoList.setPosition(position);
        ContentValues values = new ContentValues();
        values.put(TodoListSQLiteHelper.COLUMN_TODO_LIST_POSITION, todoList.getPosition());
        int affectedRow = mDataSource.updateTodoList(todoList.getId(), values);

        TodoList list = mDataSource.readTodoLists().get(0);

        // Assert
        Assert.assertTrue(affectedRow > 0);
        Assert.assertEquals(position, list.getPosition());
    }

    @Test
    public void dbShouldUpdateTodoListTask() throws Exception {

        // Arrange
        long todoListId = 2;
        int position = 0;
        String description = "harambe";
        TaskStatus status = TaskStatus.Created;
        long timeStamp = new Date().getTime();

        // Act
        mDataSource.openWriteable();
        TodoListTask expectedTask = mDataSource.createTodoListTask(todoListId,position, description, status, timeStamp);

        int newPosition = 2;

        ContentValues values = new ContentValues();
        values.put(TodoListSQLiteHelper.COLUMN_ITEMS_POSITION, newPosition);
        int rowsAffected = mDataSource.updateTask(expectedTask.getId(), values);

        TodoListTask task = mDataSource.readTodoListTasks(todoListId).get(0);

        // Assert
        Assert.assertTrue(rowsAffected > 0);
        Assert.assertEquals(newPosition, task.getPosition());
    }

    @Test
    public void dbShouldRemoveTask() throws Exception {

        // Arrange
        String todoListTitle = "My Other list";
        long todoListCreationDate = new Date().getTime();

        String description = "first task";
        TaskStatus status = TaskStatus.Created;
        long timeStamp = new Date().getTime();

        // Act
        mDataSource.openWriteable();

        TodoList todoList = mDataSource.createTodoList(todoListTitle, todoListCreationDate, 0);

        int position = 0;
        TodoListTask expectedTask = mDataSource.createTodoListTask(todoList.getId(), position, description, status, timeStamp);

        int expectedRowCount = mDataSource.removeTodoListTasks(expectedTask);

        // Assert
        Assert.assertEquals(expectedRowCount, 1);
    }

    @Test
    public void dbShouldRemoveTodoListAndTasks() throws Exception {

        // Arrange
        String todoListTitle = "My List";
        long todoListCreationDate = new Date().getTime();

        String description = "first task";
        TaskStatus status = TaskStatus.Created;
        long timeStamp = new Date().getTime();

        // Act
        mDataSource.openWriteable();
        TodoList todoList = mDataSource.createTodoList(todoListTitle, todoListCreationDate, 0);

        int position = 0;
        TodoListTask expectedTask = mDataSource.createTodoListTask(todoList.getId(), position, description, status, timeStamp);

        int expectedRowCount = mDataSource.removeTodoLists(new TodoList[]{todoList});
        mDataSource.close();
        mDataSource.openReadable();
        List<TodoListTask> tasks = mDataSource.readTodoListTasks(todoList.getId());

        Assert.assertTrue(tasks.isEmpty());
        Assert.assertTrue(expectedRowCount > -1);
    }
}
