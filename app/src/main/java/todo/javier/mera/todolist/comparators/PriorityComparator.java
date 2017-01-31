package todo.javier.mera.todolist.comparators;

import android.content.ClipData;

import java.util.List;

import todo.javier.mera.todolist.model.ItemBase;
import todo.javier.mera.todolist.model.Task;

/**
 * Created by javie on 1/26/2017.
 */

public class PriorityComparator<T extends ItemBase> implements Comparator<T> {

    @Override
    public int getPosition(T newItem, List<T> items) {

        for (T item : items) {

            if (newItem.getPriority().ordinal() >= item.getPriority().ordinal()) {

                return items.indexOf(item);
            }
        }

        return -1;
    }
}
