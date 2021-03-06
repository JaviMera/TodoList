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
import todo.javier.mera.todolist.model.Reminder;
import todo.javier.mera.todolist.model.Task;
import todo.javier.mera.todolist.model.Priority;
import todo.javier.mera.todolist.model.TaskStatus;
import todo.javier.mera.todolist.model.TodoList;

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
        TodoList todoList = createTodoList(Priority.Low);

        // Act
        long rowId = mDataSource.createTodoList(todoList, 0);

        // Assert
        Assert.assertTrue(rowId > -1);
    }

    private TodoList createTodoList(Priority priority) {

        return new TodoList(
            UUID.randomUUID().toString(),
            "My List",
            new Date().getTime(),
            priority
        );
    }

    private Task createTask(String todoListId) {

        return new Task(
            UUID.randomUUID().toString(),
            todoListId,
            "My Task",
            TaskStatus.Created,
            new Date().getTime(),
            Priority.None,
            new Date().getTime()
        );
    }

    @Test
    public void dbShouldReadTodoList() throws Exception {

        // Arrange
        TodoList expectedTodoList = createTodoList(Priority.Low);

        // Act
        mDataSource.createTodoList(expectedTodoList, 0);

        TodoList actualTodoList = mDataSource.readTodoLists().get(0);

        // Assert
        Assert.assertNotNull(actualTodoList);
        Assert.assertEquals(expectedTodoList.getDescription(), actualTodoList.getDescription());
        Assert.assertEquals(expectedTodoList.getDueDate(), actualTodoList.getDueDate());
        Assert.assertEquals(expectedTodoList.getPriority(), actualTodoList.getPriority());
    }

    @Test
    public void dbShouldReadTodoListWithSortOptions() throws Exception {

        // Arrange
        TodoList todoList1 = createTodoList(Priority.Low);
        TodoList todoList2 = createTodoList(Priority.High);

        // Act
        mDataSource.createTodoList(todoList1,0);
        mDataSource.createTodoList(todoList2, 1);

        TodoList actualTodoList = mDataSource
            .readTodoLists(TodoListSQLiteHelper.COLUMN_TODO_LIST_PRIORITY, "DESC")
            .get(0);

        // Assert
        Assert.assertNotNull(actualTodoList);
        Assert.assertEquals(todoList2.getPriority(), actualTodoList.getPriority());
    }

    @Test
    public void dbShouldAddTask() throws Exception {

        // Arrange
        TodoList todoList = createTodoList(Priority.Low);
        Task newTask = createTask(todoList.getId());

        // Act
        mDataSource.createTodoList(todoList, 0);

        long rowId = mDataSource.createTask(newTask, 0);

        // Assert
        Assert.assertTrue(rowId > -1);
    }

    @Test
    public void dbShouldReadTasks() throws Exception {

        // Arrange
        TodoList todoList = createTodoList(Priority.Low);
        Task task = createTask(todoList.getId());
        int expectedSize = 1;

        // Act
        mDataSource.createTodoList(todoList, 0);
        mDataSource.createTask(task, 0);

        List<Task> items = mDataSource.readTask(todoList.getId());

        // Assert
        Assert.assertEquals(expectedSize, items.size());

        Task item = items.get(0);
        Assert.assertEquals(task.getId(), item.getId());
        Assert.assertEquals(task.getTodoListId(), item.getTodoListId());
        Assert.assertEquals(task.getDescription(), item.getDescription());
        Assert.assertEquals(task.getDueDate(), item.getDueDate());
        Assert.assertEquals(task.getDueDate(), item.getDueDate());
        Assert.assertEquals(task.getStatus(), item.getStatus());
        Assert.assertEquals(task.getPriority(), item.getPriority());
        Assert.assertEquals(task.getReminderDate(), item.getReminderDate());
    }

    @Test
    public void dbShouldUpdateTodoListTaskPosition() throws Exception {

        // Arrange
        TodoList todoList = createTodoList(Priority.Low);
        Task task = createTask(todoList.getId());

        // Act
        long rowId = mDataSource.createTask(task, 0);

        int newPosition = 2;

        ContentValues values = new ContentValues();
        values.put(TodoListSQLiteHelper.COLUMN_ITEMS_POSITION, newPosition);

        int rowsAffected = mDataSource.update(
            TodoListSQLiteHelper.TABLE_TASKS,
            TodoListSQLiteHelper.COLUMN_ITEMS_ID,
            task.getId(),
            values
        );

        // Assert
        Assert.assertTrue(rowsAffected > 0);
    }

    @Test
    public void dbShouldUpdateTaskStatus() throws Exception {

        // Arrange
        Task expectedTask = createTask(UUID.randomUUID().toString());

        // Act
        mDataSource.createTask(expectedTask, 0);
        expectedTask.setStatus(TaskStatus.Completed);
        ContentValues values = new ContentValues();
        values.put(TodoListSQLiteHelper.COLUMN_ITEMS_STATUS, expectedTask.getStatus().ordinal());

        int rowsAffected = mDataSource.update(
            TodoListSQLiteHelper.TABLE_TASKS,
            TodoListSQLiteHelper.COLUMN_ITEMS_ID,
            expectedTask.getId(),
            values
        );

        Task actualTask = mDataSource.readTask(expectedTask.getTodoListId()).get(0);

        // Assert
        Assert.assertEquals(expectedTask.getStatus(), actualTask.getStatus());
    }

    @Test
    public void dbShouldUpdateTaskPriority() throws Exception {

        // Arrange
        Task task = createTask(UUID.randomUUID().toString());

        // Act
        mDataSource.createTask(task, 0);
        Priority newPriority = Priority.High;

        Task expectedTask = mDataSource.readTask(task.getTodoListId()).get(0);
        expectedTask.setPriority(newPriority);

        ContentValues values = new ContentValues();
        values.put(TodoListSQLiteHelper.COLUMN_ITEMS_PRIORITY, expectedTask.getPriority().ordinal());

        mDataSource.update(
            TodoListSQLiteHelper.TABLE_TASKS,
            TodoListSQLiteHelper.COLUMN_ITEMS_ID,
            expectedTask.getId(),
            values
        );

        Task actualTask = mDataSource.readTask(expectedTask.getTodoListId()).get(0);

        // Assert
        Assert.assertEquals(expectedTask.getPriority(), actualTask.getPriority());
    }

    @Test
    public void dbShouldUpdateTaskReminder() throws Exception {

        // Arrange
        Task task = createTask(UUID.randomUUID().toString());

        // Act
        mDataSource.createTask(task, 0);
        Reminder reminder = Reminder.OFF;

        Task expectedTask = mDataSource.readTask(task.getTodoListId()).get(0);
        ContentValues values = new ContentValues();
        values.put(TodoListSQLiteHelper.COLUMN_ITEMS_REMINDER, expectedTask.getReminderDate());

        mDataSource.update(
            TodoListSQLiteHelper.TABLE_TASKS,
            TodoListSQLiteHelper.COLUMN_ITEMS_ID,
            expectedTask.getId(),
            values
        );

        Task actualTask = mDataSource.readTask(expectedTask.getTodoListId()).get(0);

        // Assert
        Assert.assertEquals(expectedTask.getReminderDate(), actualTask.getReminderDate());
    }

    @Test
    public void dbShouldRemoveTask() throws Exception {

        // Arrange
        TodoList todoList = createTodoList(Priority.Low);
        Task task = createTask(todoList.getId());

        // Act
        mDataSource.createTodoList(todoList, 0);
        mDataSource.createTask(task, 0);

        int expectedRowCount = mDataSource.removeTodoListTasks(task);

        // Assert
        Assert.assertEquals(expectedRowCount, 1);
    }

    @Test
    public void dbShouldRemoveTodoListAndTasks() throws Exception {

        // Arrange
        TodoList todoList = createTodoList(Priority.Low);
        Task task = createTask(todoList.getId());

        // Act
        mDataSource.createTodoList(todoList, 0);
        mDataSource.createTask(task, 0);

        int expectedRowCount = mDataSource.removeTodoLists(new TodoList[]{todoList});
        List<Task> tasks = mDataSource.readTask(todoList.getId());

        Assert.assertTrue(tasks.isEmpty());
        Assert.assertTrue(expectedRowCount > -1);
    }

    @Test
    public void dbShouldReadMaxTodoListPosition() throws Exception {

        // Arrange
        TodoList list1 = createTodoList(Priority.None);
        TodoList list2 = createTodoList(Priority.None);

        // act
        mDataSource.createTodoList(list1, 0);
        mDataSource.createTodoList(list2, 1);

        // Arrange
        int position = mDataSource.getLastTodoList();
        Assert.assertEquals(1, position);
    }
}
