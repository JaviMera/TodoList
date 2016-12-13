package todo.javier.mera.todolist.model;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by javie on 12/12/2016.
 */

public class PriorityTest {

    private Priority mPriority;
    private String mExpectedName;
    private int mExpectedColor;

    @Before
    public void setUp() throws Exception {

        mExpectedName = "Low";
        mExpectedColor = 1234;

        mPriority = new Priority(mExpectedName, mExpectedColor);
    }

    @Test
    public void getName() throws Exception {

        // Assert
        Assert.assertEquals(mExpectedName, mPriority.getName());
    }

    @Test
    public void getColor() throws Exception {

        // Assert
        Assert.assertEquals(mExpectedColor, mPriority.getDrawable());
    }
}
