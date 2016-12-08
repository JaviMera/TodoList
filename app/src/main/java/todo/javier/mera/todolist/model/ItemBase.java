package todo.javier.mera.todolist.model;

/**
 * Created by javie on 12/7/2016.
 */
public abstract class ItemBase {

    protected long mId;
    private boolean mCanRemove;
    public void setCanRemove(boolean canRemove){

        mCanRemove = canRemove;
    }

    public boolean getCanRemove() {

        return mCanRemove;
    }

    @Override
    public boolean equals(Object obj) {

        ItemBase otherItem = (ItemBase) obj;

        return mId == otherItem.getId();
    }

    public long getId() {

        return mId;
    }
}
