package todo.javier.mera.todolist.comparators;

import java.util.List;

import todo.javier.mera.todolist.model.Task;

/**
 * Created by javie on 1/26/2017.
 */

public class DefaultComparator implements Comparator<Task> {
    @Override
    public int getPosition(Task newItem, List<Task> items) {

        return items.size();
    }
}
