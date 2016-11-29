package todo.javier.mera.todolist.model;

/**
 * Created by javie on 11/29/2016.
 */
public class TodoListItem {

    private String mDescription;
    private TodoListStatus mStatus;

    public TodoListItem(String description, TodoListStatus completed) {

        mDescription = description;
        mStatus = completed;
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
