package todo.javier.mera.todolist.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Javier on 11/28/2016.
 */
public class TodoList implements Parcelable {

    private String mTitle;
    private List<TodoListItem> mItems;

    public TodoList(String title) {

        mTitle = title;
        mItems = new LinkedList<>();
    }

    protected TodoList(Parcel in) {
        mTitle = in.readString();
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

    public int getCompletedItems() {

        return 0;
    }

    public int getIncompleteItems() {

        return 0;
    }

    public int getId() {

        return -1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mTitle);
    }
}
