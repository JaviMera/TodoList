package todo.javier.mera.todolist.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by javie on 11/29/2016.
 */
public class TodoListTask extends ItemBase implements Parcelable {

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

    protected TodoListTask(Parcel in) {
        mTodoListId = in.readLong();
        mDescription = in.readString();
        mCreationDate = in.readLong();
        mStatus = TaskStatus.values()[in.readInt()];
    }

    public static final Creator<TodoListTask> CREATOR = new Creator<TodoListTask>() {
        @Override
        public TodoListTask createFromParcel(Parcel in) {
            return new TodoListTask(in);
        }

        @Override
        public TodoListTask[] newArray(int size) {
            return new TodoListTask[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(mTodoListId);
        parcel.writeString(mDescription);
        parcel.writeLong(mCreationDate);
        parcel.writeInt(mStatus.ordinal());
    }
}
