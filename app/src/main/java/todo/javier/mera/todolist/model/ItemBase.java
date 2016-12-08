package todo.javier.mera.todolist.model;

/**
 * Created by javie on 12/7/2016.
 */
public abstract class ItemBase {

    protected long mId;
    private boolean mCanRemove;
    private boolean mIsMoving;

    public void setCanRemove(boolean canRemove){

        mCanRemove = canRemove;
    }

    public boolean getCanRemove() {

        return mCanRemove;
    }

    public long getId() {

        return mId;
    }

    public void setMoving(boolean isMoving) {

        mIsMoving = isMoving;
    }

    public boolean isMoving() {

        return mIsMoving;
    }
}
