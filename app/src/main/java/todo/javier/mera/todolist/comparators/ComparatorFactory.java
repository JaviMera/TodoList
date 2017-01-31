package todo.javier.mera.todolist.comparators;

import todo.javier.mera.todolist.R;
import todo.javier.mera.todolist.model.ItemBase;

/**
 * Created by javie on 1/26/2017.
 */

public class ComparatorFactory<T extends ItemBase> {

    public Comparator getComparator(int resourceId) {

        switch (resourceId) {

            case R.id.sortByName:

                return new NameComparator<T>();

            case R.id.sortByDueDate:

                return new DueDateComparator<T>();

            case R.id.sortByPriority:

                return new PriorityComparator<T>();
        }

        return new DefaultComparator();
    }
}
