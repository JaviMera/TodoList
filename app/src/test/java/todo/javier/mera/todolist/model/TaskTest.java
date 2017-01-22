package todo.javier.mera.todolist.model;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.UUID;

/**
 * Created by javie on 11/29/2016.
 */
public class TaskTest {

    private String mExpectedTodolistId = UUID.randomUUID().toString();
    private String mExpectedItemId = UUID.randomUUID().toString();
    private String mExpectedDescription = "Some task to do";
    private TaskStatus mExpectedStatus = TaskStatus.Created;
    private long mExpectedCreationDate = new Date().getTime();
    private Task mTask;
    private long mExpectedDueDate = new Date().getTime();
    private TaskPriority mExpectedPriority = TaskPriority.None;

    @Before
    public void setUp() throws Exception {

        mTask = new Task(
            mExpectedItemId,
            mExpectedTodolistId,
            mExpectedDescription,
            mExpectedStatus,
            mExpectedCreationDate,
            mExpectedDueDate,
            mExpectedPriority
        );
    }

    @Test
    public void getTodoListId() throws Exception {

        // Assert
        Assert.assertEquals(mExpectedTodolistId, mTask.getTodoListId());
    }

    @Test
    public void getItemId() throws Exception {

        // Assert
        Assert.assertEquals(mExpectedItemId, mTask.getId());
    }

    @Test
    public void getDescription() throws Exception {

        // Assert
        Assert.assertEquals(mExpectedDescription, mTask.getDescription());
    }

    @Test
    public void getStatus() throws Exception {

        // Assert
        Assert.assertEquals(mExpectedStatus, mTask.getStatus());
    }

    @Test
    public void getCreationDate() throws Exception {

        // Assert
        Assert.assertEquals(mExpectedCreationDate, mTask.getCreationDate());
    }

    @Test
    public void getDueDate() throws Exception {

        // Assert
        Assert.assertEquals(mExpectedDueDate, mTask.getDueDate());
    }

    @Test
    public void setStatus() throws Exception {

        // Arrange
        TaskStatus expectedStatus = TaskStatus.Completed;

        // Act
        mTask.setStatus(expectedStatus);
        TaskStatus actualStatus = mTask.getStatus();

        // Assert
        Assert.assertEquals(expectedStatus, actualStatus);
    }

    @Test
    public void getCanRemove() throws Exception {

        // Assert
        Assert.assertFalse(mTask.getCanRemove());
    }

    @Test
    public void setCanRemove() throws Exception {

        // Arrange
        boolean expectedCanRemove = true;

        // Act
        mTask.setCanRemove(expectedCanRemove);
        boolean actualCanRemove = mTask.getCanRemove();

        // Assert
        Assert.assertTrue(actualCanRemove);
    }

    @Test
    public void getPriority() throws Exception {

        // Assert
        Assert.assertEquals(mExpectedPriority, mTask.getPriority());
    }

    @Test
    public void setPriority() throws Exception {

        // Arrange
        TaskPriority newPriority = TaskPriority.High;

        // Act
        mTask.setPriority(newPriority);

        // Assert
        Assert.assertEquals(newPriority, mTask.getPriority());
    }
}