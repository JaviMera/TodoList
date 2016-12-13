package todo.javier.mera.todolist.model;

/**
 * Created by javie on 12/12/2016.
 */

public class Priority {

    private String mName;
    private int mDrawable;

    public Priority(String name, int drawable) {

        mName = name;
        mDrawable = drawable;
    }

    public String getName() {

        return mName;
    }

    public int getDrawable() {

        return mDrawable;
    }
}
