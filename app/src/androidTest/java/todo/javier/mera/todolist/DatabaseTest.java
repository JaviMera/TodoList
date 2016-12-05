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

import todo.javier.mera.todolist.database.TodoListDataSource;
import todo.javier.mera.todolist.database.TodoListSQLiteHelper;
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
        mDataSource.openWriteable();

        // Act
        long newId = mDataSource.create(expectedTitle, expectedCreationDate);

        // Assert
        Assert.assertTrue(newId > -1);
    }

    @Test
    public void dbShouldReadTodoList() throws Exception {

        // Arrange
        String expectedTitle = "My List";
        long expectedCreationDate = new Date().getTime();
        mDataSource.openWriteable();

        // Act
        long newId = mDataSource.create(expectedTitle, expectedCreationDate);
        TodoList todoList = new TodoList(newId, expectedTitle, expectedCreationDate);

        // Assert
        Assert.assertNotNull(todoList);
        Assert.assertEquals(expectedTitle, todoList.getTitle());
        Assert.assertEquals(expectedCreationDate, todoList.getCreationDate());
    }
}
