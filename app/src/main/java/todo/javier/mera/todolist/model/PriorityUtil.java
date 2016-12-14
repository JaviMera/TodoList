package todo.javier.mera.todolist.model;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import todo.javier.mera.todolist.R;

/**
 * Created by javie on 12/13/2016.
 */

public class PriorityUtil {

    private static Map<Integer, String> namesMap = new LinkedHashMap<Integer, String>() {
        {
            put(TaskPriority.None.ordinal(), "None");
            put(TaskPriority.Low.ordinal(), "Low");
            put(TaskPriority.Medium.ordinal(), "Medium");
            put(TaskPriority.High.ordinal(), "High");
        }
    };

    private static Map<Integer, Integer> colorMap = new LinkedHashMap<Integer, Integer>(){
        {
            put(TaskPriority.None.ordinal(), R.color.priority_none);
            put(TaskPriority.Low.ordinal(), R.color.priority_low);
            put(TaskPriority.Medium.ordinal(), R.color.priority_medium);
            put(TaskPriority.High.ordinal(), R.color.priority_high);
        }
    };

    private static Map<Integer, Integer> drawableMap = new LinkedHashMap<Integer, Integer>() {
        {
            put(TaskPriority.None.ordinal(), R.drawable.priority_none_background);
            put(TaskPriority.Low.ordinal(), R.drawable.priority_low_background);
            put(TaskPriority.Medium.ordinal(), R.drawable.priority_medium_background);
            put(TaskPriority.High.ordinal(), R.drawable.priority_high_background);
        }
    };

    public static String getName(int key) {

        return namesMap.get(key);
    }

    public static int getColor(int key) {

        return colorMap.get(key);
    }

    public static int getDrawable(int key) {

        return drawableMap.get(key);
    }

    public static String[] getNames() {

        Collection<String> names = namesMap.values();
        return names.toArray(new String[names.size()]);
    }

    public static Integer[] getDrawables() {

        Collection<Integer> drawables = drawableMap.values();
        return drawables.toArray(new Integer[drawables.size()]);
    }
}
