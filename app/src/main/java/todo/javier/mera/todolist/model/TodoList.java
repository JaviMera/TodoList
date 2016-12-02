package todo.javier.mera.todolist.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Javier on 11/28/2016.
 */
public class TodoList {

    private String mTitle;
    private List<TodoListItem> mItems;

    public TodoList(String title) {

        mTitle = title;
        mItems = new LinkedList<>();
    }

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
}
