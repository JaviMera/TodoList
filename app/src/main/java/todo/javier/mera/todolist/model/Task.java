package todo.javier.mera.todolist.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by javie on 11/29/2016.
 */
public class Task extends ItemBase implements Parcelable {

    private String mTodoListId;
    private String mDescription;
    private TaskStatus mStatus;
    private long mCreationDate;
    private long mDueDate;
    private long mDueTime;
    private Priority mPriority;
    private Reminder mReminder;

    public Task(
        String itemId,
        String todoListId,
        String description,
        TaskStatus status,
        long creationDate,
        long dueDate,
        long dueTime,
        Priority priority,
        Reminder reminder) {

        super(itemId, false);

        mTodoListId = todoListId;
        mDescription = description;
        mStatus = status;
        mCreationDate = creationDate;
        mDueDate = dueDate;
        mDueTime = dueTime;
        mPriority = priority;
        mReminder = reminder;
    }

    private Task(Parcel in) {

        super(in);

        mTodoListId = in.readString();
        mDescription = in.readString();
        mCreationDate = in.readLong();
        mStatus = TaskStatus.values()[in.readInt()];
        mPriority = Priority.values()[in.readInt()];
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

    public void setStatus(TaskStatus newStatus) {

        mStatus = newStatus;
    }

    public String getTodoListId() {
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
        parcel.writeString(mTodoListId);
        parcel.writeString(mDescription);
        parcel.writeLong(mCreationDate);
        parcel.writeInt(mStatus.ordinal());
        parcel.writeInt(mPriority.ordinal());
    }

    public long getDueDate() {

        return mDueDate;
    }

    public Priority getPriority() {

        return mPriority;
    }

    public long getmDueTime() {

        return mDueTime;
    }

    public void setPriority(Priority newPriority) {

        mPriority = newPriority;
    }

    public Reminder getReminder() {

        return mReminder;
    }

    public void setReminder(Reminder reminder) {

        mReminder = reminder;
    }
}
