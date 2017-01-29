package todo.javier.mera.todolist.model;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import todo.javier.mera.todolist.R;

/**
 * Created by javie on 12/13/2016.
 */

public class PriorityUtil {

    private static Map<Integer, String> namesMap = new LinkedHashMap<Integer, String>() {
        {
            put(Priority.None.ordinal(), "None");
            put(Priority.Low.ordinal(), "Low");
            put(Priority.Medium.ordinal(), "Medium");
            put(Priority.High.ordinal(), "High");
        }
    };

    private static Map<Integer, Integer> drawableMap = new LinkedHashMap<Integer, Integer>() {
        {
            put(Priority.Low.ordinal(), R.mipmap.ic_low);
            put(Priority.Medium.ordinal(), R.mipmap.ic_medium);
            put(Priority.High.ordinal(), R.mipmap.ic_high);
        }
    };

    public static String getName(int key) {

        return namesMap.get(key);
    }

    public static int getDrawable(int key) {

        return drawableMap.get(key);
    }

    public static String[] getNames() {

        Collection<String> names = namesMap.values();
        return names.toArray(new String[names.size()]);
    }
}
