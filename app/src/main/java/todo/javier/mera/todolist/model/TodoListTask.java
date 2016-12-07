package todo.javier.mera.todolist.model;

/**
 * Created by javie on 11/29/2016.
 */
public class TodoListTask {

    private long mTodoListId;
    private long mItemId;
    private String mDescription;
    private TaskStatus mStatus;
    private long mCreationDate;

    public TodoListTask(long itemId, long todoListId, String description, TaskStatus status,
                        long creationDate) {

        mItemId = itemId;
        mTodoListId = todoListId;
        mDescription = description;
        mStatus = status;
        mCreationDate = creationDate;
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

    public long getItemId() {
        return mItemId;
    }

    public long getCreationDate() {

        return mCreationDate;
    }
}
