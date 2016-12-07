package todo.javier.mera.todolist.model;

/**
 * Created by javie on 12/7/2016.
 */
public abstract class Removable {

    private boolean mCanRemove;
    public void setCanRemove(boolean canRemove){

        mCanRemove = canRemove;
    }

    public boolean getCanRemove() {

        return mCanRemove;
    }
}
