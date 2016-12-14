package todo.javier.mera.todolist.model;

import java.util.LinkedHashMap;
import java.util.Map;

import todo.javier.mera.todolist.R;

/**
 * Created by javie on 12/13/2016.
 */

public class PriorityColors {

    private static Map<Integer, Integer> map = new LinkedHashMap<Integer, Integer>(){
        {
            put(0, R.color.priority_none);
            put(1, R.color.priority_low);
            put(2, R.color.priority_medium);
            put(3, R.color.priority_high);
        }
    };

    public static int getColor(int key) {

        return map.get(key);
    }
}
