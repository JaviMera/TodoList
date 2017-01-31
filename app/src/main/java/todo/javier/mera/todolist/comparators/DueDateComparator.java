package todo.javier.mera.todolist.comparators;

import java.util.List;

import todo.javier.mera.todolist.model.ItemBase;
import todo.javier.mera.todolist.model.Task;

/**
 * Created by javie on 1/26/2017.
 */

public class DueDateComparator<T extends ItemBase> implements Comparator<T> {

    @Override
    public int getPosition(T newItem, List<T> items) {

        for (T item : items) {

            if (newItem.getDueDate() < item.getDueDate()) {

                return items.indexOf(item);
            }
        }

        return -1;
    }
}
