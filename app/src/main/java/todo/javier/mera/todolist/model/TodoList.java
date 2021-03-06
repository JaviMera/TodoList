package todo.javier.mera.todolist.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Javier on 11/28/2016.
 */
public class TodoList extends ItemBase implements Parcelable {

    private int mTaskNumber;

    public TodoList(String id, String description, long dueDate, Priority priority) {

        super(id, description,  false, dueDate, priority);
    }

    public void setTaskNumber(int number) {

        mTaskNumber = number;
    }

    public int getTaskNumber() {

        return mTaskNumber;
    }

    protected TodoList(Parcel in) {

        super(in);
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        super.writeToParcel(parcel, i);
    }
}
