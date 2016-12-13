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
import java.util.List;
import java.util.UUID;

import todo.javier.mera.todolist.database.TodoListDataSource;
import todo.javier.mera.todolist.database.TodoListSQLiteHelper;
import todo.javier.mera.todolist.model.TaskPriority;
import todo.javier.mera.todolist.model.TodoList;
import todo.javier.mera.todolist.model.Task;
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
    }

    @Test
    public void dbShouldAddTodoList() throws Exception {

        // Arrange
        TodoList todoList = createTodoList();

        // Act
        long rowId = mDataSource.createTodoList(todoList);

        // Assert
        Assert.assertTrue(rowId > -1);
    }

    private TodoList createTodoList() {

        return new TodoList(
            UUID.randomUUID().toString(),
            "My List",
            new Date().getTime(),
            0
        );
    }

    private Task createTask(String todoListId) {

        return new Task(
            UUID.randomUUID().toString(),
            todoListId,
            0,
            "My Task",
            TaskStatus.Created,
            new Date().getTime(),
            new Date().getTime(),
            TaskPriority.None
        );
    }

    @Test
    public void dbShouldReadTodoList() throws Exception {

        // Arrange
        TodoList expectedTodoList = createTodoList();

        // Act
        mDataSource.createTodoList(expectedTodoList);

        TodoList actualTodoList = mDataSource.readTodoLists().get(0);

        // Assert
        Assert.assertNotNull(actualTodoList);
        Assert.assertEquals(expectedTodoList.getTitle(), actualTodoList.getTitle());
        Assert.assertEquals(expectedTodoList.getCreationDate(), actualTodoList.getCreationDate());
    }

    @Test
    public void dbShouldAddTask() throws Exception {

        // Arrange
        TodoList todoList = createTodoList();
        Task newTask = createTask(todoList.getId());

        // Act
        mDataSource.createTodoList(todoList);

        long rowId = mDataSource.createTodoListTask(newTask);

        // Assert
        Assert.assertTrue(rowId > -1);
    }

    @Test
    public void dbShouldReadTasks() throws Exception {

        // Arrange
        TodoList todoList = createTodoList();
        Task task = createTask(todoList.getId());
        int expectedSize = 1;

        // Act
        mDataSource.createTodoList(todoList);
        mDataSource.createTodoListTask(task);

        List<Task> items = mDataSource.readTodoListTasks(todoList.getId());

        // Assert
        Assert.assertEquals(expectedSize, items.size());

        Task item = items.get(0);
        Assert.assertEquals(task.getId(), item.getId());
        Assert.assertEquals(task.getTodoListId(), item.getTodoListId());
        Assert.assertEquals(task.getPosition(), item.getPosition());
        Assert.assertEquals(task.getDescription(), item.getDescription());
        Assert.assertEquals(task.getCreationDate(), item.getCreationDate());;
        Assert.assertEquals(task.getDueDate(), item.getDueDate());
        Assert.assertEquals(task.getStatus(), item.getStatus());
        Assert.assertEquals(task.getPriority(), item.getPriority());
    }

    @Test
    public void dbShuoldUpdateTodoList() throws Exception {

        // Arrange
        TodoList todoList = createTodoList();

        // Act
        mDataSource.createTodoList(todoList);

        int position = 3;
        todoList.setPosition(position);
        ContentValues values = new ContentValues();
        values.put(TodoListSQLiteHelper.COLUMN_TODO_LIST_POSITION, todoList.getPosition());

        int affectedRow = mDataSource.update(
            TodoListSQLiteHelper.TABLE_TODO_LISTS,
            TodoListSQLiteHelper.COLUMN_TODO_LIST_ID,
            todoList.getId(),
            values);

        TodoList list = mDataSource.readTodoLists().get(0);

        // Assert
        Assert.assertTrue(affectedRow > 0);
        Assert.assertEquals(position, list.getPosition());
    }

    @Test
    public void dbShouldUpdateTodoListTaskPosition() throws Exception {

        // Arrange
        TodoList todoList = createTodoList();
        Task task = createTask(todoList.getId());

        // Act
        long rowId = mDataSource.createTodoListTask(task);

        int newPosition = 2;

        ContentValues values = new ContentValues();
        values.put(TodoListSQLiteHelper.COLUMN_ITEMS_POSITION, newPosition);

        int rowsAffected = mDataSource.update(
            TodoListSQLiteHelper.TABLE_TODO_LIST_ITEMS,
            TodoListSQLiteHelper.COLUMN_ITEMS_ID,
            task.getId(),
            values
        );

        task = mDataSource.readTodoListTasks(todoList.getId()).get(0);

        // Assert
        Assert.assertTrue(rowsAffected > 0);
        Assert.assertEquals(newPosition, task.getPosition());
    }

    @Test
    public void dbShouldUpdateTaskStatus() throws Exception {

        // Arrange
        Task expectedTask = createTask(UUID.randomUUID().toString());

        // Act
        mDataSource.createTodoListTask(expectedTask);
        expectedTask.setStatus(TaskStatus.Completed);
        ContentValues values = new ContentValues();
        values.put(TodoListSQLiteHelper.COLUMN_ITEMS_STATUS, expectedTask.getStatus().ordinal());

        int rowsAffected = mDataSource.update(
            TodoListSQLiteHelper.TABLE_TODO_LIST_ITEMS,
            TodoListSQLiteHelper.COLUMN_ITEMS_ID,
            expectedTask.getId(),
            values
        );

        Task actualTask = mDataSource.readTodoListTasks(expectedTask.getTodoListId()).get(0);

        // Assert
        Assert.assertEquals(expectedTask.getStatus(), actualTask.getStatus());
    }

    @Test
    public void dbShouldUpdateTaskPriority() throws Exception {

        // Arrange
        Task task = createTask(UUID.randomUUID().toString());

        // Act
        mDataSource.createTodoListTask(task);
        TaskPriority newPriority = TaskPriority.High;

        Task expectedTask = mDataSource.readTodoListTasks(task.getTodoListId()).get(0);
        expectedTask.setPriority(newPriority);

        ContentValues values = new ContentValues();
        values.put(TodoListSQLiteHelper.COLUMN_ITEMS_PRIORITY, expectedTask.getPriority().ordinal());

        mDataSource.update(
            TodoListSQLiteHelper.TABLE_TODO_LIST_ITEMS,
            TodoListSQLiteHelper.COLUMN_ITEMS_ID,
            expectedTask.getId(),
            values
        );

        Task actualTask = mDataSource.readTodoListTasks(expectedTask.getTodoListId()).get(0);

        // Assert
        Assert.assertEquals(expectedTask.getPriority(), actualTask.getPriority());
    }

    @Test
    public void dbShouldRemoveTask() throws Exception {

        // Arrange
        TodoList todoList = createTodoList();
        Task task = createTask(todoList.getId());

        // Act
        mDataSource.createTodoList(todoList);
        mDataSource.createTodoListTask(task);

        int expectedRowCount = mDataSource.removeTodoListTasks(task);

        // Assert
        Assert.assertEquals(expectedRowCount, 1);
    }

    @Test
    public void dbShouldRemoveTodoListAndTasks() throws Exception {

        // Arrange
        TodoList todoList = createTodoList();
        Task task = createTask(todoList.getId());

        // Act
        mDataSource.createTodoList(todoList);
        mDataSource.createTodoListTask(task);

        int expectedRowCount = mDataSource.removeTodoLists(new TodoList[]{todoList});
        List<Task> tasks = mDataSource.readTodoListTasks(todoList.getId());

        Assert.assertTrue(tasks.isEmpty());
        Assert.assertTrue(expectedRowCount > -1);
    }
}
