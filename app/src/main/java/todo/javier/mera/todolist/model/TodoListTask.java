package todo.javier.mera.todolist.model;

/**
 * Created by javie on 11/29/2016.
 */
public class TodoListTask extends ItemBase {

    private long mTodoListId;
    private String mDescription;
    private TaskStatus mStatus;
    private long mCreationDate;

    public TodoListTask(long itemId, long todoListId, String description, TaskStatus status,
        long creationDate, boolean canRemove) {

        mId = itemId;
        mTodoListId = todoListId;
        mDescription = description;
        mStatus = status;
        mCreationDate = creationDate;
        setCanRemove(canRemove);
    }

    public String getDescription() {

        return mDescription;
    }

    public TaskStatus getStatus() {

        return mStatus;
    }

    public void update(TaskStatus newStatus) {

        mStatus = newStatus;
    }

    public long getTodoListId() {
        return mTodoListId;
    }

    public long getCreationDate() {

        return mCreationDate;
    }
}
