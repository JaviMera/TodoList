package todo.javier.mera.todolist.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Javier on 11/28/2016.
 */
public class TodoList extends ItemBase implements Parcelable {

    private String mTitle;
    private long mCreationDate;
    private long mDueDate;
    private List<Task> mItems;

    public TodoList(long id, String title, long creationDate, long dueDate, int position) {

        super(id, position, false);

        mTitle = title;
        mCreationDate = creationDate;
        mDueDate = dueDate;
        mItems = new LinkedList<>();
    }

    protected TodoList(Parcel in) {

        super(in);

        mTitle = in.readString();
        mCreationDate = in.readLong();
        mDueDate = in.readLong();
        mItems = in.createTypedArrayList(Task.CREATOR);
    }

    public static final Creator<TodoList> CREATOR = new Creator<TodoList>() {
        @Override
        public TodoList createFromParcel(Parcel in) {
            return new TodoList(in);
        }

        @Override
        public TodoList[] newArray(int size) {
            return new TodoList[size];
        }
    };

    public String getTitle() {
        return mTitle;
    }

    public int getItemsCount() {

        return mItems.size();
    }

    public long getCreationDate() {

        return mCreationDate;
    }

    public List<Task> getTasks() {

        return mItems;
    }

    public void setItems(List<Task> items) {

        mItems = new LinkedList<>(items);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mTitle);
        parcel.writeLong(mCreationDate);
        parcel.writeLong(mDueDate);
        parcel.writeTypedList(mItems);
    }

    public long getDueDate() {

        return mDueDate;
    }
}
