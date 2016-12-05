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

    public TodoListItem(long todoListId, long itemId, String description, TodoListStatus status,
        long creationDate) {

        mTodoListId = todoListId;
        mItemId = itemId;
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
}
