package todo.javier.mera.todolist.comparators;

import todo.javier.mera.todolist.R;

/**
 * Created by javie on 1/26/2017.
 */

public class ComparatorFactory {

    public Comparator getComparator(int resourceId) {

        switch (resourceId) {

            case R.id.sortByName:

                return new NameComparator();

            case R.id.sortByDueDate:

                return new DueDateComparator();

            case R.id.sortByPriority:

                return new PriorityComparator();
        }

        return new DefaultComparator();
    }
}
