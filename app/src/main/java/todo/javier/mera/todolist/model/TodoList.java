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
    private List<TodoListTask> mItems;

    public TodoList(long id, String title, long creationDate, long dueDate, int position) {

        mId = id;
        mPosition = position;
        mTitle = title;
        mCreationDate = creationDate;
        mDueDate = dueDate;
        mItems = new LinkedList<>();
    }

    protected TodoList(Parcel in) {
        mTitle = in.readString();
        mCreationDate = in.readLong();
        mItems = in.createTypedArrayList(TodoListTask.CREATOR);
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

    public List<TodoListTask> getTasks() {

        return mItems;
    }

    public void setItems(List<TodoListTask> items) {

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
        parcel.writeTypedList(mItems);
    }

    public long getDueDate() {

        return mDueDate;
    }
}
