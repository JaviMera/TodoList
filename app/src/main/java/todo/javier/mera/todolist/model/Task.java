package todo.javier.mera.todolist.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by javie on 11/29/2016.
 */
public class Task extends ItemBase implements Parcelable {

    private String mTodoListId;
    private TaskStatus mStatus;
    private long mReminderDate;

    public Task(
        String itemId,
        String todoListId,
        String description,
        TaskStatus status,
        long dueDate,
        Priority priority,
        long reminderDate) {

        super(itemId, description, false, dueDate, priority);

        mTodoListId = todoListId;
        mStatus = status;
        mReminderDate = reminderDate;
    }

    private Task(Parcel in) {

        super(in);

        mTodoListId = in.readString();
        mStatus = TaskStatus.values()[in.readInt()];
        mReminderDate = in.readLong();
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

    public TaskStatus getStatus() {

        return mStatus;
    }

    public void setStatus(TaskStatus newStatus) {

        mStatus = newStatus;
    }

    public String getTodoListId() {
        return mTodoListId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mTodoListId);
        parcel.writeInt(mStatus.ordinal());
        parcel.writeLong(mReminderDate);
    }

    public long getReminderDate() {

        return mReminderDate;
    }

    public void setReminder(long reminderDate) {

        mReminderDate = reminderDate;
    }
}
