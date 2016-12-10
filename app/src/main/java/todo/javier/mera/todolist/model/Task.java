package todo.javier.mera.todolist.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by javie on 11/29/2016.
 */
public class Task extends ItemBase implements Parcelable {

    private long mTodoListId;
    private String mDescription;
    private TaskStatus mStatus;
    private long mCreationDate;

    public Task(long itemId, long todoListId, int position, String description, TaskStatus status,
                long creationDate) {

        super(itemId, position, false);

        mTodoListId = todoListId;
        mDescription = description;
        mStatus = status;
        mCreationDate = creationDate;
    }

    private Task(Parcel in) {

        super(in);

        mTodoListId = in.readLong();
        mDescription = in.readString();
        mCreationDate = in.readLong();
        mStatus = TaskStatus.values()[in.readInt()];
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
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
