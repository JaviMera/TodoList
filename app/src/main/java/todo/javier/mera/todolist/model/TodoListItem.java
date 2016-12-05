package todo.javier.mera.todolist.model;

/**
 * Created by javie on 11/29/2016.
 */
public class TodoListItem {

    private long mTodoListId;
    private long mItemId;
    private String mDescription;
    private TodoListStatus mStatus;
    private long mCreationDate;

    public TodoListItem(long itemId, long todoListId, String description, TodoListStatus status,
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

    public TodoListStatus getStatus() {

        return mStatus;
    }

    public void update(TodoListStatus newStatus) {

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
