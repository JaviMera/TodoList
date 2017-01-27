package todo.javier.mera.todolist.comparators;

import java.util.List;

import todo.javier.mera.todolist.model.ItemBase;

/**
 * Created by javie on 1/26/2017.
 */

public interface Comparator<T extends ItemBase>{

    <T> int getPosition(T newItem, List<T> items);
}
