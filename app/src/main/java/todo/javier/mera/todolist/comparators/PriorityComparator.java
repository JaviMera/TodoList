package todo.javier.mera.todolist.comparators;

import java.util.List;

import todo.javier.mera.todolist.model.Task;

/**
 * Created by javie on 1/26/2017.
 */

public class PriorityComparator implements Comparator<Task> {

    @Override
    public int getPosition(Task newItem, List<Task> items) {

        for (Task item : items) {

            if (newItem.getPriority().ordinal() >= item.getPriority().ordinal()) {

                return items.indexOf(item);
            }
        }

        return -1;
    }
}
