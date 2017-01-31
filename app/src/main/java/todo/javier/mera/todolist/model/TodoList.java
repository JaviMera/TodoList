package todo.javier.mera.todolist.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Javier on 11/28/2016.
 */
public class TodoList extends ItemBase implements Parcelable {

    private List<Task> mItems;

    public TodoList(String id, String description, long dueDate, Priority priority) {

        super(id, description,  false, dueDate, priority);

        mItems = new LinkedList<>();
    }

    protected TodoList(Parcel in) {

        super(in);

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

    public int getItemsCount() {

        return mItems.size();
    }

    public List<Task> getTasks() {

        return mItems;
    }

    public void setItems(List<Task> items) {

        mItems.clear();
        mItems = new LinkedList<>(items);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(mItems);
    }
}
